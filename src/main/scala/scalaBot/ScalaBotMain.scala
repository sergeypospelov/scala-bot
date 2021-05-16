package scalaBot

import java.time.{ZoneId, ZonedDateTime}

import cats.Monad
import cats.effect._
import cats.instances.list._
import cats.syntax.flatMap._
import cats.syntax.parallel._
import com.bot4s.telegram.methods.SendMessage
import net.fortuna.ical4j.util.MapTimeZoneCache
import scalaBot.ScalaBotMain.users
import scalaBot.bot.Bot
import scalaBot.event.NewHomeworkEvent
import scalaBot.repository.{EventRepository, UserRepository}
import scalaBot.user.User
import scalaBot.util._
import sttp.client3.asynchttpclient.cats.AsyncHttpClientCatsBackend
import user.UserNotifier

import scala.concurrent.duration.{DurationLong, FiniteDuration}

object ScalaBotMain extends IOApp {
  System.setProperty("net.fortuna.ical4j.timezone.cache.impl", classOf[MapTimeZoneCache].getName)
  System.setProperty("ical4j.parsing.relaxed", "true")

  private val UPD_TIME: FiniteDuration = 2.seconds

  private val users: UserRepository = new UserRepository
  private val events: EventRepository = new EventRepository
  private val eventsJournal: EventsJournal[IO] = new HashMapEventsJournal


  def setEnvironment(): IO[Unit] = {
    val SERP_ID = UserID(319541803)
    val SERP_EMKN_ID = EmknID(146)

    val event1 = NewHomeworkEvent("Test event", ZonedDateTime.of(2021, 10, 10, 12, 5, 10, 0, ZoneId.systemDefault()))
    val event2 = NewHomeworkEvent("Ev2", ZonedDateTime.now())
    val event3 = NewHomeworkEvent("Test event", ZonedDateTime.of(2020, 10, 10, 12, 5, 10, 0, ZoneId.systemDefault()))

    users.add(SERP_ID, User(SERP_ID, "Sergey Pospelov :D", SERP_EMKN_ID)) >>
      events.add(event1.id, event1) >> eventsJournal.addEvent(SERP_ID, event1.id, event1.time) >>
      events.add(event2.id, event2) >> eventsJournal.addEvent(SERP_ID, event2.id, event2.time) >>
      events.add(event3.id, event3) >> eventsJournal.addEvent(SERP_ID, event3.id, event3.time)

  }


  def repeat[F[_] : Temporal : Monad, A](delay: FiniteDuration, fa: F[A]): F[Unit] = {
    fa >> Temporal[F].sleep(UPD_TIME) >> repeat(delay, fa)
  }


  def updUser(user: User)(implicit bot: Bot): IO[Any] = {
    users.findById(user.id).flatMap {
      case Left(msg) => IO.pure(msg)
      case Right(user) => {
        eventsJournal.getNearestEvent(user.id).flatMap {
          case Left(msg) => IO.pure(msg)
          case Right(eventId) => {
            events.findById(eventId).flatMap {
              case Left(msg) => IO.pure(msg)
              case Right(event) => UserNotifier.notifyUser(eventsJournal, user, event)
            }
          }
        }
      }
    }
  }


  def notifyUsers(implicit bot: Bot): IO[Any] = {
    users.getAll.flatMap { users =>
      val IOusers = users.map { user =>
        updUser(user)
      }
      IOusers.parSequence
    }
  }


  def ping: IO[Unit] = {
    IO.println("ping")
  }

  def daemonize[A](io: IO[A]): Resource[IO, IO[Unit]] = {
    Resource.make(io.void.start)(_.cancel).map(_.joinWith(IO.unit))
  }


  override def run(args: List[String]): IO[ExitCode] = {
    setEnvironment >>
      AsyncHttpClientCatsBackend.resource[IO]().use { implicit backend =>
        implicit val bot: Bot = new Bot
        val res = for {
          botDaemon <- daemonize(bot.run())
          b <- daemonize(repeat(3.seconds, notifyUsers))
        } yield (botDaemon, b)

        res.use { case (value1, value2) =>

          IO.race(value1, value2).map(_.fold(identity, identity))
        }

      }.as(ExitCode.Success)
  }
}
