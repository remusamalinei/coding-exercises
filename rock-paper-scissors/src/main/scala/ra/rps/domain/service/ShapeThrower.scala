package ra.rps.domain.service

import ra.rps.domain.Shape

/**
  * @author Remus Amalinei
  */
trait ShapeThrower {

  def next: Shape
}
