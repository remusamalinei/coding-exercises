package ra.topranker.domain

/**
 * @author Remus Amalinei
 */
case class Score(player: Player, score: Int) {

  require(player != null)
  require(score > 0)
}
