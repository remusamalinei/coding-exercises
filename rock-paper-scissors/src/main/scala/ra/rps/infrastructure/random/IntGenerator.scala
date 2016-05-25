package ra.rps.infrastructure.random

/**
  * @author Remus Amalinei
  */
trait IntGenerator {

  def next(n: Int): Int
}
