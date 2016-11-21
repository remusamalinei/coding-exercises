package ra.rps.domain

import ra.rps.domain.Outcome.{Loss, Tie, Win}
import ra.rps.domain.Shape.{Paper, Rock, Scissors}
import ra.rps.domain.service.ShapeThrower

import org.scalamock.scalatest.MockFactory
import org.scalatest.prop.TableDrivenPropertyChecks.{Table, forAll}
import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Remus Amalinei
  */
class PlayerImplTest extends FlatSpec with Matchers with MockFactory {

  "Player" should "throw an exception when is constructed with invalid params" in {
    val dummyImpl = new ShapeThrower {
      override def next: Shape = Rock
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
      ("shape", "againstShape", "expectedOutcome", "expectedScore"),

      (Rock, Rock, Tie, 0),
      (Rock, Paper, Loss, 0),
      (Rock, Scissors, Win, 1),

      (Paper, Rock, Win, 1),
      (Paper, Paper, Tie, 0),
      (Paper, Scissors, Loss, 0),

      (Scissors, Rock, Loss, 0),
      (Scissors, Paper, Win, 1),
      (Scissors, Scissors, Tie, 0))

    forAll(expectations) { (shape, againstShape, expectedOutcome, expectedScore) =>
      val shapeGeneratorMocked = mock[ShapeThrower]
      (shapeGeneratorMocked.next _).expects().returning(shape)
      val player = new PlayerImpl("test", shapeGeneratorMocked)

      player.nextThrownShape

      player.updateScorePlayingAgainst(againstShape) should be(expectedOutcome)
      player.score should be(expectedScore)
    }
  }
}
