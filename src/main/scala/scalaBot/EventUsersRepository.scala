package scalaBot

import java.util.concurrent.ConcurrentHashMap
import scala.jdk.CollectionConverters.ConcurrentMapHasAsScala
import scala.Predef.identity

//class EventUsersRepository[R[_]] extends Repository[List[User], R[_]] {
class EventUsersRepository extends Repository[List[User]] {
  val table: scala.collection.concurrent.Map[Int,List[User]] = new ConcurrentHashMap[Int, List[User]]().asScala

  override def add(id: Int, t: List[User]): Unit = table.put(id, t)

  def addOne(id: Int, t: User): Unit = table.updateWith(id) {
    case Some(list) => Some(list :+ t)
    case None       => Some(List(t))
  }

  override def delete(id: Int): Unit = table.remove(id)

  def deleteOne(id: Int, user: User): Unit = table.updateWith(id) {
    case Some(list) => Some(list.filterNot(_ == user))
    case None       => None
  }

  override def findById(id: Int): Option[List[User]] = table.get(id)

  override def toList: List[List[User]] = table.values.toList
}