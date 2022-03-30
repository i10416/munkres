// remove comment-out to enable plugins.
scalacOptions ++= Seq("deprecation")
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.6")
addSbtPlugin("org.scalameta" % "sbt-mdoc" % "2.3.1")
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.6.2")
addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "1.2.0")
addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.9.0")
addSbtPlugin("com.github.sbt" % "sbt-ci-release" % "1.5.10")

// for binary compatibility
//addSbtPlugin("com.typesafe" % "sbt-mima-plugin" % "0.9.2")
