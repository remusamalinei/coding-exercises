package ra.topranker.domain

/**
 * @author Remus Amalinei
 */
case class Player(name: String) {

  require((name != null) && !name.trim.isEmpty)
}
