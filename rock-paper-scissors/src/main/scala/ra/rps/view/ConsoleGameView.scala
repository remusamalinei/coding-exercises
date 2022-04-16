package ra.rps.view

import ra.rps.controller.GameController
import ra.rps.domain.Outcome.{Loss, Tie, Win}
import ra.rps.domain.RoundResult

import java.io.{InputStream, PrintStream}
import java.util.Scanner

/**
  * @author Remus Amalinei
  */
class ConsoleGameView(private val inputStream: InputStream, private val printStream: PrintStream) {

  private val scanner = new Scanner(inputStream)

  def start(): Unit = {
    printStream.println("\nGame started")

    while (true) {
      printStream.print("\nNew game(n) or quit(q)? Please select: ")

      scanner.next match {
        case "n" | "N" => startNewGame()
        case "q" | "Q" =>
          printStream.println("\nThanks for playing.\n")
          return
        case _ =>
      }
    }
  }

  private def startNewGame(): Unit = {
    while (true) {
      printStream.print("\nHuman vs computer(h) or computer vs computer(c)? Please select: ")

      scanner.next match {
        case "h" | "H" =>
          printStream.println("\nNew human vs computer game started")
          startHumanVsComputerGame(readHumanPlayerName)
          return
        case "c" | "C" =>
          printStream.println("\nNew computer vs computer game started")
          startComputerVsComputerGame()
          return
        case _ =>
      }
    }
  }

  private def startHumanVsComputerGame(humanPlayerName: String): Unit = {
    val gameController = GameController.newHumanVsComputerGameController(humanPlayerName, System.in)
    var action = "a"

    while (true) {
      action match {
        case "a" | "A" =>
          printStream.print(s"\n$humanPlayerName, please choose your next shape: ")
          printStream.println(s"\n${format(gameController.play)}")
        case "q" | "Q" => return
        case _ =>
      }

      printStream.print("\nAgain(a) or quit(q)? Please select: ")
      action = scanner.next()
    }
  }

  private def readHumanPlayerName: String = {
    var humanPlayerName = "* long enough to loop once *****************************************************************"
    while (humanPlayerName.trim.length > 15) {
      printStream.print("\nPlease enter your name (1 to 15 characters): ")
      humanPlayerName = scanner.next
    }

    humanPlayerName
  }

  private def format(roundResults: (RoundResult, RoundResult)): String = {
    val first = roundResults._1
    val second = roundResults._2

    val outcome = first.outcome match {
      case Loss => "lost"
      case Tie => "tied"
      case Win => "won"
    }

    s"""${first.playerName} $outcome vs ${second.playerName} throwing a ${first.shape} against a ${second.shape}
      |${first.playerName}  ${first.playerScore} - ${second.playerScore}  ${second.playerName}""".stripMargin
  }

  private def startComputerVsComputerGame(): Unit = {
    val gameController = GameController.newComputerVsComputerGameController
    var action = "a"

    while (true) {
      action match {
        case "a" | "A" => printStream.println(s"\n${format(gameController.play)}")
        case "q" | "Q" => return
        case _ =>
      }

      printStream.print("\nAgain(a) or quit(q)? Please select: ")
      action = scanner.next()
    }
  }
}
