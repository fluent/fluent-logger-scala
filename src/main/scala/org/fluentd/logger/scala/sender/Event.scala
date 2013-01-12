package org.fluentd.logger.scala.sender

import org.msgpack.annotation.Message
import scala.collection.Map

@Message
class Event(k: String, ts: Long, d: Map[String, AnyRef]) {
  val key: String = k
  val timestamp: Long = ts
  val data: Map[String, AnyRef] = d
}