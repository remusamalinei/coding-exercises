package ra.exchange.domain

import ra.exchange.domain.Order.Direction._

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Remus Amalinei
  */
@RunWith(classOf[JUnitRunner])
class ExecutedOrderTest extends FlatSpec with Matchers {

  "The executed order object" should "not be constructed with nonmatching orders" in {
    val order = Order("AAPL", Buy, 1, BigDecimal(120), "test")

    an[IllegalArgumentException] should be thrownBy ExecutedOrder(order, order)
  }
}
