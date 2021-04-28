package scalaBot
import java.time.ZonedDateTime

case class Event(description: String, time: ZonedDateTime, source: Source = AnyEvent)
