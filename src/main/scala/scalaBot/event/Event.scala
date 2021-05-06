package scalaBot.event

import java.time.ZonedDateTime

sealed trait Event {
  def description: String
  def time: ZonedDateTime
}

case class NewHomeworkEvent(description: String, time: ZonedDateTime)     extends Event

case class UpdateHomeworkEvent(description: String, time: ZonedDateTime)  extends Event

case class DeadlineEvent(description: String, time: ZonedDateTime)        extends Event
// ...
case class AnyEvent(description: String, time: ZonedDateTime)             extends Event
