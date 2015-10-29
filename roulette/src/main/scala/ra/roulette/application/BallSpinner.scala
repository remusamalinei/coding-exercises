package ra.roulette.application

import ra.roulette.actor.TableActor.{ProcessNumber, SignalBallSpinning, StartGame}

import java.util.concurrent.{Executors, TimeUnit}

import akka.actor.ActorRef

/**
 * @author Remus Amalinei
 */
class BallSpinner(val tableActor: ActorRef, val numberGenerator: NumberGenerator) {

  tableActor ! StartGame()

  val scheduledExecutorService = Executors.newScheduledThreadPool(1)

  def startGame(): Unit = {
    scheduledExecutorService.scheduleAtFixedRate(
      () => tableActor ! SignalBallSpinning(),
      25, 30, TimeUnit.SECONDS)
    scheduledExecutorService.scheduleAtFixedRate(
      () => tableActor ! ProcessNumber(numberGenerator.nextNumber()),
      30, 30, TimeUnit.SECONDS)
  }

  implicit def functionToRunnable(f: () => Unit): Runnable = new Runnable() {
    override def run(): Unit = f()
  }

  def stopGame(): Unit = {
    scheduledExecutorService.shutdown()
  }
}
