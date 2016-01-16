package org.fluentd.logger.scala.sender

import scala.collection.Map

case class Event(tag: String, time: Long, record: Map[String, Any])
