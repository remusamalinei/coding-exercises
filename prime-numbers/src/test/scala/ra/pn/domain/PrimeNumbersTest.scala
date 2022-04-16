package ra.pn.domain

import ra.pn.domain.PrimeNumbers.sieveOfEratosthenes

import org.scalatest.prop.TableDrivenPropertyChecks.forAll
import org.scalatest.prop.Tables.Table
import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Remus Amalinei
  */
class PrimeNumbersTest extends FlatSpec with Matchers {

  "sieveOfEratosthenes" should "generate prime numbers" in {
    val expectations = Table(
      ("number", "primes"),

      (2, Seq(2)),
      (3, Seq(2, 3)),
      (7, Seq(2, 3, 5, 7)),
      (100, Seq(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97))
    )

    forAll(expectations) { (number, primes) =>
      sieveOfEratosthenes(number) should be(primes)
    }
  }
}
