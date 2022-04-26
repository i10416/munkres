import sbt._

object Dependencies {
    val scalaTestVersion = "3.2.12"
    def deps = Seq(
      "org.scalatest" %% "scalatest" % scalaTestVersion % Test
    )
}
