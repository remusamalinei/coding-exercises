package ra.topranker.application

import org.junit.runner.RunWith
import org.scalatest.{GivenWhenThen, FeatureSpec}
import org.scalatest.junit.JUnitRunner

/**
 * @author Remus Amalinei
 */
@RunWith(classOf[JUnitRunner])
class TopRankerServiceSpec extends FeatureSpec with GivenWhenThen {

  info("As a Developer")
  info("I want the Top Ranker Service implemented")
  info("So I can add new Scores and retrieve the top Scores")

  feature("Top Ranker Service") {

    /*scenario("A new top Score is successfully added, empty top") {

      Given("The top is empty")
      When("A Score is added")
      Then("The returned top contains the Score")
    }

    scenario("A Score is added to an existing incomplete top") {

      Given("The top is not complete")
      When("A Score is added")
      Then("The returned top contains the Score")
    }

    scenario("A new top Score is added to an existing complete top") {

      Given("The top is complete")
      When("A top Score is added")
      Then("The returned top contains the Score")
      And("The lowest Score was removed")
    }

    scenario("A Score lower than the minimum score in the top is not added to an existing complete top") {

      Given("The top is complete")
      When("A lower than the minimum score is added")
      Then("The returned top does not contain the Score")
    }

    scenario("A Score equal to a Score in the top is added below it") {

      Given("The top has a few Scores")
      When("A Score with the same value as an existing Score is added")
      Then("The added Score is added below the previously existing Score")
    }*/
  }
}
