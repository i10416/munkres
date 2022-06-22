import Dependencies._
import sbt._
import sbtcrossproject.CrossPlugin.autoImport.crossProject
val scala3Version = "3.1.3"

inThisBuild(
  Seq(
    homepage := Some(url("https://github.com/i10416/munkres")),
    organization := "dev.i10416",
    description := "Munkres Algorithm implementation for Scala",
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation"
    ),
    licenses := Seq(
      "Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")
    ),
    pomIncludeRepository := { _ => false },
    developers := List(
      Developer(
        "i10416",
        "Yoichiro ITO",
        "contact.110416+munkres@gmail.com",
        url("https://github.com/i10416")
      )
    ),
    versionScheme := Some("early-semver"),
    sonatypeCredentialHost := "s01.oss.sonatype.org",
    sonatypeRepository := "https://s01.oss.sonatype.org/service/local",
    crossScalaVersions ++= Seq(scala3Version),
    scmInfo := Some(
      ScmInfo(
        url("https://github.com/i10416/munkres"),
        "scm:git@github.com:i10416/munkres.git"
      )
    )
  )
)
lazy val noPublishSettings = Seq(
  publish / skip := true
)
lazy val root = project
    .in(file("."))
    .aggregate(munkres.js, munkres.jvm)
    .settings(noPublishSettings)
lazy val munkres = crossProject(JSPlatform, JVMPlatform)
    .in(file("."))
    .settings(
      name := "munkres",
      scalaVersion := scala3Version,
      libraryDependencies ++= Dependencies.deps
    )
    .jsSettings()
    .jvmSettings(
      libraryDependencies += "org.scala-js" %% "scalajs-stubs" % "1.1.0"
    )

lazy val docs = project
    .in(file(".generated_docs"))
    .dependsOn(munkres.jvm, munkres.js)
    .settings(
      scalaVersion := scala3Version,
      mdocIn := file("docs"),
      mdocVariables := Map[String, String]()
    )
    .settings(noPublishSettings)
    .enablePlugins(MdocPlugin)
