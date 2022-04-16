package ra.pn.domain

/**
  * @author Remus Amalinei
  */
case class PrimeNumbers
(
  number: Int,
  primes: Seq[Int]
)

object PrimeNumbers {

  def sieveOfEratosthenes(number: Int): Seq[Int] = {
    val primesInSieve = Array.fill(number + 1)(true)

    for {
      i <- 2 to number
      if primesInSieve(i)
    } markMultiplesOf(i, primesInSieve)

    for {
      i <- 2 to number
      if primesInSieve(i)
    } yield i
  }

  private def markMultiplesOf(i: Int, primesInSieve: Array[Boolean]) = {
    for {
      multiple <- (2 * i) until primesInSieve.size by i
    } primesInSieve(multiple) = false
  }
}
