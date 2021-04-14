package scalaBot

trait Event {
  // working with memory
  def send(message: String): Unit
}
