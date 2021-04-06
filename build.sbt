version in Global       := "0.1"
scalaVersion in Global  := "2.13.5"

lazy val root = project.in(file("."))
  .enablePlugins(SbtPlugin)
  .settings(
    name := "scala-bot",
    libraryDependencies ++= Seq("org.augustjune" %% "canoe" % "0.3.1",
      "org.scalactic" %% "scalactic" % "3.2.5"),
    Compile / run / mainClass := Some("scalaBot.ScalaBotMain")
  )
