package ra.exchange.domain

import ra.exchange.domain.Order.Direction
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
class OpenOrdersTest extends FlatSpec with Matchers with MockFactory {

  "The prices object" should "record buy order prices sorted ascending with equal prices in the succession they are added" in {
    val buyOrders = List(
      testOrder(Buy, "2", "test-1"),
      testOrder(Buy, "1", "test-1"),
      testOrder(Buy, "3", "test-1)"),
      testOrder(Buy, "2", "test-2"))

    var openOrders = OpenOrders()
    buyOrders.foreach { order =>
      openOrders = openOrders.add(order)
    }

    val expectedBuyOrders = List(
      testOrder(Buy, "1", "test-1"),
      testOrder(Buy, "2", "test-1"),
      testOrder(Buy, "2", "test-2"),
      testOrder(Buy, "3", "test-1)"))

    openOrders.buyList should be(expectedBuyOrders)
  }

  it should "record sell order prices sorted descending with equal prices in the succession they are added" in {
    val sellOrders = List(
      testOrder(Sell, "2", "test-1"),
      testOrder(Sell, "1", "test-1"),
      testOrder(Sell, "3", "test-1)"),
      testOrder(Sell, "2", "test-2"))

    var openOrders = OpenOrders()
    sellOrders.foreach { order =>
      openOrders = openOrders.add(order)
    }

    val expectedSellOrders = List(
      testOrder(Sell, "3", "test-1)"),
      testOrder(Sell, "2", "test-1"),
      testOrder(Sell, "2", "test-2"),
      testOrder(Sell, "1", "test-1"))


    openOrders.sellList should be(expectedSellOrders)
  }

  it should "match a buy order" in {
    val sellOrders = List(
      testOrder(Sell, "5.2", "test-1"),
      testOrder(Sell, "5.2", "test-2"),
      testOrder(Sell, "9.3", "test-1"))

    val openOrders = OpenOrders(List(), sellOrders)

    val expectations = Table(
      ("buyOrder", "expectedMatchedOrderOption"),

      (testOrder(Buy, "2", "test"), None),
      (testOrder(Buy, "5.2", "test"), Some(testOrder(Sell, "5.2", "test-1"))),
      (testOrder(Buy, "8", "test"), Some(testOrder(Sell, "5.2", "test-1"))),
      (testOrder(Buy, "10", "test"), Some(testOrder(Sell, "5.2", "test-1")))
    )

    forAll(expectations) { (buyOrder, expectedMatchedOrderOption) =>
      val (matchedOrderOption, remainingOpenOrders) = openOrders.matchAndRemove(buyOrder)
      val expectedSellOrders = expectedMatchedOrderOption match {
        case Some(order) => sellOrders.diff(List(order))
        case None => sellOrders
      }

      matchedOrderOption should be(expectedMatchedOrderOption)
      remainingOpenOrders.sellList should be(expectedSellOrders)
    }
  }

  it should "match a sell order" in {
    val buyOrders = List(
      testOrder(Buy, "9.3", "test-1"),
      testOrder(Buy, "5.2", "test-1"),
      testOrder(Buy, "5.2", "test-2"))

    val openOrders = OpenOrders(buyOrders, List())

    val expectations = Table(
      ("sellOrder", "expectedMatchedOrderOption"),

      (testOrder(Sell, "10", "test"), None),
      (testOrder(Sell, "9.3", "test"), Some(testOrder(Buy, "9.3", "test-1"))),
      (testOrder(Sell, "8", "test"), Some(testOrder(Buy, "9.3", "test-1"))),
      (testOrder(Sell, "2", "test"), Some(testOrder(Buy, "9.3", "test-1")))
    )

    forAll(expectations) { (sellOrder, expectedMatchedOrderOption) =>
      val (matchedOrderOption, remainingOpenOrders) = openOrders.matchAndRemove(sellOrder)
      val expectedBuyOrders = expectedMatchedOrderOption match {
        case Some(order) => buyOrders.diff(List(order))
        case None => buyOrders
      }

      matchedOrderOption should be(expectedMatchedOrderOption)
      remainingOpenOrders.buyList should be(expectedBuyOrders)
    }
  }

  def testOrder(direction: Direction.Value, price: String, customerName: String): Order =
    Order("AAPL", direction, 5, BigDecimal(price), customerName)
}
