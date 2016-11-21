package ra.rps.infrastructure

import ra.rps.domain.Shape.Rock
import ra.rps.domain.service.GameStrategy
import ra.rps.infrastructure.random.IntGenerator

import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

/**
 * @author Remus Amalinei
 */
class RandomShapeThrowerTest extends FlatSpec with Matchers with MockFactory {

  "RandomShapeThrower" should "offer next shapes using the IntGenerator" in {
    val gameStrategyMocked = mock[GameStrategy]
    (gameStrategyMocked.shapes _).expects().returning(Set(Rock))

    val intGeneratorMocked = mock[IntGenerator]
    (intGeneratorMocked.next _).expects(1).returning(0)

    val shapeThrower = new RandomShapeThrower(gameStrategyMocked, intGeneratorMocked)

    shapeThrower.next should be(Rock)
  }
}
