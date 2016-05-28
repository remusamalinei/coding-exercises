package ra.maze.domain

/**
  * @author Remus Amalinei
  */
trait Maze {

  def startCell: Cell

  def cellType(cell: Cell): CellType.Value
}
