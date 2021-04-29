package scalaBot.calendar

import scalaBot.Event

case class AssignmentItem(override val description: String) extends CalendarItem {
  override def getEvents: List[Event] = ???
}