package scalaBot.bot

import cats.effect.IO
import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.cats.{Polling, TelegramBot}
import sttp.client3.SttpBackend


class Bot(implicit val backend: SttpBackend[IO, Any])
  extends TelegramBot[IO](Bot.TG_TOKEN, backend)
  with Polling[IO]
  with Commands[IO] {

  onCommand("ping") {implicit msg =>
    reply("pong").void
  }

  onCommand("start" ) { implicit msg =>
    reply(s"Hello, ${msg.from.get.firstName}!").void
  }
}

object Bot {
  private val TG_TOKEN = "1605857011:AAF4c93TBz3H2v5fzmkMSZsrWsI9NAQuT0k"
}
