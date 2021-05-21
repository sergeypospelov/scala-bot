package scalaBot.repository

import cats.effect.IO
import scalaBot.util.EventID
import scalaBot.event.Event

class EventRepository extends HashMapRepository[IO, EventID, Event] {}
