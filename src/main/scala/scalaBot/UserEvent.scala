package scalaBot

trait UserEvent {
  /* creates task for handler */
  def send(data: String /*, listener */): Unit

  // working with memory
  /* change user data */
  def update(): Unit
  
  /* processing user request */
  def getMessage(message: String): Unit
}
