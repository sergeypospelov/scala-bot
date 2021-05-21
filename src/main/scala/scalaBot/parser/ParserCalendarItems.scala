package scalaBot.parser

import net.fortuna.ical4j.model.Property
import net.fortuna.ical4j.model.Property.{LAST_MODIFIED}
import net.fortuna.ical4j.model.component.VEvent
import scalaBot.calendar.{AssignmentItem, CalendarItem, ClassItem}


object ParserCalendarItems {
  def CalendarItems(vEvents: List[VEvent], fromTime: String, op: VEvent => CalendarItem): List[CalendarItem] = {
    val format = new java.text.SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'")
    vEvents
      .filter { ev : VEvent => ev.getProperties.getFirst(LAST_MODIFIED)
        .map { a : Property => format.parse(a.getValue).after(format.parse(fromTime)) }.get() }
      .map { ev : VEvent => op(ev) }
  }

  def toAssignmentItems(vEvents: List[VEvent], fromTime: String = "20200101T000000Z"): List[CalendarItem] =
    CalendarItems(vEvents, fromTime, AssignmentItem)

  def toClassItems(vEvents: List[VEvent], fromTime: String = "20200101T000000Z"): List[CalendarItem] =
    CalendarItems(vEvents, fromTime, ClassItem)
}
