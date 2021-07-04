// remove comment-out to enable plugins.
scalacOptions ++= Seq("pdeprecation")
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.2")
addSbtPlugin("org.scalameta" % "sbt-mdoc" % "2.2.21")
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.5.3")



// for binary compatibility
//addSbtPlugin("com.typesafe" % "sbt-mima-plugin" % "0.9.2")

// for release
// addSbtPlugin("com.geirsson" % "sbt-ci-release" % "1.5.7")

