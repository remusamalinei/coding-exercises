package ra.exchange.domain

import ra.exchange.domain.Order.Direction.{Buy, Sell}

/**
  * @author Remus Amalinei
  */
case class OpenOrders
(
  buyList: List[Order] = List(),
  sellList: List[Order] = List()
) {

  def add(order: Order): OpenOrders = order.direction match {
    case Buy =>
      val lowestPriceFirst = (a: Order, b: Order) => a.price < b.price
      val newBuyList = add(order, buyList, lowestPriceFirst)

      OpenOrders(newBuyList, sellList)
    case Sell =>
      val biggestPriceFirst = (a: Order, b: Order) => a.price > b.price
      val newSellList = add(order, sellList, biggestPriceFirst)

      OpenOrders(buyList, newSellList)
  }

  private def add(order: Order, list: List[Order], sorted: (Order, Order) => Boolean): List[Order] =
    if (list.isEmpty || sorted(order, list.head)) order :: list
    else list.head :: add(order, list.tail, sorted)

  def matchAndRemove(order: Order): (Option[Order], OpenOrders) = order.direction match {
    case Buy =>
      val (orderOption, newSellList) = matchAndRemove(order, sellList)

      (orderOption, OpenOrders(buyList, newSellList))
    case Sell =>
      val (orderOption, newBuyList) = matchAndRemove(order, buyList)

      (orderOption, OpenOrders(newBuyList, sellList))
  }

  private def matchAndRemove(order: Order, list: List[Order]): (Option[Order], List[Order]) = {
    val resultOption = list.zipWithIndex.find(tuple => order.matches(tuple._1))

    resultOption match {
      case Some((matchedOrder, index)) => (Some(matchedOrder), list.take(index) ::: list.drop(index + 1))
      case None => (None, list)
    }
  }
}
