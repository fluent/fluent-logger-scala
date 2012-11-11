name := "fluent-logger-scala"

version := "0.1.0"

scalaVersion := "2.9.2"
//scalaVersion := "2.10.0-RC1"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "Fluentd Repository" at "http://fluentd.org/maven2"

scalacOptions += "-deprecation"

//scalacOptions += "-feature"

// This version depends on fluet-logger-scala 0.2.6, but currently no repository.
// Please compile fluent-logger-java 0.2.6 and use its jar file.
//libraryDependencies += "org.fluentd" % "fluent-logger" % "0.2.6"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.8" % "test"

libraryDependencies += "junit" % "junit" % "4.10" % "test"
