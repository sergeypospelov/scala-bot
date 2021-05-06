package scalaBot.calendar

import canoe.models.User
import scalaBot.util.CalendarID

class Calendar {
  val id: CalendarID = ???
  val user: User = ???
  def getItems: List[CalendarItem] = ???
}
