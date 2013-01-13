package org.fluentd.logger.scala

import org.fluentd.logger.{FluentLogger => JavaFluentLogger}
//import scala.collection.immutable.{Map => ImmutableMap}
//import scala.collection.mutable.{Map => MutableMap}
import scala.collection.JavaConversions._
import org.fluentd.logger.scala.sender.Sender
import org.fluentd.logger.scala.sender.ScalaRawSocketSender
import scala.collection.Map;

class FluentLogger(t :String, s: Sender) {
  val tag = t
  val sender = s
  
  def log(label: String, key: String, value: Any): Boolean = {
    log(label, key, value, 0);
  }
  
  def log(label: String, key: String, value: Any, timestamp: Long): Boolean = {
    log(label, Map(key -> value), timestamp);
  }
  
  def log(label: String, data: Map[String, Any]): Boolean = {
    log(label, data, 0)
  }
  
  def log(label: String, data: Map[String, Any], timestamp: Long): Boolean = {
    sender.emit(tag, timestamp, data)
  }
  
  def flush() = sender.flush()
  
  def close() = sender.close()
  
  def getName: String = sender.getName()
  
  override def toString: String = sender.toString()
  
  override def finalize = sender.close
  
}
