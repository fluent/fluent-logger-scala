import SonatypeKeys._

sonatypeSettings

organization := "org.fluentd"

name := "fluent-logger-scala"

version := "0.5.1-SNAPSHOT"

publishMavenStyle := true

scalaVersion := "2.11.5"

crossScalaVersions := Seq("2.10.4", scalaVersion.value)

resolvers ++= Seq(
  "Sonatype Repository" at "http://oss.sonatype.org/content/repositories/releases"
)

scalacOptions ++= Seq("-deprecation", "-feature", "-language:implicitConversions")

logBuffered in Test := false

libraryDependencies ++= Seq(
  "org.fluentd" % "fluent-logger" % "0.3.1",
  "junit" % "junit" % "4.12" % "test",
  "org.slf4j" % "slf4j-api" % "1.7.10",
  "org.slf4j" % "slf4j-simple" % "1.7.10" % "test",
  "org.xerial" % "fluentd-standalone" % "0.1.2"
)

libraryDependencies ++= Seq(
  "org.json4s" %% "json4s-native" % "3.2.11",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "org.scala-lang" % "scala-actors" % scalaVersion.value % "test"
)

pomExtra := (
  <url>https://github.com/fluent/fluent-logger-scala</url>
  <licenses>
    <license>
      <name>Apache License, Version 2</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.html</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git://github.com/fluent/fluent-logger-scala.git</url>
    <connection>scm:git:git://github.com/fluent/fluent-logger-scala.git</connection>
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
