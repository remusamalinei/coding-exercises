package ra.rps.domain

/**
  * @author Remus Amalinei
  */
trait Player {

  def score(): Int

  def throwNextShape(): Shape

  def updateScorePlayingAgainst(shape: Shape): Outcome.Value
}
