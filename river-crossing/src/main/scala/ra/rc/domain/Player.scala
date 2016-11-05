package ra.rc.domain

/**
 * @author Remus Amalinei
 */
case class Player (name: String, sailor: Boolean = false)
                 (val injuredPlayerSet: Set[Player] = Set.empty,
                  val defendedPlayerMap: Map[Player, Injurer] = Map.empty)
  extends Injurer with Defender {

  if (samePlayerIsBothInjuredAndDefeated(injuredPlayerSet, defendedPlayerMap)) {
    throw new IllegalArgumentException("cannot both injure and defend the same player")
  }

  private def samePlayerIsBothInjuredAndDefeated(injuredPlayerSet: Set[Player], defendedPlayerMap: Map[Player, Injurer]): Boolean =
    (injuredPlayerSet & defendedPlayerMap.keySet) != Set.empty

  override def injures(player: Player): Boolean = {
    injuredPlayerSet.contains(player)
  }

  override def defendsPlayerAgainstInjurer(player: Player, injurer: Injurer): Boolean = {
    defendedPlayerMap(player) == injurer
  }
}
