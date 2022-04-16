package ra.rps.infrastructure.random

import scala.util.Random

/**
  * @author Remus Amalinei
  */
class RandomIntGenerator extends IntGenerator {

  override def next(n: Int): Int = Random.nextInt(n)
}
