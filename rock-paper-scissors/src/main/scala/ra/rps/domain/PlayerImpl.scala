package ra.rps.domain

import ra.rps.domain.service.ShapeThrower

/**
  * @author Remus Amalinei
  */
class PlayerImpl
(
  override val name: String,
  private val shapeThrower: ShapeThrower) extends Player {

  require(!name.trim.isEmpty && (name.trim.length < 16))

  private var _currentShape: Shape = _
  private var _score: Int = 0

  def currentShape = _currentShape

  override def score = _score

  override def nextThrownShape: Shape = {
    _currentShape = shapeThrower.next

    currentShape
  }

  override def updateScorePlayingAgainst(shape: Shape): Outcome.Value = {
    val outcome = currentShape.against(shape)
    if (outcome == Outcome.Win) {
      _score += 1
    }

    outcome
  }
}
