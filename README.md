# fluent-logger-scala -A Scala structured logger for Fluentd-

Many web/mobile applications generate huge amount of event logs (c,f. login, logout, purchase, follow, etc). To analyze these event logs could be really valuable for improving the service. However, the challenge is collecting these logs easily and reliably.


[Fluentd](http://github.com/fluent/fluentd) solves that problem by having: easy installation, small footprint, plugins, reliable buffering, log forwarding, etc.

**fluent-logger-scala** is a Scala library, to record the events from Scala application,  based on fluent-logger-java.
Main difference between scala and java version is to support Scala Collection.


## Requirement

* sbt(Simple Build Tool) 0.11.x
* fluent-logger-java 0.2.6 or later

## Build

    sbt update
    sbt compile

## Test

    sbt test

## Installation

maven repository will be available, but currently no support.

## API

APIs base on fluent-logger-java.

    FluentLoggerFactory#getLogger(tag: String): FluentLogger
    FluentLoggerFactory#getLogger(tag: String, host: String, port: Int): FluentLogger
    FluentLoggerFactory#getLogger(tag: String, host: String, port: Int, timeout: Int, bufferCapacity: Int): FluentLogger
    FluentLoggerFactory#flushAll(): Unit
    FluentLoggerFactory#closeAll(): Unit

    FluentLogger#log(label: String, key: String, value: Object): Boolean
    FluentLogger#log(label: String, key: String, value: Object, timestamp: Long): Boolean
    FluentLogger#log(tag:String, mutableMap/immutableMap[String, Object]):Boolean
    FluentLogger#flush()
    FluentLogger#close()


## TODOs

* Add tests by using mocks
* maven support

## License

Apache License, Version 2.0

## Misc

This document is inspired by [fluent-logger-python](https://github.com/fluent/fluent-logger-python)
