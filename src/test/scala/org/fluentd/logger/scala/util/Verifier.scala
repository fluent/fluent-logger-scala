package org.fluentd.logger.scala.util

import org.fluentd.logger.scala.sender.Event
import net.liftweb.json.JsonAST.JValue

trait Verifier {
  def verify(event: Event)
}