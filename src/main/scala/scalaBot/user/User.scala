package scalaBot.user

import scalaBot.util.{EmknID, UserID}

case class User(id: UserID, name: String, emknId: EmknID)
