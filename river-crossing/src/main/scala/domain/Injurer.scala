package domain

/**
 * @author Remus Amalinei
 */
trait Injurer {

  def injures(player: Player): Boolean
}
