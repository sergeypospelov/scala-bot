package scalaBot.repository

import cats.effect.IO
import scalaBot.util.{EventID, UserID}


class UserEventsRepository extends HashMapRepository[IO, UserID, List[EventID]] {
  def addOne(userID: UserID, eventID: EventID): IO[Unit] = IO.delay {
    table.updateWith(userID) {
      case Some(list) => Some(list :+ eventID)
      case None => Some(List(eventID))
    }
  }

  def deleteOne(userID: UserID, eventID: EventID): IO[Unit] = IO.delay {
    table.updateWith(userID) {
      case Some(list) => Some(list.filterNot(_ == eventID))
      case None => None
    }
  }
}