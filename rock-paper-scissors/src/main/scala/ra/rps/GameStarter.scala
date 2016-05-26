package ra.rps

import ra.rps.view.ConsoleGameView

/**
  * @author Remus Amalinei
  */
object GameStarter {

  def main(args: Array[String]): Unit = {
    new ConsoleGameView(System.in, System.out).start()
  }
}
