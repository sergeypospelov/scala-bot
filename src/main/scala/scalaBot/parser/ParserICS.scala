package scalaBot.parser

import net.fortuna.ical4j.data.CalendarBuilder
import net.fortuna.ical4j.model.Property.{CREATED, DESCRIPTION}
import net.fortuna.ical4j.model.component.VEvent
import net.fortuna.ical4j.util.MapTimeZoneCache
import scalaBot.calendar.CalendarItem
import scalaBot.event._
import scalaBot.calendar._

import java.io.{BufferedOutputStream, FileInputStream, FileOutputStream}
import java.time.ZonedDateTime
import scala.collection.JavaConverters._
import scala.collection.mutable

object ParserICS {
  System.setProperty("net.fortuna.ical4j.timezone.cache.impl", classOf[MapTimeZoneCache].getName)
  System.setProperty("ical4j.parsing.relaxed", "true")

  implicit class FISExt(is: FileInputStream) {
    def fixing: PrefixFilterInputStream = new PrefixFilterInputStream(is) {
      override def excludeLine(line: String): Boolean = line.startsWith("DTSTAMP")

      override def getClassDoWithLines(line: String): DoWithLines = {
        if (line.startsWith("URL")) return DoWithLinesURL
        if (line.startsWith("DESCRIPTION") || line.startsWith("SUMMARY"))
          return DoWithLinesDESCRIPTION_SUMMARY
        null
      }
    }
  }

  def parse(path: String = "classes.ics") { // change in real version
    import net.fortuna.ical4j.model.Property._
    val iStream = new FileInputStream(path)
      .fixing
    //    val target = new BufferedOutputStream(new FileOutputStream("classes2.ics"))   //
    //    try iStream.readAllBytes().foreach(target.write(_)) finally target.close      // left for convenience
    val calendar = new CalendarBuilder().build(iStream)
    val events = calendar
      .getComponents
      .getAll
      .asScala
      .collect { case ev: VEvent => ev }
    events.map(_.getProperties.get(DESCRIPTION))
  }

//  def toCalendarItem(vEvents: mutable.Buffer[VEvent], fromTime: Int /* time */): List[CalendarItem] = {
//    val list = List.empty
//    vEvents
////      .filter(_.getProperties.get("CREATED;VALUE=DATE-TIME")[0] > fromTime) // and other
//      .foreach { vEvent =>
//        val summary: String = vEvent.getProperties.get(SUMMARY).value
//        val description: String = vEvent.getProperties.get(DESCRIPTION).value
//        val time: ZonedDateTime = vEvent.getProperties.get(DTSTART;TZID=Europe/Moscow;VALUE=DATE-TIME).value
//        val desk: String = summary + "\n" + description
//        val listEvents: List[Event] = List.empty
//
//        if (summary.toLowerCase() contains "лекция") {
//          listEvents.appended(AnyEvent(desk, /* now */))
//          val cl = new ClassItem {
//            override def description: String    = desk
//            override def getEvents: List[Event] = listEvents
//          }
//          list.appended(cl)
//        }
//        else if (summary.toLowerCase() contains "семинар") {
//          if (true) {
//            listEvents.appended(NewHomeworkEvent(desk, /* now */))
//          }
//          else if (true) {
//            listEvents.appended(UpdateHomeworkEvent(desk, /* now */))
//          }
//          else if (true) {
//            listEvents.appended(DeadlineEvent(desk, time))
//          }
//          val cl = new AssignmentItem {
//            override def description: String    = desk
//            override def getEvents: List[Event] = listEvents
//          }
//          list.appended(cl)
//        }
//        else {
//          ...
//        }
//    }
//    list
//  }
}
