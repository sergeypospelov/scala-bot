package scalaBot.bot

import cats.effect.IO
import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.cats.{Polling, TelegramBot}
import com.bot4s.telegram.models.ReplyKeyboardMarkup.singleColumn
import com.bot4s.telegram.models.KeyboardButton
import sttp.client3.SttpBackend

class Bot(implicit val backend: SttpBackend[IO, Any])
  extends TelegramBot[IO](Bot.TG_TOKEN, backend)
    with Polling[IO]
    with Commands[IO] {

  private val keyboard = singleColumn(Seq(KeyboardButton("/Ping"), KeyboardButton("/Help")),
    resizeKeyboard = Some(true))

  onCommand("help") { implicit msg =>
    reply("Телеграм-бот простых работяг, который помнит о всех твоих дедлайнах.\n" +
      "/help --- помощь;\n" +
      "/ping --- проверка работоспособности бота.\n",
      replyMarkup = Some(keyboard)).void
  }

  onCommand("ping") { implicit msg =>
    reply("pong", replyMarkup = Some(keyboard)).void
  }

  onCommand("start") { implicit msg =>
    reply(s"Hello, ${msg.from.get.firstName}!", replyMarkup = Some(keyboard)).void
  }
}

object Bot {
  private val TG_TOKEN = "1605857011:AAF4c93TBz3H2v5fzmkMSZsrWsI9NAQuT0k"
}
