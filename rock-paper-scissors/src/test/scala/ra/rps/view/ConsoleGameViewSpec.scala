package ra.rps.view

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, PrintStream}

import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}

/**
  * @author Remus Amalinei
  */
class ConsoleGameViewSpec extends FeatureSpec with GivenWhenThen with Matchers {

  info("As a gamer")
  info("I want to play rock-paper-scissors")
  info("So that I can spend time having fun")

  feature("New game") {

    scenario("A new Player vs Computer game can be selected") {
      System.setOut(new PrintStream(new ByteArrayOutputStream()))

      Given("the game begins")
      System.setIn(new ByteArrayInputStream("0".getBytes))
      val inputStream = new ByteArrayInputStream("n h MyName q q".getBytes)
      val outputStream = new ByteArrayOutputStream()
      val consoleGameViewer = new ConsoleGameView(inputStream, new PrintStream(outputStream))
      consoleGameViewer.start()

      When("the user selects a new game and")
      When("the user selects a Player vs Computer game")

      Then("a new Player vs Computer game starts")
      outputStream.toString.contains("New human vs computer game started") should be(true)
    }
  }

  scenario("A new Computer vs Computer game can be selected") {

    Given("the game begins")
    val inputStream = new ByteArrayInputStream("n c q q".getBytes)
    val outputStream = new ByteArrayOutputStream()
    val consoleGameViewer = new ConsoleGameView(inputStream, new PrintStream(outputStream))
    consoleGameViewer.start()

    When("the user selects a new game and")
    When("the user selects a Computer vs Computer game")

    Then("a new Computer vs Computer game starts")
    outputStream.toString.contains("New computer vs computer game started") should be(true)
  }
}
