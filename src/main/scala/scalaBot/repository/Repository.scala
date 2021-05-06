package scalaBot.repository

trait Repository[F[_], ID, T] {
  def add(id: ID, t: T): F[Unit]

  def delete(id: ID): F[Unit]

  def findById(id: ID): F[Either[String, T]]

  def update(id: ID, t: T): F[Unit]

  def getAll: F[List[T]]
}
