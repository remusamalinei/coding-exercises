package ra.exchange.domain

import ra.exchange.domain.Order.Direction
import ra.exchange.domain.Order.Direction._

import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.prop.Tables.Table
import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Remus Amalinei
  */
class OrderAggregateTest extends FlatSpec with Matchers {

  "The aggregate order" should "record buy orders sorted descending with equal prices in the succession they are added" in {
    val buyOrders = List(
      testOrder(Buy, "2", "test-1"),
      testOrder(Buy, "1", "test-1"),
      testOrder(Buy, "3", "test-1"),
      testOrder(Buy, "2", "test-2"))

    var orderAggregate = OrderAggregate()
    buyOrders.foreach { order =>
      orderAggregate = orderAggregate.execute(order)._2
    }

    val expectedOpenBuyOrders = List(
      testOrder(Buy, "3", "test-1"),
      testOrder(Buy, "2", "test-1"),
      testOrder(Buy, "2", "test-2"),
      testOrder(Buy, "1", "test-1"))

    orderAggregate.openBuyOrders should be(expectedOpenBuyOrders)
    orderAggregate.openSellOrders should be(empty)
    orderAggregate.executedOrders should be(empty)
  }

  it should "record sell orders sorted ascending with equal prices in the succession they are added" in {
    val sellOrders = List(
      testOrder(Sell, "2", "test-1"),
      testOrder(Sell, "1", "test-1"),
      testOrder(Sell, "3", "test-1"),
      testOrder(Sell, "2", "test-2"))

    var orderAggregate = OrderAggregate()
    sellOrders.foreach { order =>
      orderAggregate = orderAggregate.execute(order)._2
    }

    val expectedOpenSellOrders = List(
      testOrder(Sell, "1", "test-1"),
      testOrder(Sell, "2", "test-1"),
      testOrder(Sell, "2", "test-2"),
      testOrder(Sell, "3", "test-1"))

    orderAggregate.openBuyOrders should be(empty)
    orderAggregate.openSellOrders should be(expectedOpenSellOrders)
    orderAggregate.executedOrders should be(empty)
  }

  it should "match a buy order" in {
    val sellOrders = List(
      testOrder(Sell, "5.2", "test-1"),
      testOrder(Sell, "5.2", "test-2"),
      testOrder(Sell, "9.3", "test-1"))

    val orderAggregate = OrderAggregate(openSellOrders = sellOrders)

    val expectations = Table(
      ("buyOrder", "expectedMatchedByOrder"),

      (testOrder(Buy, "2", "test"), null),
      (testOrder(Buy, "5.2", "test"), testOrder(Sell, "5.2", "test-1")),
      (testOrder(Buy, "8", "test"), testOrder(Sell, "5.2", "test-1")),
      (testOrder(Buy, "10", "test"), testOrder(Sell, "5.2", "test-1"))
    )

    forAll(expectations) { (buyOrder, expectedMatchedByOrder) =>
      val (maybeExecutedOrder, newOrderAggregate) = orderAggregate.execute(buyOrder)

      if (expectedMatchedByOrder != null) {
        maybeExecutedOrder.get should be(ExecutedOrder(buyOrder, expectedMatchedByOrder))
      } else {
        maybeExecutedOrder should be(None)
      }
    }
  }

  it should "match a sell order" in {
    val buyOrders = List(
      testOrder(Buy, "9.3", "test-1"),
      testOrder(Buy, "5.2", "test-1"),
      testOrder(Buy, "5.2", "test-2"))

    val orderAggregate = OrderAggregate(openBuyOrders = buyOrders)

    val expectations = Table(
      ("sellOrder", "expectedMatchedByOrder"),

      (testOrder(Sell, "10", "test"), null),
      (testOrder(Sell, "9.3", "test"), testOrder(Buy, "9.3", "test-1")),
      (testOrder(Sell, "8", "test"), testOrder(Buy, "9.3", "test-1")),
      (testOrder(Sell, "2", "test"), testOrder(Buy, "9.3", "test-1"))
    )

    forAll(expectations) { (sellOrder, expectedMatchedByOrder) =>
      val (maybeExecutedOrder, newOrderAggregate) = orderAggregate.execute(sellOrder)

      if (expectedMatchedByOrder != null) {
        maybeExecutedOrder.get should be(ExecutedOrder(sellOrder, expectedMatchedByOrder))
      } else {
        maybeExecutedOrder should be(None)
      }
    }
  }

  it should "compute the total open orders quantity for buy orders" in {
    val buyOrders = List(
      Order("AAPL", Buy, 1, BigDecimal(120), "test"),
      Order("AAPL", Buy, 100, BigDecimal(120), "test"),
      Order("AAPL", Buy, 10, BigDecimal(120), "test"))

    val orderAggregate = OrderAggregate(openBuyOrders = buyOrders)

    orderAggregate.openOrdersQuantity(Buy) should be(111)
  }

  it should "compute the total open orders quantity for sell orders" in {
    val sellOrders = List(
      Order("AAPL", Sell, 2, BigDecimal(120), "test"),
      Order("AAPL", Sell, 200, BigDecimal(120), "test"),
      Order("AAPL", Sell, 20, BigDecimal(120), "test"))

    val orderAggregate = OrderAggregate(openSellOrders = sellOrders)

    orderAggregate.openOrdersQuantity(Sell) should be(222)
  }

  it should "compute the average execution price when there are no executed orders" in {
    val orderAggregate = OrderAggregate()

    orderAggregate.averageExecutionPrice should be(None)
  }

  it should "compute the average execution price" in {
    val executedOrders = List(
      ExecutedOrder(testOrder(Sell, "10.5", "test-1"), testOrder(Buy, "11", "test-2")),
      ExecutedOrder(testOrder(Buy, "20", "test-1"), testOrder(Sell, "19", "test-2")))

    val orderAggregate = OrderAggregate(executedOrders = executedOrders)

    orderAggregate.averageExecutionPrice should be(Some(BigDecimal(15.25)))
  }

  it should "compute the executed quantity" in {
    val executedOrders = List(
      ExecutedOrder(
        Order("AAPL", Sell, 3, BigDecimal(120), "test-1"),
        Order("AAPL", Buy, 3, BigDecimal(120), "test")),
      ExecutedOrder(
        Order("AAPL", Sell, 10, BigDecimal(120), "test-2"),
        Order("AAPL", Buy, 10, BigDecimal(120), "test-1")),
      ExecutedOrder(
        Order("AAPL", Sell, 1, BigDecimal(120), "test-1"),
        Order("AAPL", Buy, 1, BigDecimal(120), "test")))

    val orderAggregate = OrderAggregate(executedOrders = executedOrders)

    orderAggregate.executedQuantity("test") should be(4)
    orderAggregate.executedQuantity("test-1") should be(6)
    orderAggregate.executedQuantity("test-2") should be(-10)
  }

  def testOrder(direction: Direction.Value, price: String, customerName: String): Order =
    Order("AAPL", direction, 5, BigDecimal(price), customerName)
}
