package org.fluentd.logger.scala.sender

import net.liftweb.json.Serializer
import net.liftweb.json.Formats
import net.liftweb.json.JsonAST.JValue
import net.liftweb.json.JsonAST.JObject
import net.liftweb.json.JsonAST.JField
import net.liftweb.json.Extraction
import net.liftweb.json.TypeInfo
import scala.collection.mutable.{Map => MutableMap}
import net.liftweb.json.MappingException

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