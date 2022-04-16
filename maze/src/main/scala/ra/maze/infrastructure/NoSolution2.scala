package ra.maze.infrastructure

/**
  * @author Remus Amalinei
  */
class NoSolution2 extends AbstractMazeReaderTemplate {

  override final protected def mazeString: String = {
    "" +
      "XXXXXXXXXXXXXXX\n" +
      "X S    X      X\n" +
      "X      X    E X\n" +
      "XXXXXXXXXXXXXXX"
  }
}
