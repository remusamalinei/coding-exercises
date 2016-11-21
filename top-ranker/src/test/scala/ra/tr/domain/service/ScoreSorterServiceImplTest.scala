package ra.tr.domain.service

import ra.tr.domain.{Player, Score}

import org.scalatest.{FlatSpec, Matchers}

/**
 * @author Remus Amalinei
 */
class ScoreSorterServiceImplTest extends FlatSpec with Matchers {

  "The ScoreSorterServiceImpl" should "sort scores descending" in {
    val scoreSorterService = new ScoreSorterServiceImpl()

    1 to 20 foreach { i =>
        val player = Player(s"player $i")
        val score = Score(player, i)

        scoreSorterService.addScore(score)
    }
    scoreSorterService.addScore(Score(Player("2nd best score"), 20))

    scoreSorterService.retrieveTopScores() should be(expectedTopScore())
  }

  def expectedTopScore(): IndexedSeq[Score] = Vector(
    Score(Player("player 20"), 20),
    Score(Player("2nd best score"), 20),
    Score(Player("player 19"), 19),
    Score(Player("player 18"), 18),
    Score(Player("player 17"), 17),
    Score(Player("player 16"), 16),
    Score(Player("player 15"), 15),
    Score(Player("player 14"), 14),
    Score(Player("player 13"), 13),
    Score(Player("player 12"), 12))
}
