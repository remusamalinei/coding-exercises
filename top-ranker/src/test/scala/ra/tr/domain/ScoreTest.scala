package ra.tr.domain

import org.scalatest.{FlatSpec, Matchers}

/**
 * @author Remus Amalinei
 */
class ScoreTest extends FlatSpec with Matchers {

  "A Score" should "not be constructed with a null player" in {
    an[IllegalArgumentException] should be thrownBy Score(null, 1)
  }

  it should "not be constructed with a negative score value" in {
    an[IllegalArgumentException] should be thrownBy Score(Player("test"), -1)
  }
}
