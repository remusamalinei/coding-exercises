package ra.rps.controller

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}

/**
  * @author Remus Amalinei
  */
@RunWith(classOf[JUnitRunner])
class GameControllerSpec extends FeatureSpec with GivenWhenThen with Matchers {

  info("As a gamer")
  info("I want to play rock-paper-scissors")
  info("So that I can spend time having fun")

  feature("Player vs Computer") {

    scenario("A Player vs Computer game is played") {

      Given("the Player vs Computer game is initialized with a valid player name")

      When("the 1st round is played and")
      When("the 2nd round is played")

      Then("the game returns the results after each round")
    }

    scenario("A Player vs Computer game cannot start with an invalid player name") {

      When("the Player vs Computer game is initialized with a too long player name")
      Then("the game construction throws an exception")
    }
  }

  feature("Computer vs Computer") {

    scenario("A Computer vs Computer game is played") {

      Given("the Computer vs Computer game is initialized")

      When("the 1st round is played and")
      When("the 2nd round is played")

      Then("the game returns the results after each round")
    }
  }
}
