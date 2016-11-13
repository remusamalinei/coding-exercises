package ra.exchange.domain

import ra.exchange.domain.Order.Direction
import ra.exchange.domain.Order.Direction.{Buy, Sell}

/**
  * Assumption: the constructed `Order` objects are semantically correct (existing `instrumentCode`,
  * positive `quantity` and `price`, existing `customerName`).
  *
  * @author Remus Amalinei
  */
case class Order
(
  instrumentCode: String,
  direction: Direction.Value,
  quantity: Int,
  price: BigDecimal,
  customerName: String
) {

  def matches(matchAgainst: Order): Boolean = {
    direction match {
      case Buy =>
        if (matchAgainst.direction == Sell) price >= matchAgainst.price
        else false
      case Sell =>
        if (matchAgainst.direction == Buy) price <= matchAgainst.price
        else false
    }
  }
}

object Order {

  object Direction extends Enumeration {
    val Buy, Sell = Value
  }
}
