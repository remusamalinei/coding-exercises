package ra.rps.domain

/**
  * @author Remus Amalinei
  */
case class RoundResult
(
  shape: Shape,
  outcome: Outcome.Value,
  playerName: String,
  playerScore: Int)
