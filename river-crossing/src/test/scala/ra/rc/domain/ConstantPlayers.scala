package ra.rc.domain

/**
 * @author Remus Amalinei
 */
object ConstantPlayers {
  val Cabbage = Player("cabbage")()
  val Goat = Player("goat")(injuredPlayerSet = Set(Cabbage))
  val Wolf = Player("wolf")(injuredPlayerSet = Set(Goat))
  val Bear = Player("bear")(injuredPlayerSet = Set(Goat))
  val Farmer = Player("farmer", sailor = true)(defendedPlayerMap = Map(Goat -> Wolf, Cabbage -> Goat))
}
