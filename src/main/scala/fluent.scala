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

import java.util.Date;
import java.util.Calendar;

class FluentClientHandler extends SimpleChannelHandler {
  override def channelConnected(ctx:ChannelHandlerContext, e:ChannelStateEvent) {
    //println("connected")
  }

  override def channelClosed(ctx:ChannelHandlerContext , e:ChannelStateEvent ) {
    //println("closed")
  }

  override def writeComplete(ctx:ChannelHandlerContext, e:WriteCompletionEvent) = {
    //println("write complete")
  }

  override def messageReceived(ctx:ChannelHandlerContext, e:MessageEvent):Unit = {
    //println("received")
    e.getMessage();
    e.getChannel().close();
  }
  override def exceptionCaught(ctx:ChannelHandlerContext , e:ExceptionEvent):Unit = {
    //println("exeception")
    e.getCause().printStackTrace();
    e.getChannel().close();
  }
}

class FluentClient(h:String, p:Int) extends Actor {
  val host = h
  val port = p
  var channel:org.jboss.netty.channel.Channel = connect()

  def connect():org.jboss.netty.channel.Channel = {
    val bootstrap = new ClientBootstrap(
        new NioClientSocketChannelFactory(
          Executors.newCachedThreadPool(),
          Executors.newCachedThreadPool()));

    // Set up the pipeline factory.
    bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
          def getPipeline() = Channels.pipeline(new FluentClientHandler())
    });

    bootstrap.setOption("tcpNoDelay" , true);
    bootstrap.setOption("keepAlive", true);
    val f = bootstrap.connect(new InetSocketAddress(host, port));
    f.await()

    return f.getChannel()
  }

  def send(bin:Array[Byte]):Int = {
    println("write " + bin.size)
    val buf:ChannelBuffer = ChannelBuffers.buffer(bin.size)
    buf.writeBytes(bin)
    val f = channel.write(buf)
    f.await()
    println("wrote?")

    //buf.write
    //val f = channel.write(buf)
    //f.await()
    return 0
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

  def main(args: Array[String]):Unit = {
    val cli = new FluentClient("localhost",24224)
    open("localhost", 24224)
    log("debug.test", mutable.Map("json"->"message"))
    close()
  }
}
