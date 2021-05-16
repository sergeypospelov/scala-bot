package scalaBot

import java.time.ZonedDateTime

import cats.effect.IO
import cats.implicits._
import scalaBot.repository.{HashMapRepository, UserEventsRepository}
import scalaBot.util._

import scala.collection.mutable

sealed trait EventsJournal[F[_]] {
  def addEvent(userID: UserID, eventID: EventID, date: ZonedDateTime): F[Unit]

  def getEventStatus(eventID: EventID): IO[Either[String, Status]]

  def markAsSended(eventID: EventID): IO[Unit]

  def markAsNotSended(eventID: EventID): IO[Unit]

  def getNearestEvent(userId: UserID): IO[Either[String, EventID]]
}


trait Status

case object Send extends Status

case object NotSend extends Status

// TODO: use another structures to make addition and getting faster
// TODO: add hole for inner structures

class HashMapEventsJournal extends EventsJournal[IO] {

  val userEvents: UserEventsRepository = new UserEventsRepository()
  val eventsStatus: HashMapRepository[IO, EventID, Status] = new HashMapRepository[IO, EventID, Status]()
  val eventsDate: HashMapRepository[IO, EventID, ZonedDateTime] = new HashMapRepository[IO, EventID, ZonedDateTime]()

  override def addEvent(userID: UserID, eventID: EventID, date: ZonedDateTime): IO[Unit] = {
    userEvents.addOne(userID, eventID) >>
      eventsStatus.add(eventID, NotSend) >>
      eventsDate.add(eventID, date)
  }

  override def getEventStatus(eventID: EventID): IO[Either[String, Status]] = {
    eventsStatus.findById(eventID)
  }

  override def markAsSended(eventID: EventID): IO[Unit] = {
    eventsStatus.update(eventID, Send)
  }

  override def markAsNotSended(eventID: EventID): IO[Unit] = {
    eventsStatus.update(eventID, NotSend)
  }

  override def getNearestEvent(userID: UserID): IO[Either[String, EventID]] = {
    userEvents.findById(userID).flatMap {
      case Left(msg) => IO.pure(Left(msg))
      case Right(events) => {
        events.map(evID =>
          eventsStatus.findById(evID).flatMap { statusOrErr =>
            eventsDate.findById(evID).map { dateOrErr =>
              (dateOrErr, statusOrErr, evID)
            }
          }
        ).sequence.map { events =>
          val lst = events.collect { case (Right(data), Right(NotSend), id) => (data, id) }
          if (lst.isEmpty) {
            Left("No events!")
          } else {
            Right(lst.minBy(_._1)._2)
          }
        }
      }
    }
  }
}
