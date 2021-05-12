package scalaBot.parser

import java.util
import scala.jdk.CollectionConverters.CollectionHasAsScala

object DoWithLinesDESCRIPTION_SUMMARY extends DoWithLines {
  override def doWithLines(listRes: util.List[String]): String =
    (listRes.asScala.drop(1).fold(listRes[0]) {(a,b) => a + b.drop(1)} + "\n")
      .replaceAll("\\;", "")
}