package org.fluentd.logger.scala.sender

import net.liftweb.json.Serializer
import net.liftweb.json.Formats
import net.liftweb.json.JsonAST.JObject
import net.liftweb.json.JsonAST.JField
import net.liftweb.json.Extraction
import net.liftweb.json.JsonAST.JValue
import net.liftweb.json.TypeInfo
import net.liftweb.json.JsonAST.JArray
import net.liftweb.json.JsonAST.JInt
import net.liftweb.json.JsonAST.JString
import net.liftweb.json.JsonAST.JNothing
import net.liftweb.json.MappingException

object EventSerializer extends Serializer[Event] {
  private val EventClass = classOf[Event]
  
  def serialize(implicit format: Formats): PartialFunction[Any, JValue] = {
    case ev: Event =>
      JArray(List(JString(ev.tag), JInt(ev.time), Extraction.decompose(ev.record)))
  }

  /* This method is only used for testing. */
  def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), Event] = {
    case (TypeInfo(EventClass, _), json) => json match {
      case JArray(JString(tag) :: JInt(time) :: JObject(obj) :: Nil) =>
        new Event(tag, time.toLong, null)
      case JNothing =>
        new Event(null, 0, null)
      case x => throw new MappingException("Can't convert " + x + " to Event")
    }
  }

}