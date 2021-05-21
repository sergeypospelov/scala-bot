package scalaBot.repository

import cats.effect.IO
import scalaBot.user.User
import scalaBot.util.UserID

class UserRepository extends HashMapRepository[IO, UserID, User]
