package rampart

import cats.Order

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
    import Comparison._
    import Relation._

    val lxly = compare(x.lesser, y.lesser)
    val lxgy = compare(x.lesser, y.greater)
    val gxly = compare(x.greater, y.lesser)
    val gxgy = compare(x.greater, y.greater)

    (lxly, lxgy, gxly, gxgy) match {
      case (Equals, _, _, Equals)           => Equal
      case (_, _, LessThan, _)              => Before
      case (_, _, Equals, _)                => Meets
      case (_, Equals, _, _)                => MetBy
      case (_, GreaterThan, _, _)           => After
      case (LessThan, _, _, LessThan)       => Overlaps
      case (LessThan, _, _, Equals)         => FinishedBy
      case (LessThan, _, _, GreaterThan)    => Contains
      case (Equals, _, _, LessThan)         => Starts
      case (Equals, _, _, GreaterThan)      => StartedBy
      case (GreaterThan, _, _, LessThan)    => During
      case (GreaterThan, _, _, Equals)      => Finishes
      case (GreaterThan, _, _, GreaterThan) => OverlappedBy
    }
  }
}
