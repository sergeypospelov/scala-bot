package scalaBot.user

import cats.effect.IO
import com.bot4s.telegram.methods.SendMessage
import scalaBot.EventsJournal
import scalaBot.bot.Bot
import scalaBot.event.Event

object UserNotifier {
  def notifyUser(eventsJournal: EventsJournal[IO], user: User, event: Event)(implicit bot: Bot): IO[Boolean] = {
    eventsJournal.markAsSended(event.id) >>
    bot.request(SendMessage(user.id.id, SimpleTextMaker.make(user, event))).as(true)
  }
}
