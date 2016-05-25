package ra.rps.infrastructure

import ra.rps.domain.Shape
import ra.rps.domain.service.{GameStrategy, ShapeThrower}
import ra.rps.infrastructure.random.IntGenerator

/**
  * @author Remus Amalinei
  */
class RandomShapeThrower
(
  private val gameStrategy: GameStrategy,
  private val intGenerator: IntGenerator) extends ShapeThrower {

  private val shapeArray = gameStrategy.shapes.toArray

  override def next: Shape = {
    val index = intGenerator.next(shapeArray.length)

    shapeArray(index)
  }
}
