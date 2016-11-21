package ra.tr.domain

import org.scalatest.{FlatSpec, Matchers}

/**
 * @author Remus Amalinei
 */
class PlayerTest extends FlatSpec with Matchers {

  "A Player" should "not be constructed with a null name" in {
    an[IllegalArgumentException] should be thrownBy Player(null)
  }

  it should "not be constructed with an empty name" in {
    an[IllegalArgumentException] should be thrownBy Player(" ")
  }
}
