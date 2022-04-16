package ra.maze.infrastructure

/**
  * @author Remus Amalinei
  */
class NoSolution3 extends AbstractMazeReaderTemplate {

  override final protected def mazeString: String = {
    "" +
      "XXXXXXXXXXXXXXX\n" +
      "X S           X\n" +
      "XXXXXXX XXXXXXX\n" +
      "X           XEX\n" +
      "XXXXXXXXXXXXXXX"
  }
}
