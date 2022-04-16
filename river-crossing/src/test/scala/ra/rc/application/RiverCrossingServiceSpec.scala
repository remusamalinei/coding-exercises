package ra.rc.application

import ra.rc.domain.ConstantPlayers._
import ra.rc.domain.{Player, Solution, SolutionStep}
import org.scalatest.{Matchers, FeatureSpec, GivenWhenThen}

/**
 * @author Remus Amalinei
 */
class RiverCrossingServiceSpec extends FeatureSpec with GivenWhenThen with Matchers{

  info("As a user")
  info("I want to be able to sail off players across the river")
  info("So I can move an entire group of players on the other bank")

  feature("River crossing") {
    scenario("Farmer and friends cross the river - happy path") {

      Given("Farmer, Wolf, Goat and Cabbage are on the left bank")
      And("Wolf injures Goat")
      And("Goat injures Cabbage")
      And("Farmer defends Goat against Wolf and Cabbage against Goat")
      val initialStep = SolutionStep(Set(Farmer, Wolf, Goat, Cabbage), Set.empty, boatOnLeftBank = true)
      val knownSolution = Solution(List(
        initialStep,
        SolutionStep(Set(Wolf, Cabbage),          Set(Farmer, Goat),                boatOnLeftBank = false),
        SolutionStep(Set(Farmer, Wolf, Cabbage),  Set(Goat),                        boatOnLeftBank = true),
        SolutionStep(Set(Cabbage),                Set(Farmer, Wolf, Goat),          boatOnLeftBank = false),
        SolutionStep(Set(Farmer, Goat, Cabbage),  Set(Wolf),                        boatOnLeftBank = true),
        SolutionStep(Set(Goat),                   Set(Farmer, Wolf, Cabbage),       boatOnLeftBank = false),
        SolutionStep(Set(Farmer, Goat),           Set(Wolf, Cabbage),               boatOnLeftBank = true),
        SolutionStep(Set.empty,                   Set(Farmer, Wolf, Goat, Cabbage), boatOnLeftBank = false)))

      When("the cross is complete")
      val solutionSet = new RiverCrossingService().cross(initialStep)

      Then("the solution set contains the known solution")
      solutionSet should contain (knownSolution)
    }

    scenario("Farmer and friends cross the river - no solution") {

      Given("Wolf and Cabbage are on the left bank")
      val initialStep = SolutionStep(Set(Wolf, Cabbage), Set.empty, boatOnLeftBank = true)

      When("they try to cross")
      val solutionSet = new RiverCrossingService().cross(initialStep)

      Then("there is no solution")
      solutionSet shouldBe 'empty
    }

    scenario("Farmer and friends cross the river - invalid initial step") {

      Given("Wolf, Goat and Cabbage are on the left bank")
      And("Wolf injures Goat")
      And("Goat injures Cabbage")
      And("There is nobody to defend players against injurers")

      val initialStep = SolutionStep(Set(Wolf, Goat, Cabbage), Set.empty, boatOnLeftBank = true)

      When("they try to cross")
      val solutionSet = new RiverCrossingService().cross(initialStep)

      Then("there is no solution")
      solutionSet shouldBe 'empty
    }

    scenario("People cross the river") {

      Given("Mom, Girl1, Girl2, Dad, Boy1, Boy2, Policeman and Thief are on the left bank")
      And("Mom injures Boy1 and Boy2")
      And("Dad injures Girl1 and Girl2")
      And("Thief injures Mom, Girl1, Girl2, Dad, Boy1 and Boy2")
      And("Mom defends Girl1 against Dad and Girl2 against Dad")
      And("Dad defends Boy1 against Mom and Boy2 against Mom")
      And("Policeman defends Mom against Thief, Girl1 against Thief, Girl2 against Thief, Dad against Thief, Boy1 against Thief and Boy2 against Thief")

      val mother = Player("Mom", sailor = true)()
      val father = Player("Dad", sailor = true)()

      val girl1 = Player("Girl1")()
      val girl2 = Player("Girl2")()
      val boy1 = Player("Boy1")()
      val boy2 = Player("Boy2")()
      val mom = Player(mother.name, sailor = true)(Set(boy1, boy2), Map(girl1 -> father, girl2 -> father))
      val dad = Player(father.name, sailor = true)(Set(girl1, girl2), Map(boy1 -> mother, boy2 -> mother))
      val thief = Player("Thief")(Set(mom, girl1, girl2, dad, boy1, boy2))
      val policeman = Player("Policeman", sailor = true)(
        defendedPlayerMap = Map(mom -> thief, girl1 -> thief, girl2 -> thief, dad -> thief, boy1 -> thief, boy2 -> thief))

      val initialStep = SolutionStep(Set(mom, girl1, girl2, dad, boy1, boy2, policeman, thief), Set.empty, boatOnLeftBank = true)
      val knownSolution = Solution(List(
        initialStep,
        SolutionStep(Set(mom, girl1, girl2, dad, boy1, boy2),             Set(policeman, thief),                                      boatOnLeftBank = false),
        SolutionStep(Set(mom, girl1, girl2, dad, boy1, boy2, policeman),  Set(thief),                                                 boatOnLeftBank = true),
        SolutionStep(Set(mom, girl1, girl2, dad, boy2),                   Set(boy1, policeman, thief),                                boatOnLeftBank = false),
        SolutionStep(Set(mom, girl1, girl2, dad, boy2, policeman, thief), Set(boy1),                                                  boatOnLeftBank = true),
        SolutionStep(Set(mom, girl1, girl2, policeman, thief),            Set(dad, boy1, boy2),                                       boatOnLeftBank = false),
        SolutionStep(Set(mom, girl1, girl2, dad, policeman, thief),       Set(boy1, boy2),                                            boatOnLeftBank = true),
        SolutionStep(Set(girl1, girl2, policeman, thief),                 Set(mom, dad, boy1, boy2),                                  boatOnLeftBank = false),
        SolutionStep(Set(mom, girl1, girl2, policeman, thief),            Set(dad, boy1, boy2),                                       boatOnLeftBank = true),
        SolutionStep(Set(mom, girl1, girl2),                              Set(dad, boy1, boy2, policeman, thief),                     boatOnLeftBank = false),
        SolutionStep(Set(mom, girl1, girl2, dad),                         Set(boy1, boy2, policeman, thief),                          boatOnLeftBank = true),
        SolutionStep(Set(girl1, girl2),                                   Set(mom, dad, boy1, boy2, policeman, thief),                boatOnLeftBank = false),
        SolutionStep(Set(mom, girl1, girl2),                              Set(dad, boy1, boy2, policeman, thief),                     boatOnLeftBank = true),
        SolutionStep(Set(girl2),                                          Set(mom, girl1, dad, boy1, boy2, policeman, thief),         boatOnLeftBank = false),
        SolutionStep(Set(girl2, policeman, thief),                        Set(mom, girl1, dad, boy1, boy2),                           boatOnLeftBank = true),
        SolutionStep(Set(thief),                                          Set(mom, girl1, girl2, dad, boy1, boy2, policeman),         boatOnLeftBank = false),
        SolutionStep(Set(policeman, thief),                               Set(mom, girl1, girl2, dad, boy1, boy2),                    boatOnLeftBank = true),
        SolutionStep(Set.empty,                                           Set(mom, girl1, girl2, dad, boy1, boy2, policeman, thief),  boatOnLeftBank = false)))

      When("the cross is complete")
      val solutionSet = new RiverCrossingService().cross(initialStep)

      Then("the solution set contains the known solution")
      solutionSet should contain (knownSolution)
    }
  }
}
