package scalaBot

trait Memory extends Registration {
  /* take information from the site. */
  def update(/*...*/): Unit

  // maybe not there
  /* parsing coming information */
  def parse(data: String): Unit

  // working with memory
  def changeData(/*...*/): Unit
}
