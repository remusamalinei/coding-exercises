package ra.rps.infrastructure

import ra.rps.domain.Shape
import ra.rps.domain.service.{GameStrategy, ShapeThrower}

import java.io.InputStream
import java.util.Scanner

/**
  * @author Remus Amalinei
  */
class InputStreamShapeThrower
(
  private val gameStrategy: GameStrategy,
  private val inputStream: InputStream) extends ShapeThrower {

  private val scanner = new Scanner(inputStream)
  private val shapeArray = gameStrategy.shapes.toArray

  override def next: Shape = {
    shapeArray.
      zipWithIndex.
      foreach {
        case (element, index) => println(s"$element ($index)")
      }

    try {
      val index = Integer.parseInt(scanner.next())

      shapeArray(index)
    } catch {
      case e: NumberFormatException => next
      case e: ArrayIndexOutOfBoundsException => next
    }
  }
}
