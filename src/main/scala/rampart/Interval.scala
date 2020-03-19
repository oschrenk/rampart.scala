package rampart

import cats.Order
import cats.kernel.Comparison.{EqualTo, GreaterThan, LessThan}

case class Interval[A: Order] private (lesser: A, greater: A)
object Interval {
  import cats.implicits._

  def toInterval[A: Order](a: A, b: A): Interval[A] =
    if (a.compare(b) > 0)
      Interval(b, a)
    else
      Interval(a, b)

  def toInterval[A: Order](t: (A, A)): Interval[A] = toInterval(t._1, t._2)

  def relate[A: Order](x: Interval[A], y: Interval[A]): Relation = {
    import cats.implicits._
    import Relation._

    val lxly = x.lesser.comparison(y.lesser)
    val lxgy = x.lesser.comparison(y.greater)
    val gxly = x.greater.comparison(y.lesser)
    val gxgy = x.greater.comparison(y.greater)

    (lxly, lxgy, gxly, gxgy) match {
      case (EqualTo, _, _, EqualTo)         => Equal
      case (_, _, LessThan, _)              => Before
      case (_, _, EqualTo, _)               => Meets
      case (_, EqualTo, _, _)               => MetBy
      case (_, GreaterThan, _, _)           => After
      case (LessThan, _, _, LessThan)       => Overlaps
      case (LessThan, _, _, EqualTo)        => FinishedBy
      case (LessThan, _, _, GreaterThan)    => Contains
      case (EqualTo, _, _, LessThan)        => Starts
      case (EqualTo, _, _, GreaterThan)     => StartedBy
      case (GreaterThan, _, _, LessThan)    => During
      case (GreaterThan, _, _, EqualTo)     => Finishes
      case (GreaterThan, _, _, GreaterThan) => OverlappedBy
    }
  }
}
