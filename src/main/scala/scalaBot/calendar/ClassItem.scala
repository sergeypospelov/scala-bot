package scalaBot.calendar
import scalaBot.Event

case class ClassItem(override val description: String) extends CalendarItem {
  override def getEvents: List[Event] = ???
}
