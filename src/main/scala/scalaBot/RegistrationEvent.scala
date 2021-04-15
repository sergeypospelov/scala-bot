package scalaBot

trait RegistrationEvent {
  // working with memory
  /* register someone, return success of operation */
  def registration(): Boolean

  // working with memory
  /* delete user, return success of operation */
  def delete(): Boolean
}
