# fluent-logger-scala -A Scala structured logger for Fluent-

Many web/mobile applications generate huge amount of event logs (c,f. login, logout, purchase, follow, etc). To analyze these event logs could be really valuable for improving the service. However, the challenge is collecting these logs easily and reliably.

[Fluent](http://github.com/fluent/fluent) solves that problem by having: easy installation, small footprint, plugins, reliable buffering, log forwarding, etc.

**fluent-logger-scala** is a Scala library, to record the events from Scala application.

(NOTE: This document is quated from [fluent-logger-python](https://github.com/fluent/fluent-logger-python) )

## Requirement

sbt(Simple Build Tool) 0.11.x is required.

## Build

    sbt update
    sbt compile

## Installation

maven repository will be available, but currently no support.

## API

Simple APIs are provided.

* open(host:String, port:Int)
* close()
* log(tag:String, Map[String,Any])


## License

Apache License, Version 2.0
