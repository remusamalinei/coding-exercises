package ra.maze.domain.service

/**
  * @author Remus Amalinei
  */
trait MazeReader {

  def read(): Array[Array[Char]]
}
