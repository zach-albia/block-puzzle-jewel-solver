name := "block-puzzle-jewel-solver"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % "1.0.0-RC11-1",
  "dev.zio" %% "zio-streams" % "1.0.0-RC11-1"
)

scalacOptions += "-Ypartial-unification"