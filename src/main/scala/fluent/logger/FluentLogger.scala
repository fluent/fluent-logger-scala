//--------------------------------------
//
// FluentLogger.scala
// Since: 2013/12/18 0:02
//
//--------------------------------------

package fluent.logger

import scala.reflect.ClassTag

object FluentLogger {

  /**
   * Get a tagged logger
   * @param tag
   * @return
   */
  def apply(tag:String) : TaggedFluentLogger = NA

  def apply(tag:String, cls:Class[_]) = {
    NA
  }

  def of[A:ClassTag] : Logger = NA


  /**
   * Not available
   */
  private[logger] def NA = {
    val e = new Throwable()
    val caller = e.getStackTrace()(1)
    throw new UnsupportedOperationException(s"${caller.getMethodName} (${caller.getFileName}:${caller.getLineNumber})")
  }

}


/**
 * Fluent logger interface
 */
trait FluentLogger {

  def currentTime = System.currentTimeMillis() / 1000

  def log[A](tag:String, record:A) : Boolean = log(tag, currentTime, record)
  def log[K, V](tag:String, map:Map[K, V]) : Boolean = log(tag, currentTime, map)
  def json(tag:String, jsonObj:String) : Boolean = json(tag, currentTime, jsonObj)

  def log[A](tag:String, timeStamp:Long, record:A) : Boolean
  def log[K, V](tag:String, timeStamp:Long, map:Map[K, V]) : Boolean
  def json(tag:String, timeStamp:Long, jsonObj:String) : Boolean

}

/**
 * Fluent logger with a tag
 */
trait TaggedFluentLogger extends FluentLogger {

  def tag : String

  def log[A](record:A) : Boolean = log(tag, currentTime, record)
  def log[K, V](map:Map[K, V]) : Boolean = log(tag, currentTime, map)
  def logJson(jsonObj:String) : Boolean = json(tag, currentTime, jsonObj)

  def log[A](timeStamp:Long, record:A) : Boolean = log(tag, timeStamp, record)
  def log[K, V](timeStamp:Long, map:Map[K, V]) : Boolean = log(tag, currentTime, map)
  def logJson(timeStamp:Long, jsonObj:String) : Boolean = json(tag, timeStamp, jsonObj)

}


