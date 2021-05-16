package scalaBot.parser

import java.io.{FileInputStream}
import net.fortuna.ical4j.data.CalendarBuilder
import net.fortuna.ical4j.model.component.VEvent
import net.fortuna.ical4j.util.MapTimeZoneCache
import scala.collection.JavaConverters._


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

  def parse(path: String): List[VEvent] = {
    val iStream = new FileInputStream(path)
      .fixing
//    val target = new BufferedOutputStream(new FileOutputStream("classes2.ics"))   //
//    try iStream.readAllBytes().foreach(target.write(_)) finally target.close      // left for convenience
    val calendar = new CalendarBuilder().build(iStream)
    calendar
      .getComponents
      .getAll
      .asScala
      .collect { case ev: VEvent => ev }
      .toList
  }
}
