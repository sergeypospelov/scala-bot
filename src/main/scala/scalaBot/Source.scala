package scalaBot

sealed trait Source

case object NewHomeworkEvent    extends Source
case object UpdateHomeworkEvent extends Source
case object DeadlineEvent       extends Source
// ...
case object AnyEvent            extends Source
