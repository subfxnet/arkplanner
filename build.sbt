name := "ark-survivor-planner"

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "com.github.japgolly.scalajs-react" %%% "core" % "0.9.0",
  "com.github.japgolly.scalajs-react" %%% "ext-scalaz71" % "0.9.0",
  "com.github.japgolly.scalajs-react" %%% "ext-monocle" % "0.9.0",
  "com.github.japgolly.scalacss" %%% "core" % "0.2.0",
  "com.github.japgolly.scalacss" %%% "ext-react" % "0.2.0"
)

jsDependencies ++= Seq(
  "org.webjars" % "react" % "0.12.2" / "react-with-addons.min.js" commonJSName "React"
)

persistLauncher := true

persistLauncher in Test := false

lazy val root = (project in file("."))
  .enablePlugins(ScalaJSPlugin)


