package ra.roulette.application

import ra.roulette.actor.TableActor.{ProcessNumber, SignalBallSpinning, StartGame}

import akka.actor.{Props, ActorSystem, Actor}
import org.junit.runner.RunWith
import org.scalamock.scalatest.MockFactory
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfterEach, BeforeAndAfterAll, FlatSpec, Matchers}

/**
 * @author Remus Amalinei
 */
@RunWith(classOf[JUnitRunner])
class BallSpinnerTest extends FlatSpec with Matchers with BeforeAndAfterEach with BeforeAndAfterAll with MockFactory {

  val actorSystem = ActorSystem("test")

  override def afterAll(): Unit = {
    actorSystem.shutdown()
  }

  "A ball spinner" should "start spinning the ball after 25 seconds" in {
    val methodsMock = mock[Methods]
    (methodsMock.startGameReceived _).expects()
    (methodsMock.signalBallSpinningReceived _).expects()
    val dummyTableActor = actorSystem.actorOf(Props(classOf[DummyTableActor], methodsMock))

    val ballSpinner = new BallSpinner(dummyTableActor, mock[NumberGenerator])
    ballSpinner.startGame()

    Thread.sleep(25100)
  }

  it should "generate a number after 30 seconds" in {
    val expectedNumber = 123

    val methodsMock = mock[Methods]
    (methodsMock.processNumberReceived _).expects(expectedNumber)
    val dummyTableActor = actorSystem.actorOf(Props(classOf[DummyTableActor], methodsMock))

    val numberGeneratorStub = stub[NumberGenerator]

    val ballSpinner = new BallSpinner(dummyTableActor, numberGeneratorStub)
    ballSpinner.startGame()

    (numberGeneratorStub.nextNumber _).when().returns(expectedNumber)

    Thread.sleep(30100)
  }
}

trait Methods {
  def startGameReceived(): Unit

  def signalBallSpinningReceived(): Unit

  def processNumberReceived(value: Int): Unit
}

class DummyTableActor(dummyMethods: Methods) extends Actor {

  override def receive = {
    case StartGame() => dummyMethods.startGameReceived()
    case SignalBallSpinning() => dummyMethods.signalBallSpinningReceived()
    case ProcessNumber(value) => dummyMethods.processNumberReceived(value)
  }
}
