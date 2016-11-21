package ra.tr.actor

import ra.tr.actor.TopRankerActor.{AddScore, QueryTopScores, TopScores}
import ra.tr.domain.service.ScoreSorterService
import ra.tr.domain.{Player, Score}

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}

/**
 * @author Remus Amalinei
 */
class TopRankerActorTest(_system: ActorSystem)
  extends TestKit(_system)
  with ImplicitSender
  with WordSpecLike
  with BeforeAndAfterAll
  with MockFactory {

  def this() = this(ActorSystem("test"))

  override def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "TopRankerActor should" should {
    "call the ScoreSorterService to add a new Score" in {
      val scoreSorterServiceStub = stub[ScoreSorterService]
      val topRankerActor = system.actorOf(Props(classOf[TopRankerActor], scoreSorterServiceStub))
      val testScore = Score(Player("test"), 1)

      topRankerActor ! AddScore(testScore)

      (scoreSorterServiceStub.addScore _).verify(testScore)
    }
  }

  it should {
    "call the ScoreSorterService to retrieve the top scores" in {
      val scoreSorterServiceMock = mock[ScoreSorterService]
      val expectedScores = Vector(
        Score(Player("a"), 2),
        Score(Player("b"), 1))
      (scoreSorterServiceMock.retrieveTopScores _).expects().returning(expectedScores)

      val topRankerActor = system.actorOf(Props(classOf[TopRankerActor], scoreSorterServiceMock))

      topRankerActor ! QueryTopScores()
      expectMsg(TopScores(expectedScores))
    }
  }
}
