name := "block-puzzle-jewel-solver"

version := "0.1"

scalaVersion := "2.12.6"
val zioVersion = "1.0.0-RC17"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % zioVersion,
  "dev.zio" %% "zio-streams" % zioVersion,
  "dev.zio" %% "zio-interop-cats" % "2.0.0.0-RC10",
  "org.typelevel" %% "cats-effect" % "2.0.0",
)

scalacOptions += "-Ypartial-unification"