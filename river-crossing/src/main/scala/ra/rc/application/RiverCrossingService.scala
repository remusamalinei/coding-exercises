package ra.rc.application

import ra.rc.domain.{Solution, SolutionStep}

import scala.annotation.tailrec
import scala.collection.immutable.Set

/**
 * @author Remus Amalinei
 */
class RiverCrossingService {

  def cross(initialStep: SolutionStep): Set[Solution] = {

    @tailrec
    def go(solutionSet: Set[Solution]): Set[Solution] = {
      val firstSolutionToProgressOn = solutionSet.find {
        solution => ! solution.solutionStepList.last.isFinalStep
      }

      firstSolutionToProgressOn match {
        case None => solutionSet
        case Some(solutionInProgress) =>
          val lastStep = solutionInProgress.solutionStepList.last
          val nextStepSet = lastStep.cross
          if (nextStepSet.isEmpty) {
            solutionSet - solutionInProgress
          } else {
            val newSolutionSet = addIfThereIsProgress(solutionSet, solutionInProgress, nextStepSet)

            go(newSolutionSet)
          }
      }
    }

    @tailrec
    def addIfThereIsProgress(solutionSet: Set[Solution], solutionInProgress: Solution, nextStepSet: Set[SolutionStep]): Set[Solution] = {
      if (nextStepSet.isEmpty) {
        solutionSet
      } else {
        val potentialStep = nextStepSet.head
        val newSolutionSet =
          if (solutionInProgress.solutionStepList.contains(potentialStep)) {
            solutionSet - solutionInProgress
          } else {
            val expandedSolutionStepList = solutionInProgress.solutionStepList :+ potentialStep
            val solutionExpanded = Solution(expandedSolutionStepList)

            solutionSet - solutionInProgress + solutionExpanded
          }

        addIfThereIsProgress(newSolutionSet, solutionInProgress, nextStepSet.tail)
      }
    }

    go(Set(Solution(List(initialStep))))
  }
}
