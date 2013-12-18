//--------------------------------------
//
// FluentLogger.scala
// Since: 2013/12/18 0:02
//
//--------------------------------------

package fluent.logger

object FluentLogger {

  def getLogger(tag:String) : TaggedFluentLogger = NA




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
  def msgpack(tag:String, msgpackRecord:Array[Byte]) : Boolean = msgpack(tag, currentTime, msgpackRecord)

  def log[A](tag:String, timeStamp:Long, record:A) : Boolean
  def log[K, V](tag:String, timeStamp:Long, map:Map[K, V]) : Boolean
  def json(tag:String, timeStamp:Long, jsonObj:String) : Boolean
  def msgpack(tag:String, timeStamp:Long, msgpackRecord:Array[Byte]) : Boolean

}

/**
 * Fluent logger with a tag
 */
trait TaggedFluentLogger extends FluentLogger {

  def tag : String

  def log[A](record:A) : Boolean = log(tag, currentTime, record)
  def log[K, V](map:Map[K, V]) : Boolean = log(tag, currentTime, map)
  def json(jsonRecord:String) : Boolean = json(tag, currentTime, jsonRecord)

  def log[A](timeStamp:Long, record:A) : Boolean = log(tag, timeStamp, record)
  def log[K, V](timeStamp:Long, map:Map[K, V]) : Boolean = log(tag, currentTime, map)
  def json(timeStamp:Long, jsonObj:String) : Boolean = json(tag, timeStamp, jsonObj)

}


/**
 * Add logging features to the class extending this trait
 */
trait Logging {

  protected def tag : String = "log"

  protected def fatal(message:String) : Unit
  protected def error(message:String) : Unit
  protected def warn(message:String) : Unit
  protected def info(message:String) : Unit
  protected def debug(message:String) : Unit
  protected def trace(message:String) : Unit

}


