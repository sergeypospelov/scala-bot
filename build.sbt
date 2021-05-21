name := "scala-bot"

version := "0.1"

scalaVersion := "2.13.5"

semanticdbEnabled := true
semanticdbVersion := scalafixSemanticdb.revision

resolvers += Resolver.sonatypeRepo("snapshots")

lazy val root = project.in(file("."))
  .settings(
    Compile / run / mainClass := Some("scalaBot.ScalaBotMain"),

    semanticdbEnabled := true, // enable SemanticDB
    semanticdbVersion := scalafixSemanticdb.revision, // use Scalafix compatible version
    scalacOptions += "-Ywarn-unused:imports" // required by `RemoveUnused` rule
  )

libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.2.7" % Test,

    "com.bot4s" %% "telegram-core" % "5.0.0-0e30d39a-SNAPSHOT", // telegram api

    "com.softwaremill.sttp.client3" %% "core" % "3.3.0", // http client
    "com.softwaremill.sttp.client3" %% "async-http-client-backend-cats" % "3.3.0", // for cats http

    "org.typelevel" %% "cats-effect" % "3.1.0", // cats

    "org.mnode.ical4j" % "ical4j" % "4.0.0-alpha9", // ICAL parser
)