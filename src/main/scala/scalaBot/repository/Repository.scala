package scalaBot.repository

import scalaBot.ID
import scala.util.Try

trait Repository[T] {
  def add(id: ID, t: T): Unit

  def delete(id: ID): Unit

  def findById(id: ID): Try[T]

  def update(id: ID, t: T): Unit

  def getAll: List[T]
}
