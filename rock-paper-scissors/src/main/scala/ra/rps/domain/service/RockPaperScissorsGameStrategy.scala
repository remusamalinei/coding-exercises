package ra.rps.domain.service

import ra.rps.domain.Shape
import ra.rps.domain.Shape.{Paper, Rock, Scissors}

/**
  * @author Remus Amalinei
  */
class RockPaperScissorsGameStrategy extends GameStrategy {

  override val shapes = Set[Shape](Rock, Paper, Scissors)
}
