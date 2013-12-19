//--------------------------------------
//
// LogLevel.scala
// Since: 2013/12/19 3:04 PM
//
//--------------------------------------

package fluent.logger

/**
 * log level definitions
 *
 * @author Taro L. Saito
 */
object LogLevel extends xerial.core.collection.Enum[LogLevel] {

  case object OFF extends LogLevel(0, "off")
  case object ERROR extends LogLevel(1, "error")
  case object WARN extends LogLevel(2, "warn")
  case object INFO extends LogLevel(3, "info")
  case object DEBUG extends LogLevel(4, "debug")
  case object TRACE extends LogLevel(5, "trace")
  case object ALL extends LogLevel(6, "all")

  val values = IndexedSeq(OFF, ERROR, WARN, INFO, DEBUG, TRACE, ALL)
  private lazy val index = values.map { l => l.name.toLowerCase -> l } toMap

  def apply(name: String): LogLevel = {
    val n = name.toLowerCase
    val lv = values.find(n == _.name)
    if (lv.isEmpty) {
      Console.err.println("Unknown log level [%s] Use info log level instead." format (name))
      INFO
    }
    else
      lv.get
  }

  def unapply(name:String) : Option[LogLevel] = index.get(name.toLowerCase)
}

sealed abstract class LogLevel(val order: Int, val name: String) extends Ordered[LogLevel] with Serializable {
  def compare(other: LogLevel) = this.order - other.order
  override def toString = name
}
