package ra.maze

import org.junit.runner.RunWith
import org.scalamock.scalatest.MockFactory
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}

/**
  * @author Remus Amalinei
  */
@RunWith(classOf[JUnitRunner])
class MazeExplorerSpec extends FeatureSpec with GivenWhenThen with Matchers with MockFactory {

  info("As an Explorer of Mazes")
  info("I want to automatically explore a Maze")
  info("In order to either find the Exit")
  info("Or return to Start and conclude there is no path to the Exit")

  feature("Given a maze the Explorer should be able to automatically explore it") {

    scenario("Maze with solution") {
      Given("a Maze with solution")
      When("the Explorer starts to automatically explore the Maze")
      Then("the Exit is found")
      Then("and the Explorer is located at Exit")
    }
  }

  scenario("Maze without solution") {
    Given("a Maze without solution")
    When("the Explorer starts to automatically explore the Maze")
    Then("the Exit is not found")
    Then("and the Explorer returns to Start after visiting the entire Maze")
  }
}
