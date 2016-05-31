package ra.rps.controller

import ra.rps.domain.Outcome.{Loss, Win}
import ra.rps.domain.RoundResult
import ra.rps.domain.Shape.{Paper, Rock}
import ra.rps.application.GameService

import org.junit.runner.RunWith
import org.scalamock.scalatest.MockFactory
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Remus Amalinei
  */
@RunWith(classOf[JUnitRunner])
class GameControllerTest extends FlatSpec with Matchers with MockFactory {

  "GameController" should "delegate play to GameService" in {
    val expectedRoundResults = (
      new RoundResult(Rock, Loss, "a", 0),
      new RoundResult(Paper, Win, "b", 1))

    val gameServiceMocked = mock[GameService]
    (gameServiceMocked.play _).expects().returning(expectedRoundResults)

    val gameController = new GameController(gameServiceMocked)

    gameController.play should be(expectedRoundResults)
  }
}
