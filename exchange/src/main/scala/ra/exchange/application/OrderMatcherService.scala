package ra.exchange.application

import ra.exchange.domain.Order.Direction
import ra.exchange.domain.{Order, OrderAggregate}

import scala.collection.concurrent.TrieMap

/**
  * @author Remus Amalinei
  */
class OrderMatcherService {

  private final val orderAggregateMap = new TrieMap[String, OrderAggregate]

  def execute(order: Order): Unit = {
    val key = order.instrumentCode
    orderAggregateMap.putIfAbsent(key, OrderAggregate())

    var modified = false
    while (!modified) {
      val orderAggregate = orderAggregateMap(key)
      val newOrderAggregate = orderAggregate.execute(order)._2

      modified = orderAggregateMap.replace(key, orderAggregate, newOrderAggregate)
    }
  }

  def openOrdersQuantity(instrumentCode: String, direction: Direction.Value): Int = {
    val orderAggregate = orderAggregateMap.getOrElse(instrumentCode, OrderAggregate())

    orderAggregate.openOrdersQuantity(direction)
  }

  def averageExecutionPrice(instrumentCode: String): Option[BigDecimal] = {
    val orderAggregate = orderAggregateMap.getOrElse(instrumentCode, OrderAggregate())

    orderAggregate.averageExecutionPrice
  }

  def executedQuantity(instrumentCode: String, customerName: String): Int = {
    val orderAggregate = orderAggregateMap.getOrElse(instrumentCode, OrderAggregate())

    orderAggregate.executedQuantity(customerName)
  }
}
