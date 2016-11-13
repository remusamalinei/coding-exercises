package ra.exchange.application

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}

/**
  * @author Remus Amalinei
  */
@RunWith(classOf[JUnitRunner])
class OrderMatcherServiceSpec extends FeatureSpec with GivenWhenThen with Matchers {

  info(
    """
        As an order matcher service client
        I want call the API
        So that I can add orders
          And compute the open orders quantity
          And compute the average execution price
          And compute the executed quantity
    """)

  feature("Add a matching order") {

    scenario("A newly added buy order is matched") {
      Given("several open orders")
      When("a new matching buy order is added")
      Then("the matched sell order is removed from the open sell orders quantity")
      Then("the open buy orders quantity does not change")
      Then("the matched order is added to the average execution price")
      Then("the buy order is added to the executed quantity")
    }

    scenario("A newly added sell order is matched") {
      Given("several open orders")
      When("a new matching sell order is added")
      Then("the matched buy order is removed from the open buy orders quantity")
      Then("the open sell orders quantity does not change")
      Then("the matched order is added to the average execution price")
      Then("the sell order is added to the executed quantity")
    }
  }

  feature("Add a nonmatching order") {

    scenario("A newly added buy order is not matched") {
      Given("several open orders")
      When("a new nonmatching buy order is added")
      Then("the open sell orders quantity does not change")
      Then("the buy order is added to the open buy orders quantity")
      Then("the average execution price does not change")
      Then("the executed quantity does not change")
    }

    scenario("A newly added sell order is not matched") {
      Given("several open orders")
      When("a new nonmatching sell order is added")
      Then("the open buy orders quantity does not change")
      Then("the sell order is added to the open sell orders quantity")
      Then("the average execution price does not change")
      Then("the executed quantity does not change")
    }
  }
}
