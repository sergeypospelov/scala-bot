package scalaBot

import cats.effect.{ExitCode, IO, IOApp}
import cats.effect.unsafe.implicits.global
import scalaBot.bot.Bot
import sttp.client3.SttpBackend
import sttp.client3.asynchttpclient.cats.AsyncHttpClientCatsBackend

import scala.concurrent.duration.{DurationLong, FiniteDuration}

object ScalaBotMain extends IOApp {
//  private def UPD_TIME: FiniteDuration = 1L minute
  private val client: IO[SttpBackend[IO, Any]] = AsyncHttpClientCatsBackend[IO]()

  override def run(args: List[String]): IO[ExitCode] = {
    client.flatMap { implicit backend =>
      val bot = new Bot
      bot.run()
    }.as(ExitCode.Success)
  }
}
