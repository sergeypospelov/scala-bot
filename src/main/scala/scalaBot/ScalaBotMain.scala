package scalaBot

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import scalaBot.bot.Bot
import sttp.client3.asynchttpclient.cats.AsyncHttpClientCatsBackend

object ScalaBotMain {
  def main(args: Array[String]): Unit = {
    val client = AsyncHttpClientCatsBackend[IO]()
    val botRunner = client.flatMap { implicit backend =>
      val bot = new Bot
      bot.run()
    }
    botRunner.unsafeRunSync()
  }
}
