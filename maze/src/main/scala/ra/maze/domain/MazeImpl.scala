package ra.maze.domain

import ra.maze.domain.service.MazeReader

/**
  * @author Remus Amalinei
  */
class MazeImpl(private val mazeReader: MazeReader) extends Maze {

  private val startAndCells = {
    val rawCells = mazeReader.read()
    var start: Cell = Cell(-1, -1)
    val cells = Array.ofDim[Array[CellType.Value]](rawCells.length)
    var startPointCount = 0
    var exitPointCount = 0

    rawCells.zipWithIndex.foreach { case (col, rowIndex) =>
      cells(rowIndex) = Array.ofDim[CellType.Value](col.length)
      col.zipWithIndex.foreach { case (c, colIndex) =>
        cells(rowIndex)(colIndex) = c match {
          case 'X' => CellType.Wall
          case ' ' => CellType.EmptySpace
          case 'S' =>
            start = Cell(rowIndex, colIndex)
            startPointCount += 1
            CellType.Start
          case 'E' =>
            exitPointCount += 1
            CellType.Exit
          case invalidChar => throw new IllegalArgumentException(s"invalid maze representation: $invalidChar is an invalid char")
        }
      }
    }

    if ((startPointCount != 1) || (exitPointCount != 1)) {
      throw new IllegalArgumentException(s"invalid maze representation: $startPointCount start points and $exitPointCount exit points")
    }

    (start, cells)
  }

  override def startCell: Cell = startAndCells._1

  override def cellType(cell: Cell): CellType.Value = startAndCells._2(cell.row)(cell.col)
}
