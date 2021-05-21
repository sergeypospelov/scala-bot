package scalaBot.repository


import cats.effect.Sync

import java.util.concurrent.ConcurrentHashMap
import scala.jdk.CollectionConverters.ConcurrentMapHasAsScala

class HashMapRepository[F[_] : Sync, ID, T] extends Repository[F, ID, T] {
  val table: scala.collection.concurrent.Map[ID, T] = new ConcurrentHashMap[ID, T]().asScala

  override def add(id: ID, t: T): F[Unit] = Sync[F].delay {
    table.put(id, t)
  }

  override def delete(id: ID): F[Unit] = Sync[F].delay {
    table.remove(id)
  }

  override def findById(id: ID): F[Either[String, T]] = Sync[F].delay {
    table.get(id) match {
      case None         => Left("No such id!")
      case Some(value)  => Right(value)
    }
  }

  override def update(id: ID, t: T): F[Unit] = Sync[F].delay {
    table.update(id, t)
  }

  override def getAll: F[List[T]] = Sync[F].delay {
    table.values.toList
  }
}
