package scalaBot.parser

import java.util
import scala.jdk.CollectionConverters.CollectionHasAsScala

object DoWithLinesURL extends DoWithLines {
  override def doWithLines(listRes: util.List[String]): String =
    listRes.asScala.fold("") {(a,b) => a + b.dropWhile(_ == ' ')} + "\n"
}