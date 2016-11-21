package ra.exchange.application

import ra.exchange.domain.Order
import ra.exchange.domain.Order.Direction.{Buy, Sell}

import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}

/**
  * @author Remus Amalinei
  */
class OrderMatcherServiceSpec extends FeatureSpec with GivenWhenThen with Matchers {

  info(
    """
        As an order matcher service client
        I want call the API
        So that I can execute orders
          And compute the open orders quantity
          And compute the average execution price
          And compute the executed quantity
    """)

  def buildTestService(): OrderMatcherService = {
    val oms = new OrderMatcherService
    oms.execute(Order("AAPL", Buy, 10, BigDecimal(110), "test-1"))
    oms.execute(Order("AAPL", Buy, 30, BigDecimal(111), "test-1"))
    oms.execute(Order("GOOG", Buy, 50, BigDecimal(750), "test-1"))
    oms.execute(Order("MSFT", Buy, 70, BigDecimal(60), "test-1"))

    oms.execute(Order("AAPL", Sell, 20, BigDecimal(114), "test-2"))
    oms.execute(Order("AAPL", Sell, 40, BigDecimal(113), "test-2"))
    oms.execute(Order("AAPL", Sell, 60, BigDecimal(112), "test-2"))
    oms.execute(Order("GOOG", Sell, 80, BigDecimal(751), "test-2"))
    oms.execute(Order("MSFT", Sell, 100, BigDecimal(61), "test-2"))

    oms
  }

  feature("Order execution") {

    scenario("Execute buy orders") {
      Given("several open orders")
      val service = buildTestService()

      When("new matching buy orders are added")
      service.execute(Order("AAPL", Buy, 20, BigDecimal(120.25), "test")) // 20 * 120.25 = 2405
      service.execute(Order("AAPL", Buy, 40, BigDecimal(115), "test")) // 40 * 115 = 4600
      // average execution price = (2405 + 4600) / (20 + 40) = 116.75

      Then("the matched sell orders are removed from the open sell orders quantity")
      service.openOrdersQuantity("AAPL", Sell) should be(60)

      Then("the open buy orders quantity does not change")
      service.openOrdersQuantity("AAPL", Buy) should be(40)

      Then("the matching orders are added to the average execution price")
      service.averageExecutionPrice("AAPL") should be(Some(BigDecimal(116.75)))

      Then("the buy orders are added to the executed quantity")
      service.executedQuantity("AAPL", "test") should be(60)

      Then("the sell orders are added to the executed quantity")
      service.executedQuantity("AAPL", "test-2") should be(-60)
    }

    scenario("Execute sell orders") {
      Given("several open orders")
      val service = buildTestService()

      When("new matching sell orders are added")
      service.execute(Order("AAPL", Sell, 10, BigDecimal(30), "test")) // 10 * 30 = 300
      service.execute(Order("AAPL", Sell, 30, BigDecimal(10), "test")) // 30 * 10 = 300
      // average execution price = (300 + 300) / (10 + 30) = 15

      Then("the matched buy orders are removed from the open buy orders quantity")
      service.openOrdersQuantity("AAPL", Buy) should be(0)

      Then("the open sell orders quantity does not change")
      service.openOrdersQuantity("AAPL", Sell) should be(120)

      Then("the matching orders are added to the average execution price")
      service.averageExecutionPrice("AAPL") should be(Some(BigDecimal(15)))

      Then("the sell orders are added to the executed quantity")
      service.executedQuantity("AAPL", "test") should be(-40)

      Then("the buy orders are added to the executed quantity")
      service.executedQuantity("AAPL", "test-1") should be(40)
    }
  }

  feature("Nonmatching orders") {

    scenario("Add a nonmatching buy order") {
      Given("several open orders")
      val service = buildTestService()

      When("a new nonmatching buy order is added")
      service.execute(Order("AAPL", Buy, 1, BigDecimal(100), "test"))

      Then("the open sell orders quantity does not change")
      service.openOrdersQuantity("AAPL", Sell) should be(120)

      Then("the buy order is added to the open buy orders quantity")
      service.openOrdersQuantity("AAPL", Buy) should be(41)

      Then("the average execution price does not change")
      service.averageExecutionPrice("AAPL") should be(None)

      Then("the executed quantity does not change")
      service.executedQuantity("AAPL", "test") should be(0)
    }

    scenario("Add a nonmatching sell order") {
      Given("several open orders")
      val service = buildTestService()

      When("a new nonmatching sell order is added")
      service.execute(Order("AAPL", Sell, 1, BigDecimal(200), "test"))

      Then("the open buy orders quantity does not change")
      service.openOrdersQuantity("AAPL", Buy) should be(40)

      Then("the sell order is added to the open sell orders quantity")
      service.openOrdersQuantity("AAPL", Sell) should be(121)

      Then("the average execution price does not change")
      service.averageExecutionPrice("AAPL") should be(None)

      Then("the executed quantity does not change")
      service.executedQuantity("AAPL", "test") should be(0)
    }
  }
}
