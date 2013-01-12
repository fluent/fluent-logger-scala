organization := "org.fluentd"

name := "fluent-logger-scala"

version := "0.2.0"

publishMavenStyle := true

//crossScalaVersions := Seq("2.8.1", "2.8.2", "2.9.0", "2.9.1", "2.9.2") //, "2.10.0-RC2")
//crossScalaVersions := Seq("2.9.1") //, "2.10.0-RC2")
//crossVersion := CrossVersion.full

scalaVersion := "2.9.1"


resolvers ++= Seq(
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Sonatype Repository" at "http://oss.sonatype.org/content/repositories/releases"
)

scalaBinaryVersion <<= scalaBinaryVersion { v =>
  if (v.startsWith("2.10"))
    "2.10.0-M7"
  else
    v
}

scalacOptions <++= scalaVersion map { v =>
  if (v.startsWith("2.10"))
    Seq("-deprecation", "-feature", "-language:implicitConversions")
  else
    Seq("-deprecation")
}

libraryDependencies ++= Seq(
  "org.fluentd" % "fluent-logger" % "0.2.7",
  "org.msgpack" %% "msgpack-scala" % "0.6.6",
  "org.scalatest" %% "scalatest" % "1.8" % "test",
  "junit" % "junit" % "4.10" % "test"
)

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
  </developers>
)

publishTo <<= version { v =>
  val nexus = "http://oss.sonatype.org/"
  if (v.endsWith("-SNAPSHOT"))
    Some("snapshots" at nexus+"content/repositories/snapshots")
  else
    Some("releases" at nexus+"service/local/staging/deploy/maven2")
}

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