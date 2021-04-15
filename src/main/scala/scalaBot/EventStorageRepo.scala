package scalaBot

class Event

trait EventStorageRepo {g
  def addEvent(t: Event): Unit

  def deleteEvent(t: Event): Unit

  def changeEvent(t: Event): Unit

  def findEventById(ind: Int): Event

  def toList: List[Event]
}
