package ra.tr.actor

import ra.tr.domain.Score
import ra.tr.domain.service.ScoreSorterService

import akka.actor.Actor

/**
 * @author Remus Amalinei
 */
object TopRankerActor {

  case class AddScore(score: Score)

  case class QueryTopScores()

  case class TopScores(scores: IndexedSeq[Score])

}

class TopRankerActor(scoreSorterService: ScoreSorterService) extends Actor {

  import TopRankerActor.{AddScore, TopScores, QueryTopScores}

  override def receive = {
    case AddScore(score) =>
      scoreSorterService.addScore(score)
    case QueryTopScores() =>
      val scores = scoreSorterService.retrieveTopScores()

      sender ! TopScores(scores)
  }
}
