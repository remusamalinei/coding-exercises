package ra.rps.application

import ra.rps.domain.RoundResult

/**
  * @author Remus Amalinei
  */
trait GameService {

  def play: (RoundResult, RoundResult)
}
