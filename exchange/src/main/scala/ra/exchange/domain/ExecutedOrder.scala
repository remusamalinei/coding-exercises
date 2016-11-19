package ra.exchange.domain

import ra.exchange.domain.Order.Direction.{Buy, Sell}

/**
  * @author Remus Amalinei
  */
case class ExecutedOrder
(
  order: Order,
  matchedBy: Order
) {

  require(order.matches(matchedBy))
}
