package ra.roulette.infrastructure

import ra.roulette.application.NumberGenerator

import scala.util.Random

/**
 * @author Remus Amalinei
 */
class RandomNumberGenerator extends NumberGenerator {

  override def nextNumber(): Int = {
    val random = new Random()

    random.nextInt(36) + 1
  }
}
