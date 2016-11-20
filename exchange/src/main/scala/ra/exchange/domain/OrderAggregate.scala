package ra.exchange.domain

import ra.exchange.domain.Order.Direction._
import ra.exchange.domain.Order.Direction

/**
  * @author Remus Amalinei
  */
case class OrderAggregate
(
  openBuyOrders: List[Order] = List(),
  openSellOrders: List[Order] = List(),
  executedOrders: List[ExecutedOrder] = List()
) {

  def execute(order: Order): (Option[ExecutedOrder], OrderAggregate) = {
    val (maybeExecutedOrder, newOpenBuyOrders, newOpenSellOrders) = matchAndRemove(order)
    maybeExecutedOrder match {
      case Some(eo) =>
        val newExecutedOrders = eo :: executedOrders
        val newOrderAggregate = OrderAggregate(newOpenBuyOrders, newOpenSellOrders, newExecutedOrders)

        (Some(eo), newOrderAggregate)
      case None =>
        val newOrderAggregate = add(order)

        (None, newOrderAggregate)
    }
  }

  private def matchAndRemove(order: Order): (Option[ExecutedOrder], List[Order], List[Order]) = order.direction match {
    case Buy =>
      val (maybeExecutedOrder, newOpenSellOrders) = matchAndRemove(order, openSellOrders)

      (maybeExecutedOrder, openBuyOrders, newOpenSellOrders)
    case Sell =>
      val (maybeExecutedOrder, newOpenBuyOrders) = matchAndRemove(order, openBuyOrders)

      (maybeExecutedOrder, newOpenBuyOrders, openSellOrders)
  }

  private def matchAndRemove(o: Order, orders: List[Order]): (Option[ExecutedOrder], List[Order]) = {
    val maybeFound = orders.zipWithIndex.find(tuple => o.matches(tuple._1))

    maybeFound match {
      case Some((matchedByOrder, index)) =>
        val eo = ExecutedOrder(o, matchedByOrder)
        val newOrders = orders.take(index) ::: orders.drop(index + 1)

        (Some(eo), newOrders)
      case None =>
        (None, orders)
    }
  }

  private def add(order: Order): OrderAggregate = order.direction match {
    case Buy =>
      val highestPriceFirst = (a: Order, b: Order) => a.price > b.price
      val newOpenBuyOrders = add(order, openBuyOrders, highestPriceFirst)

      OrderAggregate(newOpenBuyOrders, openSellOrders, executedOrders)
    case Sell =>
      val lowestPriceFirst = (a: Order, b: Order) => a.price < b.price
      val newOpenSellOrders = add(order, openSellOrders, lowestPriceFirst)

      OrderAggregate(openBuyOrders, newOpenSellOrders, executedOrders)
  }

  private def add(order: Order, list: List[Order], sorted: (Order, Order) => Boolean): List[Order] =
    if (list.isEmpty || sorted(order, list.head)) order :: list
    else list.head :: add(order, list.tail, sorted)

  def openOrdersQuantity(direction: Direction.Value): Int = {
    val openOrders = direction match {
      case Buy => openBuyOrders
      case Sell => openSellOrders
    }

    openOrders.map(_.quantity).sum
  }

  def averageExecutionPrice: Option[BigDecimal] = {
    if (executedOrders.nonEmpty) {
      val totalQuantity = executedOrders.map(_.order.quantity).sum
      val totalPrice = executedOrders.
        map(eo => eo.order.quantity * eo.order.price).
        sum

      Some(totalPrice / totalQuantity)
    } else {
      None
    }
  }

  def executedQuantity(customerName: String): Int = {
    val quantities = executedOrders.map { eo =>
      if (customerName == eo.order.customerName) eo.order.direction match {
        case Buy => eo.order.quantity
        case Sell => -eo.order.quantity
      } else if (customerName == eo.matchedBy.customerName) eo.matchedBy.direction match {
        case Buy => eo.matchedBy.quantity
        case Sell => -eo.matchedBy.quantity
      } else {
        0
      }
    }

    quantities.sum
  }
}
