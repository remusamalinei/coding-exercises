package ra.roulette.infrastructure

import ra.roulette.actor.TableActor.{ProcessNumber, SignalBallSpinning, StartGame}

import scala.util.Random

import java.util.concurrent.{Executors, TimeUnit}

import akka.actor.ActorRef

/**
 * @author Remus Amalinei
 */
class NumberGenerator(val tableActor: ActorRef) {

  tableActor ! StartGame()

  val scheduledExecutorService = Executors.newScheduledThreadPool(1)

  def startGame(): Unit = {
    scheduledExecutorService.scheduleAtFixedRate(spinBallRunnable(), 25, 30, TimeUnit.SECONDS)
    scheduledExecutorService.scheduleAtFixedRate(generateNumberRunnable(), 30, 30, TimeUnit.SECONDS)
  }

  def stopGame(): Unit = {
    scheduledExecutorService.shutdown()
  }

  private def spinBallRunnable(): Runnable = {
    new Runnable {
      override def run(): Unit = {
        tableActor ! SignalBallSpinning()
      }
    }
  }

  private def generateNumberRunnable(): Runnable = {
    new Runnable {
      override def run(): Unit = {
        val random = new Random()
        val randomNumber = random.nextInt(36) + 1

        tableActor ! ProcessNumber(randomNumber)
      }
    }
  }
}
