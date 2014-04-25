package org.fluentd.logger.scala.sender

import org.json4s.Serializer
import org.json4s.Formats
import org.json4s.JsonAST.JObject
import org.json4s.JsonAST.JField
import org.json4s.Extraction
import org.json4s.JsonAST.JValue
import org.json4s.TypeInfo
import org.json4s.JsonAST.JArray
import org.json4s.JsonAST.JInt
import org.json4s.JsonAST.JString
import org.json4s.JsonAST.JNothing
import org.json4s.MappingException

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
