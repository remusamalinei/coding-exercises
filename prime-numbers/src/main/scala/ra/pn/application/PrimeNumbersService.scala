package ra.pn.application

import ra.pn.domain.PrimeNumbers

import scala.concurrent.Future

/**
  * @author Remus Amalinei
  */
trait PrimeNumbersService {

  def constructFor(number: Int): Future[PrimeNumbers]
}
