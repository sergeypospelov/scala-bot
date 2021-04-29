package scalaBot.repository

import java.util.concurrent.ConcurrentHashMap
import scala.jdk.CollectionConverters.ConcurrentMapHasAsScala
import scala.util.{Failure, Success, Try}

trait HashMapRepository[T] extends Repository[T] {
  val table: scala.collection.concurrent.Map[Int, T] = new ConcurrentHashMap[Int, T]().asScala

  override def add(id: Int, t: T): Unit = table.put(id, t)

  override def delete(id: Int): Unit = table.remove(id)

  override def findById(id: Int): Try[T] = table.get(id) match {
    case None => Failure(new Exception("No such id!"))
    case Some(value) => Success(value)
  }

  override def update(id: Int, t: T): Unit = table.update(id, t)

  override def getAll: List[T] = table.values.toList
}
