package scalaBot.parser

import java.io.File
import sttp.client3._
import cats.effect.IO
import cats.effect.unsafe.implicits.global
import sttp.client3.asynchttpclient.cats.AsyncHttpClientCatsBackend
import sttp.model.Uri

object ParserEMKN {
  def getUriById(id : Int): Uri = {
    val idStr = id.toString
    uri"https://emkn.ru/users/$idStr/classes.ics"
  }

  def parseByPath(id: Int, targetFileName: String = "classes.ics")(implicit backend: SttpBackend[IO, Any]): IO[Any] = {
    val targetFile = new File(targetFileName)
    val uri = getUriById(id)
    val request = basicRequest
      .get(uri)
      .response(asFile(targetFile))

    request.send(backend)
  }
}
