package ra.topranker.domain

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

/**
 * @author Remus Amalinei
 */
@RunWith(classOf[JUnitRunner])
class PlayerTest extends FlatSpec with Matchers {

  "A Player" should "not be constructed with a null name" in {
    an [IllegalArgumentException] should be thrownBy Player(null)
  }

  it should "not be constructed with an empty name" in {
    an [IllegalArgumentException] should be thrownBy Player(" ")
  }
}
