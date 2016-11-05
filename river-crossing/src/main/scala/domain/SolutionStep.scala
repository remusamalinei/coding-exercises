package domain

import scala.annotation.tailrec

/**
 * @author Remus Amalinei
 */
case class SolutionStep(leftBankPlayerSet: Set[Player],
                        rightBankPlayerSet: Set[Player],
                        boatOnLeftBank: Boolean) {

  def cross: Set[SolutionStep] =
    (crossSailorAlone ++ crossSailorAndAnotherPlayer)
      .filter(s => s.isPartOfPotentialSolution)

  private def crossSailorAlone: Set[SolutionStep] = {
    val sourceSet = sourceDestinationSetTuple._1
    val destinationSet = sourceDestinationSetTuple._2

    for {
      player <- sourceSet
      if player.sailor
    } yield buildSolutionStep((sourceSet - player), (destinationSet + player))
  }

  private def sourceDestinationSetTuple: Tuple2[Set[Player], Set[Player]] = {
    val sourceSet =
      if (boatOnLeftBank) leftBankPlayerSet
      else rightBankPlayerSet

    val destinationSet =
      if (boatOnLeftBank) rightBankPlayerSet
      else leftBankPlayerSet

    (sourceSet, destinationSet)
  }

  private def buildSolutionStep(sourceSet: Set[Player], destinationSet: Set[Player]): SolutionStep =
    if (boatOnLeftBank) SolutionStep(sourceSet, destinationSet, boatOnLeftBank = !boatOnLeftBank)
    else SolutionStep(destinationSet, sourceSet, boatOnLeftBank = !boatOnLeftBank)

  private def crossSailorAndAnotherPlayer: Set[SolutionStep] = {
    val sourceSet = sourceDestinationSetTuple._1
    val destinationSet = sourceDestinationSetTuple._2

    for {
      sailorPlayer <- sourceSet
      if sailorPlayer.sailor
      player <- sourceSet
      if sailorPlayer != player
    } yield buildSolutionStep((sourceSet - sailorPlayer - player), (destinationSet + sailorPlayer + player))
  }

  def isFinalStep: Boolean = leftBankPlayerSet.isEmpty

  private def isPartOfPotentialSolution: Boolean =
    ( ! injuresIn(leftBankPlayerSet)) && ( ! injuresIn(rightBankPlayerSet))

  private def injuresIn(playerSet: Set[Player]): Boolean = {
    injuredPlayerTupleSet(playerSet) != defendedPlayerTupleSet(playerSet)
  }

  private def injuredPlayerTupleSet(playerSet: Set[Player]): Set[Tuple2[Player, Injurer]] = {

    @tailrec
    def go(pSet: Set[Player], iPlayerTupleSet: Set[Tuple2[Player, Injurer]]): Set[Tuple2[Player, Injurer]] = {
      if (pSet.isEmpty) iPlayerTupleSet
      else {
        val firstPlayer = pSet.head
        val tupleSet = for {
          p <- firstPlayer.injuredPlayerSet
          if playerSet.contains(p)
        } yield (p, firstPlayer)

        go(pSet.tail, iPlayerTupleSet ++ tupleSet.toSet)
      }
    }

    go(playerSet, Set.empty)
  }

  private def defendedPlayerTupleSet(playerSet: Set[Player]): Set[Tuple2[Player, Injurer]] = {

    @tailrec
    def go(pSet: Set[Player], dPlayerTupleSet: Set[Tuple2[Player, Injurer]]): Set[Tuple2[Player, Injurer]] = {
      if (pSet.isEmpty) dPlayerTupleSet
      else {
        val firstPlayer = pSet.head
        val map = for {
          (p, i) <- firstPlayer.defendedPlayerMap
          if (playerSet.contains(p) && playerSet.contains(i.asInstanceOf[Player]))
        } yield (p, i)

        go(pSet.tail, dPlayerTupleSet ++ map.to[Set])
      }
    }

    go(playerSet, Set.empty)
  }
}
