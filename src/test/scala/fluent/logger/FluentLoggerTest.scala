//--------------------------------------
//
// FluentLoggerTest.scala
// Since: 2013/12/18 10:18 AM
//
//--------------------------------------

package fluent.logger

import fluent.FluentSpec

object FluentLoggerTest {

  class Sample {

  }

  class Sample2 extends Logging {

  }
}

import FluentLoggerTest._


/**
 * @author Taro L. Saito
 */
class FluentLoggerTest extends FluentSpec {

  "FluentLogger" should {

    "have factory methods" in {

      FluentLogger("log")
      FluentLogger.of[Sample]


    }

  }

}