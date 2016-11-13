package ra.exchange.domain

import ra.exchange.domain.Order.Direction.{Buy, Sell}

import org.junit.runner.RunWith
import org.scalamock.scalatest.MockFactory
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.TableDrivenPropertyChecks.{Table, forAll}
import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Remus Amalinei
  */
@RunWith(classOf[JUnitRunner])
class OrderTest extends FlatSpec with Matchers with MockFactory {

  "The order" should "match a sell order" in {
    val buyOrder = Order("AAPL", Buy, 5, BigDecimal(110.49), "test-1")

    val expectations = Table(
      ("sellOrder", "matched"),

      (Order("AAPL", Sell, 5, BigDecimal(90.32), "test-2"), true),
      (Order("AAPL", Sell, 5, BigDecimal(200.16), "test-2"), false)
    )

    forAll(expectations) { (sellOrder, matched) =>
      buyOrder.matches(sellOrder) should be(matched)
    }
  }

  it should "match a buy order" in {
    val sellOrder = Order("AAPL", Sell, 5, BigDecimal(110.49), "test-1")

    val expectations = Table(
      ("buyOrder", "matched"),

      (Order("AAPL", Buy, 5, BigDecimal(90.32), "test-2"), false),
      (Order("AAPL", Buy, 5, BigDecimal(200.16), "test-2"), true)
    )

    forAll(expectations) { (buyOrder, matched) =>
      sellOrder.matches(buyOrder) should be(matched)
    }
  }
}
