package scalaBot.repository

import scalaBot.User

class EventUsersRepository extends HashMapRepository[List[User]] {

  def addOne(id: Int, t: User): Unit = table.updateWith(id) {
    case Some(list) => Some(list :+ t)
    case None       => Some(List(t))
  }

  def deleteOne(id: Int, user: User): Unit = table.updateWith(id) {
    case Some(list) => Some(list.filterNot(_ == user))
    case None       => None
  }
}