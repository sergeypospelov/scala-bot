package scalaBot.repository

import scalaBot.ID

import java.util.concurrent.ConcurrentHashMap
import scala.jdk.CollectionConverters.ConcurrentMapHasAsScala
import scala.util.{Failure, Success, Try}

trait HashMapRepository[T] extends Repository[T] {
  val table: scala.collection.concurrent.Map[ID, T] = new ConcurrentHashMap[ID, T]().asScala

  override def add(id: ID, t: T): Unit = table.put(id, t)

  override def delete(id: ID): Unit = table.remove(id)

  override def findById(id: ID): Try[T] = table.get(id) match {
    case None         => Failure(new Exception("No such id!"))
    case Some(value)  => Success(value)
  }

  override def update(id: ID, t: T): Unit = table.update(id, t)

  override def getAll: List[T] = table.values.toList
}
