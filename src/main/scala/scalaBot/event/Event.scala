package scalaBot.event

import java.time.ZonedDateTime

import scalaBot.util.EventID

sealed trait Event {
  def description: String
  def time: ZonedDateTime
  def id: EventID = EventID(hashCode()) // TODO: not correct!
}

case class NewHomeworkEvent(description: String, time: ZonedDateTime)     extends Event

//case class UpdateHomeworkEvent(description: String, time: ZonedDateTime)  extends Event

case class DeadlineEvent(description: String, time: ZonedDateTime)        extends Event

case class SomeHomeworkEvent(description: String, time: ZonedDateTime)    extends Event

case class ClassEvent(description: String, time: ZonedDateTime)           extends Event
// ...
case class AnyEvent(description: String, time: ZonedDateTime)             extends Event
