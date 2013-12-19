//--------------------------------------
//
// Logger.scala
// Since: 2013/12/19 3:34 PM
//
//--------------------------------------

package fluent.logger


trait Logger {

  def log(level:LogLevel, message: => String)
  def log(level:LogLevel, message: => String, e:Throwable)

  def error(message: =>String) : Unit = log(LogLevel.ERROR, message)
  def error(message: =>String, e:Throwable) : Unit = log(LogLevel.ERROR, message, e)
  def warn(message: =>String) : Unit = log(LogLevel.WARN, message)
  def info(message: =>String) : Unit = log(LogLevel.INFO, message)
  def debug(message: =>String) : Unit = log(LogLevel.DEBUG, message)
  def trace(message: =>String) : Unit = log(LogLevel.TRACE, message)

}


/**
 * Add logging features to the class extending this trait
 */
trait Logging {

  protected def tag : String = "log"
  private[this] val _logger : Logger = FluentLogger(tag, this.getClass)

  protected def error(message: =>String) : Unit = _logger.error(message)
  protected def error(message: =>String, e:Throwable) : Unit = _logger.error(message, e)
  protected def warn(message: =>String) : Unit = _logger.warn(message)
  protected def info(message: =>String) : Unit = _logger.info(message)
  protected def debug(message: =>String) : Unit = _logger.debug(message)
  protected def trace(message: =>String) : Unit = _logger.trace(message)

}


