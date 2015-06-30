package domain

/**
 * @author Remus Amalinei
 */
case class Player(name: String)
                 (injuredPlayerSet: Set[Player] = Set.empty,
                  defendedPlayerMap: Map[Player, Player] = Map.empty) {

  if (samePlayerIsBothInjuredAndDefeated(injuredPlayerSet, defendedPlayerMap)) {
    throw new IllegalArgumentException("cannot both injure and defend the same player")
  }

  private def samePlayerIsBothInjuredAndDefeated(injuredPlayerSet: Set[Player], defendedPlayerMap: Map[Player, Player]): Boolean =
    (injuredPlayerSet & defendedPlayerMap.keySet) != Set.empty

  def injures(player: Player): Boolean = {
    injuredPlayerSet.contains(player)
  }

  def defendsPlayerAgainstInjurer(player: Player, injurer: Player): Boolean = {
    defendedPlayerMap(player) == injurer
  }
}
