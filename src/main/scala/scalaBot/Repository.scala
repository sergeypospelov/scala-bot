package scalaBot

trait Repository[T] {
  def add(id: Int, t: T): Unit

  def delete(id: Int): Unit

  def findById(id: Int): Option[T]

  def toList: List[T]
}
