package scalaBot.calendar

import scalaBot.event.Event

sealed trait CalendarItem {
  def description: String
  def getEvents: List[Event]
}

final case class AssignmentItem(description: String) extends CalendarItem {
  override def getEvents: List[Event] = ???
}

final case class ClassItem(override val description: String) extends CalendarItem {
  override def getEvents: List[Event] = ???
}
