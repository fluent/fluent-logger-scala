# fluent-logger-scala -A Scala structured logger for Fluentd-

Many web/mobile applications generate huge amount of event logs (c,f. login, logout, purchase, follow, etc). To analyze these event logs could be really valuable for improving the service. However, the challenge is collecting these logs easily and reliably.


[Fluentd](http://github.com/fluent/fluentd) solves that problem by having: easy installation, small footprint, plugins, reliable buffering, log forwarding, etc.

**fluent-logger-scala** is a Scala library, to record the events from Scala application,  based on fluent-logger-java.
Main difference between scala and java version is to support Scala Collection.

Please see [QuickStart](https://github.com/oza/fluent-logger-scala/wiki/QuickStart) to get started!

## Requirement

* Scala Compiler 2.9.0, 2.9.1, 2.9.2
* sbt(Simple Build Tool) 0.12.x
* fluent-logger-java 0.2.6 or later

## Build

    sbt update
    sbt compile

## Test

    sbt test

## Installation

Add sonatype repository and dependencies to build.sbt file as follows:

    resolvers += "Apache Maven Central Repository" at "http://repo.maven.apache.org/maven2/"
    
    libraryDependencies += "org.fluentd" % "fluent-logger-scala_<scala_version>" % "0.3.0"
    

Please replace <scala_version> with scala compiler version you use.
Currently, <scala_version> can be replaced with 2.9.0, 2.9.1, 2.9.2.
For instance,

    libraryDependencies += "org.fluentd" % "fluent-logger-scala_2.9.0" % "0.3.0"

or

    libraryDependencies += "org.fluentd" % "fluent-logger-scala_2.9.1" % "0.3.0"

or

    libraryDependencies += "org.fluentd" % "fluent-logger-scala_2.9.2" % "0.3.0"



## API

APIs base on fluent-logger-java.

    FluentLoggerFactory#getLogger(tag: String): FluentLogger
    FluentLoggerFactory#getLogger(tag: String, host: String, port: Int): FluentLogger
    FluentLoggerFactory#getLogger(tag: String, host: String, port: Int, timeout: Int, bufferCapacity: Int): FluentLogger
    FluentLoggerFactory#flushAll(): Unit
    FluentLoggerFactory#closeAll(): Unit

    FluentLogger#log(label: String, key: String, value: Any): Boolean
    FluentLogger#log(label: String, key: String, value: Any, timestamp: Long): Boolean
    FluentLogger#log(tag:String, mutableMap/immutableMap[String, Any]):Boolean
    FluentLogger#flush()
    FluentLogger#close()


## TODOs

* Set support(This is WIP).
* Handling Java objects and Scala objects transparently.

## License and Copyright

* Copyright © 2011- Tsuyoshi Ozawa
* Apache License, Version 2.0

## Misc

This document is inspired by [fluent-logger-python](https://github.com/fluent/fluent-logger-python)
