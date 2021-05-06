package scalaBot.repository

import cats.effect.IO
import scalaBot.util.{EventID, UserID}


class EventUsersRepository extends HashMapRepository[IO, EventID, List[UserID]] {
  def addOne(id: EventID, userID: UserID): IO[Unit] = IO.delay {
    table.updateWith(id) {
      case Some(list) => Some(list :+ userID)
      case None => Some(List(userID))
    }
  }

  def deleteOne(id: EventID, userId: UserID): Unit = table.updateWith(id) {
    case Some(list) => Some(list.filterNot(_ == userId))
    case None       => None
  }
}