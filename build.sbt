name := "block-puzzle-jewel-solver"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  "org.clapper" %% "grizzled-scala" % "4.5.0"
)

scalacOptions += "-Ypartial-unification"