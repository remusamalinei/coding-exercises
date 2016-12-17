package ra.pn.application

import ra.pn.domain.PrimeNumbers

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

import org.scalamock.scalatest.MockFactory
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.prop.Tables.Table
import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Remus Amalinei
  */
class PrimeNumbersServiceImplTest extends FlatSpec with Matchers with MockFactory {

  "PrimeNumbersService" should "not construct PrimeNumbers for invalid numbers" in {
    val invalidNumbers = Table("number", -5, 0, 1)
    forAll(invalidNumbers) { number =>
      val service = new PrimeNumbersServiceImpl
      val future = service.constructFor(number)

      an[IllegalArgumentException] should be thrownBy Await.result(future, 1.second)
    }
  }

  "PrimeNumbersService" should "return a Future holding PrimeNumbers" in {
    val (number, primes) = (5, Seq(2, 3, 5))

    val generatePrimesMock = mockFunction[Int, Seq[Int]]
    generatePrimesMock.
      expects(number).
      returning(primes)

    val service = new PrimeNumbersServiceImpl(generatePrimesMock)
    val future = service.constructFor(number)

    Await.result(future, 1.second) shouldBe new PrimeNumbers(number, primes)
  }
}
