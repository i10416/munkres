// remove comment-out to enable plugins.
scalacOptions ++= Seq("deprecation")
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.6")
addSbtPlugin("org.scalameta" % "sbt-mdoc" % "2.2.24")
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.5.3")

addSbtPlugin("com.github.sbt" % "sbt-ci-release" % "1.5.10")


// for binary compatibility
//addSbtPlugin("com.typesafe" % "sbt-mima-plugin" % "0.9.2")


