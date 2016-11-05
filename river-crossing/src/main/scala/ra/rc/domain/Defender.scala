package ra.rc.domain

/**
 * @author Remus Amalinei
 */
trait Defender {

  def defendsPlayerAgainstInjurer(player: Player, injurer: Injurer): Boolean
}
