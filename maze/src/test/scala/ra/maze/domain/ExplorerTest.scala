package ra.maze.domain

import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Remus Amalinei
  */
class ExplorerTest extends FlatSpec with Matchers with MockFactory {

  "Explorer" should "drop in to the Start point" in {
    val expectedStartCell = Cell(10, 10)

    val mazeMock = mock[Maze]
    (mazeMock.startCell _).expects().returning(expectedStartCell)

    val explorer = new Explorer(mazeMock)

    explorer.position should be(expectedStartCell)
  }
}
