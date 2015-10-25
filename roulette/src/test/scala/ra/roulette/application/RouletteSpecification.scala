package ra.roulette.application

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FeatureSpec, GivenWhenThen}

/**
 * @author Remus Amalinei
 */
@RunWith(classOf[JUnitRunner])
class RouletteSpecification extends FeatureSpec with GivenWhenThen {
  {

    info("As a Product Owner")
    info("I want the game to start and accept Players")
    info("So they can play roulette")

    feature("Play roulette") {

      scenario("One player wins on red/black") {

        Given("the game is in progress")
        Given("and two players join")
        When("one player bets on red")
        When("and one player bets on black")
        Then("only one player wins")
      }

      scenario("One player wins on number") {

        Given("the game is in progress")
        Given("and three players join")
        When("one player bets on 1-12")
        When("and one player bets on 13-24")
        When("and one player bets on 25-36")
        Then("only one player wins")
      }
    }
  }
}
