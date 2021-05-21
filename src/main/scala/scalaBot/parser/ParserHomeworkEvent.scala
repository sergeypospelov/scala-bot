package scalaBot.parser

import scalaBot.event.{DeadlineEvent, Event, NewHomeworkEvent, SomeHomeworkEvent}
import java.time.ZonedDateTime

object ParserHomeworkEvent {
  private def fromEvent(fromTime: ZonedDateTime)(event: Event): List[Event] = {
    event match {
      case SomeHomeworkEvent(description, time) =>
        if (time.isBefore(fromTime)) { // updateHW?
          List(DeadlineEvent(description, time), NewHomeworkEvent(description, time))
        } else List(NewHomeworkEvent(description, time))
      case _ => List.empty
    }
  }

  /* fromTime - time from which the deadline */
  def getHomeworkEvents(le: List[Event], fromTime: ZonedDateTime): List[Event] =
    le.flatMap(fromEvent(fromTime))
}
