package ra.maze.infrastructure

/**
  * @author Remus Amalinei
  */
class NoSolution1 extends AbstractMazeReaderTemplate {

  override final protected def mazeString: String = {
    "" +
      "XXXXXXXXXXXXXXX\n" +
      "X             X\n" +
      "X XXXXXXXXXXX X\n" +
      "X XS        X X\n" +
      "X XXXXXXXXX X X\n" +
      "X XXXXXXXXX X X\n" +
      "X XXXX      X X\n" +
      "X XXXX XXXX X X\n" +
      "X XXXX XXXX X X\n" +
      "X X    XXXXXX X\n" +
      "X X XXXXXXXXX X\n" +
      "X X XXXXXXXXX X\n" +
      "X X         X X\n" +
      "XXXXXXXXXXX   X\n" +
      "XEXXXXXXXXXXXXX"
  }
}
