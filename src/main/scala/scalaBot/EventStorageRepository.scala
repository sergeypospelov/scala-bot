package scalaBot

import java.util.concurrent.ConcurrentHashMap
import scala.jdk.CollectionConverters.ConcurrentMapHasAsScala

class EventStorageRepository extends Repository[Event] {
  val table: scala.collection.concurrent.Map[Int,Event] = new ConcurrentHashMap[Int, Event]().asScala

  override def add(id: Int, t: Event): Unit = table.put(id, t)

  override def delete(id: Int): Unit = table.remove(id)

  override def findById(id: Int): Option[Event] = table.get(id)

  override def toList: List[Event] = table.values.toList
}
