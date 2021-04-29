package scalaBot

import java.time.ZonedDateTime

sealed abstract class Event {
  val description: String
  val time: ZonedDateTime
}

case class NewHomeworkEvent(override val description: String, override val time: ZonedDateTime)     extends Event
case class UpdateHomeworkEvent(override val description: String, override val time: ZonedDateTime)  extends Event
case class DeadlineEvent(override val description: String, override val time: ZonedDateTime)        extends Event
// ...
case class AnyEvent(override val description: String, override val time: ZonedDateTime)             extends Event
