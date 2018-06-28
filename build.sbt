name := "block-puzzle-jewel-solver"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  "co.fs2" %% "fs2-core" % "0.10.4",
  "org.typelevel" %% "cats-core" % "1.1.0"
)

scalacOptions += "-Ypartial-unification"