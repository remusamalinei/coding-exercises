package ra.rc.domain

import ra.rc.domain.ConstantPlayers._
import org.scalatest.{FlatSpec, Matchers}

/**
 * @author Remus Amalinei
 */
class SolutionStepTest extends FlatSpec with Matchers {

  "A solution step" should "produce the next solution steps when the river is crossed" in {
    val solutionStep = SolutionStep(Set(Wolf, Cabbage), Set(Goat, Farmer), boatOnLeftBank = false)

    solutionStep.cross should equal(Set(
      SolutionStep(Set(Wolf, Cabbage, Farmer), Set(Goat), boatOnLeftBank = true),
      SolutionStep(Set(Wolf, Cabbage, Goat, Farmer), Set.empty, boatOnLeftBank = true)))
  }

  it should "not produce any next solution steps when the cross is not possible" in {
    val solutionStep = SolutionStep(Set(Wolf, Bear, Goat), Set.empty, boatOnLeftBank = true)

    solutionStep.cross shouldBe empty
  }

  it should "be recognized as a final step" in {
    val solutionStep = SolutionStep(Set.empty, Set(Goat, Farmer), boatOnLeftBank = true)

    solutionStep shouldBe 'finalStep
  }
}
