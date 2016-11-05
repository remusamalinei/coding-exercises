package domain

/**
 * @author Remus Amalinei
 */
trait Defender {

  def defendsPlayerAgainstInjurer(player: Player, injurer: Injurer): Boolean
}
