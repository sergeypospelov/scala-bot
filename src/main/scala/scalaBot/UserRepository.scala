package scalaBot

import java.util.concurrent.ConcurrentHashMap
import scala.jdk.CollectionConverters.ConcurrentMapHasAsScala

class UserRepository extends Repository[User] {
  val table: scala.collection.concurrent.Map[Int,User] = new ConcurrentHashMap[Int, User]().asScala

  override def add(id: Int, t: User): Unit = table.put(id, t)

  override def delete(id: Int): Unit = table.remove(id)

  override def findById(id: Int): Option[User] = table.get(id)

  override def toList: List[User] = table.values.toList
}
