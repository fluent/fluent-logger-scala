//
// A Structured Logger for Fluent
//
// Copyright (C) 2012 OZAWA Tsuyoshi
//
//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at
//
//        http://www.apache.org/licenses/LICENSE-2.0
//
//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//
package org.fluentd.logger.scala


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import org.fluentd.logger.util.MockFluentd;
import scala.collection.mutable.HashMap

@RunWith(classOf[JUnitRunner])
class FluentLoggerSuite extends FunSuite with BeforeAndAfter {
  
  // TODO: fix to use mock.
  test("test normal 1"){
    val logger = FluentLoggerFactory.getLogger("debug")
    val data1 = new HashMap[String, String]();
    data1.put("k1", "v1");
    data1.put("k2", "v2");
    logger.log("test01", data1);
    
    val data2 = new HashMap[String, String]();
    data2.put("k3", "v3");
    data2.put("k4", "v4");
    logger.log("test01", data2);
    logger.log("test01", data2, System.currentTimeMillis);
    val data3 = data2.toMap
    logger.log("test01", data3);
    logger.log("test01", data3, System.currentTimeMillis);
    logger.log("test01", "foo", "bar");
    logger.log("test01", "foo", "bar", System.currentTimeMillis);
    
    FluentLoggerFactory.flushAll
    FluentLoggerFactory.closeAll
  }
  
  test("test big hash map") {
    val logger0 = FluentLoggerFactory.getLogger("debug")
    val data = new HashMap[String, String]();
    for (i <- 1 to 100) {
      data.put("k"+i.toString, i.toString);
    }
    logger0.log("test01", data);
    FluentLoggerFactory.flushAll
    FluentLoggerFactory.closeAll
  }
  
  test("test sending lots List objects") {
    val logger0 = FluentLoggerFactory.getLogger("debug")
    val ev = new HashMap[String, List[String]]();
    var list: List[String] = List();
    for (i <- 1 to 100) {
      list = i.toString::list
      ev.put("key1", list)
      logger0.log("test02", ev);
    }
    FluentLoggerFactory.flushAll
    FluentLoggerFactory.closeAll
  }
 
  test("test sending big List object") {
    val logger0 = FluentLoggerFactory.getLogger("debug")
    val ev = new HashMap[String, List[String]]();
    for (i <- 1 to 100) {
      var list: List[String] = List();
   	  for (j <- 1 to 100) {
        list = j.toString::list
      }
      ev.put("key"+i.toString, list)
    }
    logger0.log("test03", ev);
    FluentLoggerFactory.flushAll
    FluentLoggerFactory.closeAll
  }
  
  test("test sending nested Map object") {
    val logger0 = FluentLoggerFactory.getLogger("debug")
    val ev = new HashMap[String, HashMap[String, String]]();
    var innerMap = HashMap[String, String]()
    for (i <- 1 to 100) {
      innerMap.put(i.toString, i.toString)
    }
    ev.put("inner", innerMap)
    logger0.log("test04", ev)
  }
   /*
  test("test sending Set objects") {
    val logger0 = FluentLoggerFactory.getLogger("debug")
    val ev = new HashMap[String, Set[String]]();
    for (i <- 1 to 100) {
      var list: List[String] = List();
   	  for (j <- 1 to 100) {
        list = j.toString::list
      }
      ev.put("key"+i.toString, list.toSet)
    }
    logger0.log("test01", ev);
    FluentLoggerFactory.flushAll
    FluentLoggerFactory.closeAll
  }
  */

  test("close") {
    FluentLoggerFactory.getLogger("tag1");
    FluentLoggerFactory.getLogger("tag2");
    FluentLoggerFactory.getLogger("tag3");
    FluentLoggerFactory.closeAll
  }
}
