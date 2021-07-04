import sbt._

object Dependencies {
    val scalaTestVersion = "3.2.9"
    val http4sVersion = "0.23.0-RC1"
    def deps = Seq(
      "org.http4s" %% "http4s-core" % http4sVersion,
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "org.scala-lang.modules" %% "scala-collection-compat" % "2.4.4",
      "org.scalatest" %% "scalatest" %scalaTestVersion % Test,
    )
}
