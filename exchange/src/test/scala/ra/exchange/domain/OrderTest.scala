package ra.exchange.domain

import ra.exchange.domain.Order.Direction.{Buy, Sell}

import org.scalatest.prop.TableDrivenPropertyChecks.{Table, forAll}
import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Remus Amalinei
  */
class OrderTest extends FlatSpec with Matchers {

  "One order" should "match another order if " +
    "they have the same instrument and quantity, opposite directions and sell price <= buy price" in {

    val order = Order("AAPL", Buy, 2, BigDecimal(120), "test-1")

    val matchingOrderValues = Table(
      ("instrumentCode", "direction", "quantity", "price", "expectedResult"),

      ("GOOG", Sell, 2, BigDecimal(120), false),
      ("AAPL", Buy, 2, BigDecimal(120), false),
      ("AAPL", Sell, 1, BigDecimal(120), false),
      ("AAPL", Sell, 2, BigDecimal(130), false),
      ("AAPL", Sell, 2, BigDecimal(110), true),
      ("AAPL", Sell, 2, BigDecimal(120), true)
    )

    forAll(matchingOrderValues) { (instrumentCode, direction, quantity, price, expectedResult) =>
      val anotherOrder = Order(instrumentCode, direction, quantity, price, "test-2")

      order.matches(anotherOrder) should be (expectedResult)
    }
  }
}
