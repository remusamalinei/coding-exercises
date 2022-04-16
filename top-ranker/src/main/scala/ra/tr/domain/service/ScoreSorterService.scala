package ra.tr.domain.service

import ra.tr.domain.Score

/**
 * @author Remus Amalinei
 */
trait ScoreSorterService {

  def addScore(newScore: Score): Unit

  def retrieveTopScores(): IndexedSeq[Score]
}
