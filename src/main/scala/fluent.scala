package org.fluentd.logger.FluentLogger

import akka.serialization._
import akka.serialization.Serializable.ScalaJSON
import akka.serialization.JsonSerialization._
import akka.serialization.DefaultProtocol._

import sjson.json.Serializer._

import scala.collection.mutable
import scala.actors._

import org.jboss.netty._
import org.jboss.netty.buffer.ChannelBuffers._

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.channel.{Channel => NettyChannel}
import org.jboss.netty.channel._
import org.jboss.netty.buffer._
import scala.collection.mutable.ConcurrentMap
import java.util.concurrent.atomic.AtomicLong
import org.jboss.netty.handler.queue.BufferedWriteHandler

import java.util.Date;
import java.util.Calendar;

class FluentClient(h:String, p:Int) extends Actor {
  val host = h
  val port = p
  val handler:FluentClientHandler = createHandler()
  var channel:org.jboss.netty.channel.Channel = connect()

  class FluentClientHandler() extends BufferedWriteHandler {
    val bufferSize:AtomicLong = new AtomicLong();

    override def channelConnected(ctx:ChannelHandlerContext, e:ChannelStateEvent) {
      //println("connected")
    }

    override def writeRequested(ctx:ChannelHandlerContext, e:MessageEvent) {
      super.writeRequested(ctx, e);

      val data:ChannelBuffer = e.getMessage().asInstanceOf[ChannelBuffer];
      val newBufferSize:Long = bufferSize.addAndGet(data.readableBytes());

      // Flush the queue if it gets larger than 8KiB.
      if (newBufferSize > 0) {
        flush();
        bufferSize.set(0);
      }
    }

    override def exceptionCaught(ctx:ChannelHandlerContext , e:ExceptionEvent):Unit = {
      //println("exeception")
      e.getCause().printStackTrace();
      e.getChannel().close();
    }
  }

  def createHandler():FluentClientHandler = {
    return new FluentClientHandler();
  }

  def connect():org.jboss.netty.channel.Channel = {
    val bootstrap = new ClientBootstrap(
        new NioClientSocketChannelFactory(
          Executors.newCachedThreadPool(),
          Executors.newCachedThreadPool()));

    // Set up the pipeline factory.
    bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
          def getPipeline() = Channels.pipeline(handler)
    });

    bootstrap.setOption("tcpNoDelay" , true);
    bootstrap.setOption("keepAlive", true);
    val f = bootstrap.connect(new InetSocketAddress(host, port));
    f.await()

    return f.getChannel()
  }

  def send(bin:Array[Byte]):Int = {
    val buf:ChannelBuffer = ChannelBuffers.buffer(bin.size)
    buf.writeBytes(bin)
    val f = channel.write(buf)

    return 0
  }

  def flush() = {
    handler.flush()
  }

  def close():Unit = {
    channel.close()
  }

  override def toString():String = {
    return channel.toString()
  }

  def act() = {
    react {
      case json:Array[Byte] =>
        send(json)
        act()
      case "close" =>
        close()
      case "flush" =>
        flush()
    }
  }
}

object FluentLogger {
  private var client:FluentClient = null
  private val serializer = sjson.json.Serializer.SJSON
  private val calendar = Calendar.getInstance()

  def open(host:String, port:Int) {
    client = new FluentClient(host, port)
    client.start()
  }

  def close() {
    client ! "close"
  }

  def log(tag:String, m:mutable.Map[String,Any]) {
    val now = calendar.getTimeInMillis()
    val msg = List(tag, now, m)
    val json = serializer.out(msg)
    client ! json
  }

  def flush() {
    client ! "flush"
  }

  def main(args: Array[String]):Unit = {
    val cli = new FluentClient("localhost",24224)
    open("localhost", 24224)
    log("debug.test", mutable.Map("json"->"1"))
    log("debug.test", mutable.Map("json"->"2"))
    log("debug.test", mutable.Map("json"->"3"))
    flush()
    close()
  }
}
