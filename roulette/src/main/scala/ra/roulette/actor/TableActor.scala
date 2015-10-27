package ra.roulette.actor

import ra.roulette.actor.TableActor.{StartGame, ProcessNumber, SignalBallSpinning}

import akka.actor.{Actor, ActorRef}

/**
 * @author Remus Amalinei
 */
object TableActor {

  case class StartGame()

  case class SignalBallSpinning()

  case class ProcessNumber(value: Int)
}

class TableActor extends Actor {

  override def receive = {
    case StartGame() => context become acceptBets(sender)
    case SignalBallSpinning() => context become rejectBets(sender)
    case ProcessNumber(value) => context become acceptBets(sender)
  }

  def rejectBets(originalSender: ActorRef): Receive = {
    case ProcessNumber(value) => println(value)
  }

  def acceptBets(originalSender: ActorRef): Receive = {
    case ProcessNumber(value) => println(value)
  }
}

