import sbt._

object Dependencies {
    val scalaTestVersion = "3.2.9"
    def deps = Seq(
      "org.typelevel" %% "cats-core" % "2.6.1",
      "org.scala-lang.modules" %% "scala-collection-compat" % "2.4.4",
      "org.scalatest" %% "scalatest" %scalaTestVersion % Test,
    )
}
