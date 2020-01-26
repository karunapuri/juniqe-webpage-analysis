name := "juniqe_webpage_analysis"

version := "1.0"

scalaVersion := "2.12.1"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)

libraryDependencies ++= Seq(ws,
"org.jsoup" % "jsoup" % "1.6.1"
)

lazy val testDependencies = Seq(
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.0.0" % Test,
  //  "org.scalatest" %% "scalatest" % "3.0.8" % "test",
  specs2 % Test)

libraryDependencies += guice