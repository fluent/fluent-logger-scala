//--------------------------------------
//
// FluentSpec.scala
// Since: 2013/12/18 10:18 AM
//
//--------------------------------------

package fluent

import org.scalatest._
import xerial.core.io.Resource
import xerial.core.util.Timer
import xerial.core.log.Logger


/**
 * @author Taro L. Saito
 */
trait FluentSpec extends WordSpec with GivenWhenThen with OptionValues with Resource with Timer with Logger with BeforeAndAfter

