package scalaBot

trait EventStorageRepo {
  def addEvent(t: Event): Unit

  def deleteEvent(t: Event): Unit

  def changeEvent(t: Event): Unit

  def findEventById(ind: Int): Event

  def toList: List[Event]
}
