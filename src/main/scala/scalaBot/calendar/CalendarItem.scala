package scalaBot.calendar

import net.fortuna.ical4j.model.component.VEvent
import scalaBot.event.Event
import scalaBot.parser.ParserCalendarEvents.{getAssignmentEvents, getClassEvents}

abstract sealed class CalendarItem(vEvent: VEvent) {
  def description: String
  def getEvents: List[Event]
}

final case class AssignmentItem(vEvent: VEvent) extends CalendarItem(vEvent) {
  override def description: String = "" // ???
  override def getEvents: List[Event] = getAssignmentEvents(vEvent)
}

final case class ClassItem(vEvent: VEvent) extends CalendarItem(vEvent) {
  override def description: String = "" // ???
  override def getEvents: List[Event] = getClassEvents(vEvent)
}
