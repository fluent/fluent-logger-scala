//--------------------------------------
//
// FluentLoggerTest.scala
// Since: 2013/12/18 10:18 AM
//
//--------------------------------------

package fluent.logger

import fluent.FluentSpec

object FluentLoggerTest {

//  class Sample extends Logging {
//
//  }

}


/**
 * @author Taro L. Saito
 */
class FluentLoggerTest extends FluentSpec {

  "FluentLogger" should {

    "have factory methods" in {

      FluentLogger.getLogger("log")


    }

  }

}