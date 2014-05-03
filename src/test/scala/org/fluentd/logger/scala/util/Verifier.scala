package org.fluentd.logger.scala.util

import org.fluentd.logger.scala.sender.Event

trait Verifier {
  def verify(event: Event)
}
