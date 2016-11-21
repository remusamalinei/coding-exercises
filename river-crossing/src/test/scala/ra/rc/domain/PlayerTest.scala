package ra.rc.domain

import ra.rc.domain.ConstantPlayers._
import org.scalatest.{FlatSpec, Matchers}

/**
 * @author Remus Amalinei
 */
class PlayerTest extends FlatSpec with Matchers {

  "A Player" should "injure a player" in {
    Wolf injures Goat should be (true)
  }

  it should "defend a player" in {
   Farmer defendsPlayerAgainstInjurer (Goat, Wolf) should be (true)
  }

  it should "fail to defend another player" in {
    Farmer defendsPlayerAgainstInjurer (Goat, Bear) should be (false)
  }

  it should "not both injure and defend the same player" in {
    an [IllegalArgumentException] should be thrownBy Player("test")(Set(Goat), Map(Goat -> Wolf))
  }
}
