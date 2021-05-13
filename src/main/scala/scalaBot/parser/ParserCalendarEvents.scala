package scalaBot.parser

import net.fortuna.ical4j.model.Property
import net.fortuna.ical4j.model.Property.{DESCRIPTION, DTEND, SUMMARY}
import net.fortuna.ical4j.model.component.VEvent
import net.fortuna.ical4j.model.property._
import scalaBot.event.{ClassEvent, Event, SomeHomeworkEvent}
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


object ParserCalendarEvents {
  def getCalendarEvents(vEvent: VEvent, op: (String, ZonedDateTime) => Event): List[Event] = {
    val format = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'Z")
    val summary: String = vEvent.getProperties.getFirst(SUMMARY)
      .map { a : Property => a.getValue }.get()
    val description: String = vEvent.getProperties.getFirst(DESCRIPTION)
      .map { a : Property => a.getValue }.get()
    val time: ZonedDateTime = vEvent.getProperties.getFirst(DTEND)
      .map { a : DtEnd[ZonedDateTime] => var str = a.getValue
        if (!str.endsWith("Z")) str = str + "Z"
        ZonedDateTime.parse(str + "+0000", format) }.get()
    List(op(summary + "\n" + description, time))
  }

  def getAssignmentEvents(vEvent: VEvent): List[Event] = getCalendarEvents(vEvent, SomeHomeworkEvent)

  def getClassEvents(vEvent: VEvent): List[Event] = getCalendarEvents(vEvent, ClassEvent)
}
