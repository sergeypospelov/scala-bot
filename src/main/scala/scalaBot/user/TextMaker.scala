package scalaBot.user

import scalaBot.event.Event

sealed trait TextMaker {
  def make(user: User, event: Event): String
}

object SimpleTextMaker extends TextMaker {
  override def make(user: User, event: Event): String =
    s"Dear ${user.name}, see this:\n ${event.description} at ${event.time}"
}