package ra.maze.infrastructure

import ra.maze.domain.service.MazeReader

/**
  * @author Remus Amalinei
  */
abstract class AbstractMazeReaderTemplate extends MazeReader {

  protected def mazeString: String

  override def read(): Array[Array[Char]] = {
    val mazeRows = mazeString.split("\\n")
    val result = Array.ofDim[Array[Char]](mazeRows.length)
    mazeRows.zipWithIndex.foreach { case (row, rowIndex) =>
      result(rowIndex) = Array.ofDim[Char](row.length)
      row.zipWithIndex.foreach { case (c, colIndex) =>
        result(rowIndex)(colIndex) = c
      }
    }

    result
  }
}
