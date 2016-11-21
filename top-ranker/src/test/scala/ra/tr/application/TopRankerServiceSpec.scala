package ra.tr.application

import ra.tr.actor.TopRankerActor
import ra.tr.actor.TopRankerActor.TopScores
import ra.tr.domain.service.ScoreSorterServiceImpl
import ra.tr.domain.{Player, Score}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

import java.util.concurrent.TimeUnit

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.util.Timeout
import org.scalatest.{BeforeAndAfterEach, FeatureSpec, GivenWhenThen, Matchers}

/**
 * @author Remus Amalinei
 */
class TopRankerServiceSpec extends FeatureSpec with GivenWhenThen with Matchers with BeforeAndAfterEach {

  info("As a Product Owner")
  info("I want the Top Ranker Service implemented")
  info("So I can add new Scores " +
    "And retrieve the top Scores")

  val testSystem = ActorSystem("test")
  val actorTimeout = Timeout(300, TimeUnit.MILLISECONDS)

  var topRankerActor: ActorRef = _
  var topRankerService: TopRankerService = _

  override def beforeEach() {
    topRankerActor = testSystem.actorOf(Props(
      classOf[TopRankerActor],
      new ScoreSorterServiceImpl()))
    topRankerService = new TopRankerService(topRankerActor)
  }

  feature("TopRankerService") {

    scenario("The top is empty initially") {

      Given("the top is empty")

      Then("the returned top is empty")
      val topScores = retrieveTopScoresByCallingTheService()

      topScores.scores shouldBe 'empty
    }

    scenario("A new top Score is successfully added, empty top") {

      Given("the top is empty")

      When("a Score is added")
      val score = Score(Player("test"), 1)
      topRankerService.addScore(score)

      Then("the returned top contains the Score")
      val topScores = retrieveTopScoresByCallingTheService()

      topScores.scores should be(Vector(score))
    }

    scenario("A Score is added to an existing incomplete top") {

      Given("The top is not complete")
      val existingScores = Vector(
        Score(Player("player-1"), 1),
        Score(Player("player-3"), 3))

      existingScores.foreach(s => topRankerService.addScore(s))

      When("A Score is added")
      val score = Score(Player("player-2"), 2)
      topRankerService.addScore(score)

      Then("The returned top contains the Score")
      val topScores = retrieveTopScoresByCallingTheService()

      topScores.scores should contain(score)
    }

    scenario("A new top Score is added to an existing complete top") {

      Given("The top is complete")
      val minScore = Score(Player("player-1"), 1)
      val existingScores = Vector(
        minScore,
        Score(Player("player-2"), 2),
        Score(Player("player-3"), 3),
        Score(Player("player-4"), 4),
        Score(Player("player-5"), 5),
        Score(Player("player-6"), 26),
        Score(Player("player-7"), 27),
        Score(Player("player-8"), 28),
        Score(Player("player-9"), 29),
        Score(Player("player-10"), 30))

      existingScores.foreach(s => topRankerService.addScore(s))

      When("A top Score is added")
      val score = Score(Player("player-11"), 20)
      topRankerService.addScore(score)

      Then("The returned top contains the Score")
      And("The lowest Score was removed")
      val topScores = retrieveTopScoresByCallingTheService()

      topScores.scores should contain(score)
      topScores.scores should not contain minScore
    }

    scenario("A Score lower than the minimum score in the top is not added to an existing complete top") {

      Given("The top is complete")
      val existingScores = Vector(
        Score(Player("player-1"), 21),
        Score(Player("player-2"), 22),
        Score(Player("player-3"), 23),
        Score(Player("player-4"), 24),
        Score(Player("player-5"), 25),
        Score(Player("player-6"), 26),
        Score(Player("player-7"), 27),
        Score(Player("player-8"), 28),
        Score(Player("player-9"), 29),
        Score(Player("player-10"), 30))

      existingScores.foreach(s => topRankerService.addScore(s))

      When("A lower than the minimum score is added")
      val minScore = Score(Player("player-1"), 1)
      topRankerService.addScore(minScore)

      Then("The returned top does not contain the Score")
      val topScores = retrieveTopScoresByCallingTheService()

      topScores.scores should not contain minScore
    }

    scenario("A Score equal to a Score in the top is added below it") {

      Given("The top has a few Scores")
      val existingScores = Vector(
        Score(Player("player-1"), 1),
        Score(Player("player-2"), 2),
        Score(Player("player-3"), 3))

      existingScores.foreach(s => topRankerService.addScore(s))

      When("A Score with the same value as an existing Score is added")
      val sameScore = Score(Player("another player-2"), 2)
      topRankerService.addScore(sameScore)

      Then("The added Score is added below the previously existing Score")
      val topScores = retrieveTopScoresByCallingTheService()

      topScores.scores should be(Vector(
        Score(Player("player-3"), 3),
        Score(Player("player-2"), 2),
        Score(Player("another player-2"), 2),
        Score(Player("player-1"), 1)))
    }
  }

  def retrieveTopScoresByCallingTheService(): TopScores = {
    val future = topRankerService.retrieveTopScores(actorTimeout)

    Await.result(future, Duration.Inf)
  }
}
