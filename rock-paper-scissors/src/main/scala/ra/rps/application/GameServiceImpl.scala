package ra.rps.application

import ra.rps.domain.{Player, RoundResult}

/**
  * @author Remus Amalinei
  */
class GameServiceImpl
(
  private val playerA: Player,
  private val playerB: Player) extends GameService {

  override def play: (RoundResult, RoundResult) = {
    val shapeA = playerA.nextThrownShape
    val shapeB = playerB.nextThrownShape

    val outcomeForPlayerA = playerA.updateScorePlayingAgainst(shapeB)
    val outcomeForPlayerB = playerB.updateScorePlayingAgainst(shapeA)

    (
      RoundResult(shapeA, outcomeForPlayerA, playerA.name, playerA.score),
      RoundResult(shapeB, outcomeForPlayerB, playerB.name, playerB.score))
  }
}
