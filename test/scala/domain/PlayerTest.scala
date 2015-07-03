package domain

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

/**
 * @author Remus Amalinei
 */
@RunWith(classOf[JUnitRunner])
class PlayerTest extends FlatSpec with Matchers {

  def fixture =
    new {
      val goat = Player("goat")()
      val wolf = Player("wolf")(injuredPlayerSet = Set(goat))
      val bear = Player("bear")(injuredPlayerSet = Set(goat))
      val farmer = Player("farmer")(defendedPlayerMap = Map(goat -> wolf))
    }

  "A Player" should "injure a player" in {
    val f = fixture

    f.wolf injures (f.goat) should be (true)
  }

  it should "defend a player" in {
    val f = fixture

    f.farmer defendsPlayerAgainstInjurer (f.goat, f.wolf) should be (true)
  }

  it should "fail to defend another player" in {
    val f = fixture

    f.farmer defendsPlayerAgainstInjurer (f.goat, f.bear) should be (false)
  }

  it should "not both injure and defend the same player" in {
    val f = fixture

    an [IllegalArgumentException] should be thrownBy Player("test")(Set(f.goat), Map(f.goat -> f.wolf))
  }
}
