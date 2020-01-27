name := "juniqe_webpage_analysis"

version := "1.0"

scalaVersion := "2.12.1"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)

libraryDependencies ++= Seq(ws,
"org.jsoup" % "jsoup" % "1.6.1",
  "org.scalatra.scalate" %% "play-scalate" % "0.4.0"
)++testDependencies

lazy val testDependencies = Seq(
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.0.0" % Test,
  specs2 % Test)

libraryDependencies += guice