package ra.rps.domain

/**
  * @author Remus Amalinei
  */
trait Player {

  def name: String

  def score: Int

  def nextThrownShape: Shape

  def updateScorePlayingAgainst(shape: Shape): Outcome.Value
}
