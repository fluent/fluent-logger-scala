fluent-logger-scala [![Build Status](https://travis-ci.org/fluent/fluent-logger-scala.svg?branch=develop)](https://travis-ci.org/fluent/fluent-logger-scala)
![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.fluentd/fluent-logger-scala_2.12/badge.svg) [![Scaladoc](http://javadoc-badge.appspot.com/org.fluentd/fluent-logger-scala_2.12.svg?label=scaladoc)](http://javadoc-badge.appspot.com/org.fluentd/fluent-logger-scala_2.12)

====
Fluentd logger for Scala

Many web/mobile applications generate huge amount of event logs (c,f. login, logout, purchase, follow, etc). To analyze these event logs could be really valuable for improving the service. However, the challenge is collecting these logs easily and reliably.

[Fluentd](http://github.com/fluent/fluentd) solves that problem by having: easy installation, small footprint, plugins, reliable buffering, log forwarding, etc.

**fluent-logger-scala** is a Scala library, to record the events from Scala application,  based on fluent-logger-java.
Main difference between scala and java version is to support Scala Collection.

Please see [QuickStart](https://github.com/fluent/fluent-logger-scala/wiki/QuickStart) to get started!

## Usage

```scala
# For Scala 2.11 and 2.12 (Since fluent-logger-scala 0.7.0)
libraryDependencies += "org.fluentd" %% "fluent-logger-scala" % "(version)"

# For Scala 2.10 users (deprecated)
libraryDependencies += "org.fluentd" %% "fluent-logger-scala" % "0.6.0"
```

### API

fluent-logger-scala API is based on fluent-logger-java:

```
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
```

## For Developers

```
$ ./sbt
> compile
> test
# cross Scala version testing
> + test
```

### Publishing to Maven Central

Describe your account information in $HOME/.sbt/(sbt-version)/sonatype.sbt file:

    credentials += Credentials("Sonatype Nexus Repository Manager",
        "oss.sonatype.org",
        "(Sonatype user name)",
        "(Sonatype password)")

The release command will publish signed artifacts to the Sonatype repository, and perform releasing to Maven Central:

    ./sbt release

## License and Copyright

* Copyright © 2011- Tsuyoshi Ozawa and Taro L. Saito
* Apache License, Version 2.0

## Misc

This document is inspired by [fluent-logger-python](https://github.com/fluent/fluent-logger-python)
