package ra.maze.domain

import org.junit.runner.RunWith
import org.scalamock.scalatest.MockFactory
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Remus Amalinei
  */
@RunWith(classOf[JUnitRunner])
class ExplorerTest extends FlatSpec with Matchers with MockFactory {

  "Explorer" should "drop in to the Start point" in {
    val expectedStartCell = new Cell(10, 10)

    val mazeMock = mock[Maze]
    (mazeMock.startCell _).expects().returning(expectedStartCell)

    val explorer = new Explorer(mazeMock)

    explorer.position should be(expectedStartCell)
  }
}
