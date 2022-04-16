package ra.maze.domain

import ra.maze.domain.CardinalPoint.{E, N, S, W}
import ra.maze.domain.CellType.{Exit, Wall}

import scala.collection.mutable

/**
  * @author Remus Amalinei
  */
class Explorer(private val maze: Maze) {

  private var _position = maze.startCell
  private var direction = N
  private var visitedCellToDirection: mutable.Map[Cell, Set[CardinalPoint.Value]] = mutable.Map()
  private var visitedCells: mutable.MutableList[Cell] = mutable.MutableList()

  def findExit(): Boolean =
    if (maze.cellType(_position) == Exit) true
    else
      moveToNotExploredCell() || moveToNotExploredDirection()

  private def moveToNotExploredCell(): Boolean = {
    CardinalPoint.values.foreach { d =>
      if (!visitedCellToDirection.contains(positionMovingToDirection()) && tryToMove()) {
        return findExit()
      }

      turnRight()
    }

    false
  }

  private def positionMovingToDirection(): Cell = {
    direction match {
      case N => Cell(_position.row - 1, _position.col)
      case E => Cell(_position.row, _position.col + 1)
      case S => Cell(_position.row + 1, _position.col)
      case W => Cell(_position.row, _position.col - 1)
    }
  }

  private def tryToMove(): Boolean = {
    val cellAhead = maze.cellType(positionMovingToDirection())
    if (cellAhead == Wall) false
    else {
      val positionAfterMove = positionMovingToDirection()
      rememberVisitedCell()
      _position = positionAfterMove

      true
    }
  }

  private def rememberVisitedCell(): Unit = {
    visitedCells += _position

    val visitedDirections = visitedCellToDirection.getOrElse(_position, Set())
    visitedCellToDirection(_position) = visitedDirections + direction
  }

  private def turnRight(): Unit = {
    direction = direction match {
      case N => E
      case E => S
      case S => W
      case W => N
    }
  }

  private def moveToNotExploredDirection(): Boolean = {
    val visitedDirections = visitedCellToDirection.getOrElse(_position, Set())
    CardinalPoint.values.foreach { d =>
      if (!visitedDirections.contains(direction) && tryToMove()) {
        return findExit()
      }

      turnRight()
    }

    false
  }

  private[domain] def position: Cell = _position
}
