package ra.rps.domain

/**
  * @author Remus Amalinei
  */

sealed trait Shape {

  def against(that: Shape): Outcome.Value =
    if (this == that) Outcome.Tie
    else if (Shape.WinsAgainstShapeMap(this) == that) Outcome.Win
    else Outcome.Loss
}

object Shape {

  val WinsAgainstShapeMap = Map[Shape, Shape](
    Rock -> Scissors,
    Scissors -> Paper,
    Paper -> Rock
  )

  case object Rock extends Shape

  case object Paper extends Shape

  case object Scissors extends Shape

}
