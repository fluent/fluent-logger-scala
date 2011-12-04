name := "fluent-logger-scala"

version := "0.0.1"

scalaVersion := "2.9.1"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "se.scalablesolutions.akka" % "akka-remote" % "1.3-RC1"

//libraryDependencies += "se.scalablesolutions.akka" % "akka-actor" % "1.3-RC1"

libraryDependencies += "org.jboss.netty" % "netty" % "3.2.6.Final"

libraryDependencies += "org.scala-tools.time" % "time_2.8.1" % "0.3"
