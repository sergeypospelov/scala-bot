package scalaBot.repository

import scalaBot.{EventID, ID}

class EventUsersRepository extends HashMapRepository[List[ID]] {
  def addOne(id: EventID, userId: ID): Unit = table.updateWith(id) {
    case Some(list) => Some(list :+ userId)
    case None       => Some(List(userId))
  }

  def deleteOne(id: EventID, userId: ID): Unit = table.updateWith(id) {
    case Some(list) => Some(list.filterNot(_ == userId))
    case None       => None
  }
}