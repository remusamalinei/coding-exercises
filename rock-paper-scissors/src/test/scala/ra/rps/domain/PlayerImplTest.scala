package ra.rps.domain

import ra.rps.domain.Shape.Rock
import ra.rps.domain.service.ShapeThrower

import org.junit.runner.RunWith
import org.scalamock.scalatest.MockFactory
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.TableDrivenPropertyChecks.{Table, forAll}
import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Remus Amalinei
  */
@RunWith(classOf[JUnitRunner])
class PlayerImplTest extends FlatSpec with Matchers with MockFactory {

  "Player" should "throw an exception when is constructed with invalid params" in {
    val dummyImpl = new ShapeThrower {
      override def next(): Shape = Rock
    }

    val invalidParams = Table(
      ("name", "shapeGenerator"),
      ("       ", dummyImpl),
      ("_16_characters__", dummyImpl))

    forAll(invalidParams) { (name, shapeGenerator) =>
      an[IllegalArgumentException] should be thrownBy new PlayerImpl(name, shapeGenerator)
    }
  }

  it should "use the ShapeThrower to generate the next thrown shape" in {
    val shapeGeneratorMocked = mock[ShapeThrower]
    (shapeGeneratorMocked.next _).expects().returning(Rock)

    val player = new PlayerImpl("test", shapeGeneratorMocked)

    player.nextThrownShape should be(Rock)
  }

  it should "have the score incremented only when they win" in {
    val expectations = Table(
      ("againstShape", "expectedOutcome", "expectedScore"),
      (Rock, Outcome.Tie, 0),
      (Shape.Paper, Outcome.Loss, 0),
      (Shape.Scissors, Outcome.Win, 1))

    forAll(expectations) { (againstShape, expectedOutcome, expectedScore) =>
      val shapeGeneratorMocked = mock[ShapeThrower]
      (shapeGeneratorMocked.next _).expects().returning(Rock)
      val player = new PlayerImpl("test", shapeGeneratorMocked)

      player.nextThrownShape

      player.updateScorePlayingAgainst(againstShape) should be(expectedOutcome)
      player.score should be(expectedScore)
    }
  }
}
