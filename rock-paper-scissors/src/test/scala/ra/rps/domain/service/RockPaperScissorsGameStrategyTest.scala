package ra.rps.domain.service

import ra.rps.domain.Outcome.{Loss, Tie, Win}
import ra.rps.domain.Shape.{Paper, Rock, Scissors}

import org.scalatest.prop.TableDrivenPropertyChecks.{Table, forAll}
import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Remus Amalinei
  */
class RockPaperScissorsGameStrategyTest extends FlatSpec with Matchers {

  val ExpectedShapes = Set(Rock, Paper, Scissors)

  "RockPaperScissorsGameStrategy" should "contain only rock, paper and scissors shapes" in {
    val gameStrategy = new RockPaperScissorsGameStrategy()

    gameStrategy.shapes should be(ExpectedShapes)
  }

  it should "model a zero sum game with the shapes it contains" in {
    val shapes = Table("s", Rock, Paper, Scissors)
    val expectedOutcome = Set(Loss, Tie, Win)
    val gameStrategy = new RockPaperScissorsGameStrategy()

    forAll(shapes) { s =>
      val oneShapeAgainstAllShapesOutcome = ExpectedShapes.map {
        other => s.against(other)
      }

      oneShapeAgainstAllShapesOutcome should be(expectedOutcome)
    }
  }
}
