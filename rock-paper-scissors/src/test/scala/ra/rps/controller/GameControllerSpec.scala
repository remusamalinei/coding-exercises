package ra.rps.controller

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, PrintStream}

import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}

/**
  * @author Remus Amalinei
  */
class GameControllerSpec extends FeatureSpec with GivenWhenThen with Matchers {

  info("As a gamer")
  info("I want to play rock-paper-scissors")
  info("So that I can spend time having fun")

  feature("Player vs Computer") {

    scenario("A Player vs Computer game is played") {

      System.setOut(new PrintStream(new ByteArrayOutputStream()))

      Given("the Player vs Computer game is initialized with a valid player name")
      val inputStream = new ByteArrayInputStream("0 1".getBytes)
      val gameController = GameController.newHumanVsComputerGameController("MyName", inputStream)

      When("the 1st round is played and")
      val results1 = gameController.play
      When("the 2nd round is played")
      val results2 = gameController.play

      Then("the game returns the results after each round")
      results1.productArity should be(2)
      results2.productArity should be(2)
    }

    scenario("A Player vs Computer game cannot start with an invalid player name") {

      When("the Player vs Computer game is initialized with a too long player name")
      Then("the game construction throws an exception")
      an[IllegalArgumentException] should be thrownBy
        GameController.newHumanVsComputerGameController("_16_characters__", System.in)
    }
  }

  feature("Computer vs Computer") {

    scenario("A Computer vs Computer game is played") {

      Given("the Computer vs Computer game is initialized")
      val gameController = GameController.newComputerVsComputerGameController

      When("the 1st round is played and")
      val results1 = gameController.play
      When("the 2nd round is played")
      val results2 = gameController.play

      Then("the game returns the results after each round")
      results1.productArity should be(2)
      results2.productArity should be(2)
    }
  }
}
