//--------------------------------------
//
// FluentLogger.scala
// Since: 2013/12/18 0:02
//
//--------------------------------------

package fluent



/**
 * Fluent logger interface
 */
trait FluentLogger {

  def currentTime = System.currentTimeMillis() / 1000

  def log[A](tag:String, record:A) = log(tag, currentTime, record)
  def json(tag:String, jsonObj:String) : Boolean = json(tag, currentTime, jsonObj)
  def msgpack(tag:String, msgpackRecord:Array[Byte]) : Boolean = msgpack(tag, currentTime, msgpackRecord)

  def log[A](tag:String, timeStamp:Long, record:A) : Boolean
  def json(tag:String, timeStamp:Long, jsonObj:String) : Boolean
  def msgpack(tag:String, timeStamp:Long, msgpackRecord:Array[Byte]) : Boolean

}

/**
 * Fluent logger with a tag
 */
trait TaggedFluentLogger extends FluentLogger {

  def tag : String

  def log[A](record:A) : Boolean = log(tag, currentTime, record)
  def json(jsonRecord:String) : Boolean = json(tag, currentTime, jsonRecord)

  def log[A](timeStamp:Long, record:A) : Boolean = log(tag, timeStamp, record)
  def json(timeStamp:Long, jsonObj:String) : Boolean = json(tag, timeStamp, jsonObj)

}

