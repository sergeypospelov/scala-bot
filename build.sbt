name := "scala-bot"

version := "0.1"

scalaVersion := "2.13.5"

semanticdbEnabled := true
semanticdbVersion := scalafixSemanticdb.revision

lazy val root = project.in(file("."))
  .settings(
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.5" % Test,
    libraryDependencies += "org.augustjune" %% "canoe" % "0.5.1",

    Compile / run / mainClass := Some("scalaBot.ScalaBotMain"),

    semanticdbEnabled := true, // enable SemanticDB
    semanticdbVersion := scalafixSemanticdb.revision, // use Scalafix compatible version
    scalacOptions += "-Ywarn-unused" // required by `RemoveUnused` rule
  )