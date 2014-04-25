package org.fluentd.logger.scala.sender

import org.json4s.Serializer
import org.json4s.Formats
import org.json4s.JsonAST.JValue
import org.json4s.JsonAST.JObject
import org.json4s.JsonAST.JField
import org.json4s.Extraction
import org.json4s.TypeInfo
import scala.collection.mutable.{Map => MutableMap}
import org.json4s.MappingException

object MapSerializer extends Serializer[Map[String, Any]] {
  def serialize(implicit format: Formats): PartialFunction[Any, JValue] = {
    case m: Map[_, _] => 
      JObject(m.toMap.map({
      case (k, v) => JField(
        k match {
          case ks: String => ks
          case ks: Symbol => ks.name
          case ks: Any => ks.toString
        },
        Extraction.decompose(
          v match {
            case vs: MutableMap[_,_] => vs.toMap
            case _ => v
          }
        )
      )
    }).toList)
  }

  case class AnyMap(values: Map[String, Any]) 
  def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), Map[String, Any]] = {
    case x => throw new MappingException("Can't convert " + x + " to Event")
  }
}
