package ra.pn.application

import ra.pn.domain.PrimeNumbers

import scala.concurrent.{ExecutionContext, Future}

import java.lang.Runtime.getRuntime
import java.util.concurrent.Executors

/**
  * @author Remus Amalinei
  */
class PrimeNumbersServiceImpl(generatePrimes: (Int) => Seq[Int] = PrimeNumbers.sieveOfEratosthenes)
  extends PrimeNumbersService {

  override def constructFor(number: Int): Future[PrimeNumbers] = Future[PrimeNumbers] {
    require(2 <= number, s"the provided number = $number must be greater than or equal to 2, the smallest prime number")

    new PrimeNumbers(number, generatePrimes(number))
  }(PrimeNumbersServiceImpl.ExeCtx)
}

object PrimeNumbersServiceImpl {

  val ExeCtx = ExecutionContext.fromExecutor(
    Executors.newFixedThreadPool(getRuntime.availableProcessors)
  )
}
