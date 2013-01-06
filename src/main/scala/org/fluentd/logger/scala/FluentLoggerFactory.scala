//
// A Structured Logger for Fluent
//
// Copyright (C) 2012 OZAWA Tsuyoshi
//
//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at
//
//        http://www.apache.org/licenses/LICENSE-2.0
//
//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//
package org.fluentd.logger.scala

import scala.collection.JavaConversions._
import org.fluentd.logger.{FluentLogger => JavaFluentLogger}
import org.fluentd.logger.{FluentLoggerFactory => JavaFluentLoggerFactory}
import org.fluentd.logger.{Constants => JavaConstants}


object FluentLoggerFactory {
  implicit def wrap(logger: JavaFluentLogger): FluentLogger = new FluentLogger(logger)
  val senderPropertyName = JavaConstants.FLUENT_SENDER_CLASS
  val scalaSenderName = "RawSocketSender"
  val senderClassName = 
    if (System.getProperties().containsKey(senderPropertyName))
      System.getProperty(senderPropertyName)
    else 
      scalaSenderName
  val factory = new JavaFluentLoggerFactory()
  
  def getLogger(tag: String): FluentLogger = {
    getLogger(tag, "localhost", 24224)
  }
  
  def getLogger(tag: String, host: String, port: Int): FluentLogger = {
    getLogger(tag, host, port, 3 * 1000, 1 * 1024 * 1024)
  }
  
  def getLogger(tag: String, host: String, port: Int,
      timeout: Int, bufferCapacity: Int): FluentLogger = {
    factory.getLogger(tag, host, port, timeout, bufferCapacity)
  }
  
  def flushAll() = factory.flushAll()
  def closeAll() = factory.closeAll()
  
}