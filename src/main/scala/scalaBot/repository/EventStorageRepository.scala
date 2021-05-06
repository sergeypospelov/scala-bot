package scalaBot.repository

import cats.effect.IO
import scalaBot.util.EventID
import scalaBot.event.Event

class EventStorageRepository extends HashMapRepository[IO, EventID, Event] {}
