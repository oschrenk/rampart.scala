package rampart

import cats.Order

sealed trait Comparison
object Comparison {
  case object LessThan    extends Comparison
  case object Equals      extends Comparison
  case object GreaterThan extends Comparison
  import cats.implicits._
  def compare[A: Order](x: A, y: A): Comparison =
    x.compare(y) match {
      case lt if lt < 0  => LessThan
      case gt if gt > 0  => GreaterThan
      case eq if eq == 0 => Equals
    }
}
