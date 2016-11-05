package ra.tr.domain

/**
 * @author Remus Amalinei
 */
case class Player(name: String) {

  require((name != null) && !name.trim.isEmpty)
}
