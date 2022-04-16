package ra.maze.domain

import ra.maze.domain.CellType.{EmptySpace, Exit, Start, Wall}
import ra.maze.domain.service.MazeReader

import org.scalamock.scalatest.MockFactory
import org.scalatest.prop.TableDrivenPropertyChecks.{Table, forAll}
import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Remus Amalinei
  */
class MazeImplTest extends FlatSpec with Matchers with MockFactory {

  "Maze" should "be successfully constructed from correct representation" in {
    val mazeReaderMock = mock[MazeReader]
    (mazeReaderMock.read _).expects().returning(CorrectMazeRepresentation)

    new MazeImpl(mazeReaderMock)
  }

  it should "not be constructed from invalid representation" in {

    val invalidMazeRepresentations = Table(
      "maze",
      InvalidCharacterInMazeRepresentation,
      NoStartInMazeRepresentation,
      MoreThan1StartInMazeRepresentation,
      NoExitInMazeRepresentation,
      MoreThan1ExitInMazeRepresentation)

    forAll(invalidMazeRepresentations) { maze =>
      val mazeReaderMock = mock[MazeReader]
      (mazeReaderMock.read _).expects().returning(maze)

      an[IllegalArgumentException] should be thrownBy new MazeImpl(mazeReaderMock)
    }
  }

  it should "have the Start cell available after construction" in {
    val mazeReaderMock = mock[MazeReader]
    (mazeReaderMock.read _).expects().returning(CorrectMazeRepresentation)

    val maze = new MazeImpl(mazeReaderMock)

    maze.startCell should be(Cell(1, 1))
  }

  it should "know what exists at any coordinates" in {
    val expectations = Table(
      ("x", "y", "cellType"),
      (0, 0, Wall),
      (1, 2, EmptySpace),
      (1, 1, Start),
      (2, 3, Exit))

    forAll(expectations) { (x, y, cellType) =>
      val mazeReaderMock = mock[MazeReader]
      (mazeReaderMock.read _).expects().returning(CorrectMazeRepresentation)

      val maze = new MazeImpl(mazeReaderMock)

      maze.cellType(Cell(x, y)) should be(cellType)
    }
  }

  it should "throw an exception if the coordinates are outside boundaries" in {
    val mazeReaderMock = mock[MazeReader]
    (mazeReaderMock.read _).expects().returning(CorrectMazeRepresentation)

    val maze = new MazeImpl(mazeReaderMock)

    an[ArrayIndexOutOfBoundsException] should be thrownBy maze.cellType(Cell(123, 456))
  }

  val CorrectMazeRepresentation = Array(
    Array('X', 'X', 'X', 'X', 'X'),
    Array('X', 'S', ' ', ' ', 'X'),
    Array('X', 'X', ' ', 'E', 'X'),
    Array('X', 'X', 'X', 'X', 'X'))

  val InvalidCharacterInMazeRepresentation = Array(Array('*', 'S', 'E', '*', '*'))

  val NoStartInMazeRepresentation = Array(Array('X', ' ', 'E', 'X', 'X'))

  val MoreThan1StartInMazeRepresentation = Array(Array('X', 'S', 'E', 'S', 'X'))

  val NoExitInMazeRepresentation = Array(Array('X', 'S', ' ', 'X', 'X'))

  val MoreThan1ExitInMazeRepresentation = Array(Array('X', 'S', 'E', 'E', 'X'))
}
