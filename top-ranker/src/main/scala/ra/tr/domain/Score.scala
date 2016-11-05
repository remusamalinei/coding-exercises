package ra.tr.domain

/**
 * @author Remus Amalinei
 */
case class Score(player: Player, value: Int) {

  require(player != null)
  require(value > 0)
}
