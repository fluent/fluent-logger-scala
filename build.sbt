import SonatypeKeys._

sonatypeSettings

organization := "org.fluentd"

name := "fluent-logger-scala"

version := "0.5.1-SNAPSHOT"

publishMavenStyle := true

crossScalaVersions := Seq("2.10.4", "2.11.0")

val SCALA_VERSION = "2.11.0"

scalaVersion := SCALA_VERSION

resolvers ++= Seq(
  "Sonatype Repository" at "http://oss.sonatype.org/content/repositories/releases",
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "MessagePack For Scala Repository" at "http://takezoux2.github.com/maven"
)

scalacOptions <++= scalaVersion map { v =>
  if (v.startsWith("2.10"))
    Seq("-deprecation", "-feature", "-language:implicitConversions")
  else
    Seq("-deprecation")
}


logBuffered in Test := false


libraryDependencies ++= Seq(
  "org.fluentd" % "fluent-logger" % "0.2.11",
  "junit" % "junit" % "4.11" % "test",
  "org.slf4j" % "slf4j-api" % "1.7.7",
  "org.slf4j" % "slf4j-simple" % "1.7.7" % "test",
  "org.xerial" % "fluentd-standalone" % "0.1.2"
)

libraryDependencies <++=  scalaVersion { sv =>
  if (sv.startsWith("2.10"))
    Seq("org.json4s" %% "json4s-native" % "3.2.9",
        "org.scalatest" %% "scalatest" % "2.1.3" % "test",
        "org.scala-lang" % "scala-actors" % "2.10.4" % "test")
  else //(sv.startsWith("2.11"))
    Seq("org.json4s" %% "json4s-native" % "3.2.9",
        "org.scalatest" %% "scalatest" % "2.1.3" % "test",
        "org.scala-lang" % "scala-actors" % "2.11.0" % "test")
}


pomExtra := (
  <url>https://github.com/oza/fluent-logger-scala</url>
  <licenses>
    <license>
      <name>Apache License, Version 2</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.html</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git://github.com/oza/fluent-logger-scala.git</url>
    <connection>scm:git:git://github.com/oza/fluent-logger-scala.git</connection>
  </scm>
  <developers>
    <developer>
      <id>oza</id>
      <name>Tsuyoshi Ozawa</name>
      <url>https://github.com/oza</url>
    </developer>
    <developer>
      <id>xerial</id>
      <name>Taro L. Saito</name>
      <url>https://github.com/xerial</url>
    </developer>
  </developers>
)

credentials ++= {
  val sonatype = ("Sonatype Nexus Repository Manager", "oss.sonatype.org")
  def loadMavenCredentials(file: java.io.File) : Seq[Credentials] = {
    xml.XML.loadFile(file) \ "servers" \ "server" map (s => {
      val host = (s \ "id").text
      val realm = if (host == sonatype._2) sonatype._1 else "Unknown"
      Credentials(realm, host, (s \ "username").text, (s \ "password").text)
    })
  }
  val ivyCredentials   = Path.userHome / ".ivy2" / ".credentials"
  val mavenCredentials = Path.userHome / ".m2"   / "settings.xml"
  (ivyCredentials.asFile, mavenCredentials.asFile) match {
    case (ivy, _) if ivy.canRead => Credentials(ivy) :: Nil
    case (_, mvn) if mvn.canRead => loadMavenCredentials(mvn)
    case _ => Nil
  }
}
