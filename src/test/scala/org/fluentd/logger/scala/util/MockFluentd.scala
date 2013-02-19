package org.fluentd.logger.scala.util

import java.net.ServerSocket
import java.net.InetSocketAddress
import net.liftweb.json.Serialization
import org.fluentd.logger.scala.sender.Event
import net.liftweb.json.NoTypeHints
import org.fluentd.logger.scala.sender.EventSerializer
import net.liftweb.json.JsonParser
import org.fluentd.logger.scala.sender.MapSerializer
import net.liftweb.json.JsonAST.JObject
import scala.util.parsing.json.JSON
import scala.actors.Actor
import java.io.BufferedInputStream
import java.io.InputStreamReader
import java.io.BufferedReader
import java.util.ArrayList
import java.util.concurrent.atomic.AtomicBoolean
import java.io.IOException

case class MockFluentd(port: Int, verifier: Verifier) extends Actor {
  implicit val formats = Serialization.formats(NoTypeHints) + EventSerializer + MapSerializer
  val serverSocket = new ServerSocket(port);
  val finished = new AtomicBoolean(false)
 
  case class Element(tag:String, time:Long, params:JObject)
  
  def act() = {
	loopWhile(!finished.get()) {
      try {
        val socket = serverSocket.accept();
        val in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        val processor = new Runnable {
          override def run() {
            val ev = Serialization.read[Event](in)
            verifier.verify(ev)
          }
        }
        processor.run
      } catch {
        case e:IOException =>  ;
      }
      
	  receive {
	    case "EXIT" => 
          if (serverSocket != null) {
            serverSocket.close();
          }  
          finished.set(true);
	  }
    }
        
  }

  def close = {
    this ! "EXIT"
    while(!finished.get()){}
  }
}

object MockFluentdFactory {
  var fluentds = new ArrayList[MockFluentd]()

  def create(port: Int, verifier: Verifier) = {
    val fluentd = new MockFluentd(port, verifier)
    fluentd.start
    fluentds.add(fluentd)
  }
    
  def stop() {
    val itr = fluentds.iterator()
    while (itr.hasNext()) {
      val fluentd = itr.next()
      fluentd.close
    }
  }
}