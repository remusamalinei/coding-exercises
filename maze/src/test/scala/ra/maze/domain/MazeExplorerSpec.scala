package ra.maze.domain

import ra.maze.domain.CellType.Exit
import ra.maze.infrastructure.{NoSolution1, NoSolution2, NoSolution3, Solution2, Solution3, Solution1}

import org.scalamock.scalatest.MockFactory
import org.scalatest.prop.TableDrivenPropertyChecks.{Table, forAll}
import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}

/**
  * @author Remus Amalinei
  */
class MazeExplorerSpec extends FeatureSpec with GivenWhenThen with Matchers with MockFactory {

  info("As an Explorer of Mazes")
  info("I want to automatically explore a Maze")
  info("In order to either find the Exit")
  info("Or return to Start and conclude there is no path to the Exit")

  val ExpectedStartCell = Cell(10, 10)

  feature("Given a maze the Explorer should be able to automatically explore it") {

    scenario("Maze with solution") {
      val mazeReaders = Table("mr", new Solution1(), new Solution2(), new Solution3())

      forAll(mazeReaders) { mr =>
        Given("a Maze with solution")
        val maze = new MazeImpl(mr)
        val explorer = new Explorer(maze)

        When("the Explorer starts to automatically explore the Maze")
        val exitFound = explorer.findExit()

        Then("the Exit is found")
        Then("and the Explorer is located at Exit")
        exitFound should be(true)
        maze.cellType(explorer.position) should be(Exit)
      }
    }

    scenario("Maze without solution") {
      val mazeReaders = Table("mr", new NoSolution1(), new NoSolution2(), new NoSolution3())

      forAll(mazeReaders) { mr =>
        Given("a Maze without solution")
        val maze = new MazeImpl(mr)
        val explorer = new Explorer(maze)
        val start = explorer.position

        When("the Explorer starts to automatically explore the Maze")
        val exitFound = explorer.findExit()

        Then("the Exit is not found")
        Then("and the Explorer returns to Start after visiting the entire Maze")
        exitFound should be(false)
        explorer.position should be(start)
      }
    }
  }
}
