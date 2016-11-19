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

  def matches(that: Order): Boolean = {
    (instrumentCode == that.instrumentCode) &&
      (quantity == that.quantity) &&
      (direction != that.direction) &&
      (((direction == Buy) && (price >= that.price)) || ((direction == Sell) && (price <= that.price)))
  }
}

object Order {

  object Direction extends Enumeration {
    val Buy, Sell = Value
  }
}
