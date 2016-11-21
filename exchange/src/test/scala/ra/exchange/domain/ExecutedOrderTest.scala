package ra.exchange.domain

import ra.exchange.domain.Order.Direction._

import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Remus Amalinei
  */
class ExecutedOrderTest extends FlatSpec with Matchers {

  "The executed order object" should "not be constructed with nonmatching orders" in {
    val order = Order("AAPL", Buy, 1, BigDecimal(120), "test")

    an[IllegalArgumentException] should be thrownBy ExecutedOrder(order, order)
  }
}
