package scalaBot.repository

import scala.util.Try

trait Repository[T] {
  def add(id: Int, t: T): Unit

  def delete(id: Int): Unit

  def findById(id: Int): Try[T]

  def update(id: Int, t: T): Unit

  def getAll: List[T]
}
