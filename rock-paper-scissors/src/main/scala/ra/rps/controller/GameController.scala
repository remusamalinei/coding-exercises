package ra.rps.controller

import ra.rps.domain.{PlayerImpl, RoundResult}
import ra.rps.domain.service.RockPaperScissorsGameStrategy
import ra.rps.infrastructure.{InputStreamShapeThrower, RandomShapeThrower}
import ra.rps.infrastructure.random.RandomIntGenerator
import ra.rps.application.{GameService, GameServiceImpl}

import java.io.InputStream

/**
  * @author Remus Amalinei
  */
class GameController private[controller](val gameService: GameService) {

  def play: (RoundResult, RoundResult) = gameService.play
}

object GameController {

  def newHumanVsComputerGameController(humanPlayerName: String, inputStream: InputStream): GameController = {
    val gameStrategy = new RockPaperScissorsGameStrategy()
    val humanShapeThrower = new InputStreamShapeThrower(gameStrategy, inputStream)
    val computerShapeThrower = new RandomShapeThrower(gameStrategy, new RandomIntGenerator)
    val playerA = new PlayerImpl(humanPlayerName, humanShapeThrower)
    val playerB = new PlayerImpl("Computer", computerShapeThrower)
    val gameService = new GameServiceImpl(playerA, playerB)

    new GameController(gameService)
  }

  def newComputerVsComputerGameController: GameController = {
    val gameStrategy = new RockPaperScissorsGameStrategy()
    val computerShapeThrower = new RandomShapeThrower(gameStrategy, new RandomIntGenerator)
    val playerA = new PlayerImpl("ComputerA", computerShapeThrower)
    val playerB = new PlayerImpl("ComputerB", computerShapeThrower)
    val gameService = new GameServiceImpl(playerA, playerB)

    new GameController(gameService)
  }
}
