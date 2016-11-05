package ra.tr.domain.service

import ra.tr.domain.Score

/**
 * @author Remus Amalinei
 */
class ScoreSorterServiceImpl extends ScoreSorterService {

  val MaxSize = 10

  private var currentSize = 0
  private val scores = Array.ofDim[Score](MaxSize + 1)

  override def addScore(newScore: Score): Unit = {
    var i = currentSize
    while ((i > 0) && (scores(i - 1).value < newScore.value)) {
      scores(i) = scores(i - 1)
      i -= 1
    }
    scores(i) = newScore

    currentSize = (currentSize + 1) min MaxSize
  }

  override def retrieveTopScores(): IndexedSeq[Score] = {
    scores
      .slice(0, currentSize)
      .toIndexedSeq
  }
}
