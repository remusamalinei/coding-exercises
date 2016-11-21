package ra.rps.application

import ra.rps.domain.Outcome.{Loss, Win}
import ra.rps.domain.Shape.{Paper, Rock}
import ra.rps.domain.{Player, RoundResult}

import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Remus Amalinei
  */
class GameServiceImplTest extends FlatSpec with Matchers with MockFactory {

  "The game" should "be played by delegating to domain" in {
    val expectedRoundResults = (
      RoundResult(Rock, Loss, "a", 0),
      RoundResult(Paper, Win, "b", 1))

    val playerAMocked = mock[Player]
    (playerAMocked.nextThrownShape _).expects().returning(Rock)
    (playerAMocked.updateScorePlayingAgainst _).expects(Paper).returning(Loss)
    (playerAMocked.name _).expects().returning("a")
    (playerAMocked.score _).expects().returning(0)

    val playerBMocked = mock[Player]
    (playerBMocked.nextThrownShape _).expects().returning(Paper)
    (playerBMocked.updateScorePlayingAgainst _).expects(Rock).returning(Win)
    (playerBMocked.name _).expects().returning("b")
    (playerBMocked.score _).expects().returning(1)

    val gameService = new GameServiceImpl(playerAMocked, playerBMocked)

    gameService.play should be(expectedRoundResults)
  }
}
