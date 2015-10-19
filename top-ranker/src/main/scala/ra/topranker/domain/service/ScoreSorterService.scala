package ra.topranker.domain.service

import ra.topranker.domain.Score

/**
 * @author Remus Amalinei
 */
trait ScoreSorterService {

  def addScore(newScore: Score): Unit

  def retrieveTopScores(): IndexedSeq[Score]
}
