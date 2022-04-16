package ra.tr.application

import ra.tr.actor.TopRankerActor.{AddScore, QueryTopScores, TopScores}
import ra.tr.domain.Score

import scala.concurrent.Future

import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout

/**
 * @author Remus Amalinei
 */
class TopRankerService(topRankerActor: ActorRef) {

  def addScore(score: Score): Unit = {
    topRankerActor ! AddScore(score)
  }

  def retrieveTopScores(t: Timeout): Future[TopScores] = {
    implicit val timeout = t

    (topRankerActor ? QueryTopScores()).mapTo[TopScores]
  }
}
