package scalaBot.calendar

import scalaBot.Event

abstract class CalendarItem {
  val description: String
  def getEvents: List[Event]
}

