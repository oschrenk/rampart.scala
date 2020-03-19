package rampart

import cats.Order
import cats.kernel.Comparison.{EqualTo, GreaterThan, LessThan}

/**
  * Represents an interval bounded by two values, the 'lesser' and the 'greater'
  * value. These values can be anything with an `cats.kernel.Order` instance:
  * numbers, times, strings — you name it.
  *
  * Use `toInterval` to construct an interval
  * Use `relate` to determine how two intervals relate to each other.
 **/
case class Interval[A: Order] private (lesser: A, greater: A)
object Interval {
  import cats.implicits._

  def toInterval[A: Order](a: A, b: A): Interval[A] =
    if (a.compare(b) > 0)
      Interval(b, a)
    else
      Interval(a, b)

  /**
    * Converts a tuple into an [[Interval]]. Note that this requires an
    * `cats.kernel.Order` constraint so that the Interval can be sorted on
    * construction.
   **/
  def toInterval[A: Order](t: (A, A)): Interval[A] = toInterval(t._1, t._2)

  /**
    * Relates two [[Interval]]s. Calling `relate(x, y)` tells you how Interval
    * `x` * relates to Interval `y`. Consult the [[Relation]] documentation
    * for an explanation of all the possible results.
    *
    * {{{
    * relate(toInterval(1, 2)), (toInterval(3, 7)) == Before
    * relate(toInterval(2, 3)), (toInterval(3, 7)) == Meets
    * relate(toInterval(2, 4)), (toInterval(3, 7)) == Overlaps
    * relate(toInterval(2, 7)), (toInterval(3, 7)) == FinishedBy
    * relate(toInterval(2, 8)), (toInterval(3, 7)) == Contains
    * relate(toInterval(3, 4)), (toInterval(3, 7)) == Starts
    * relate(toInterval(3, 7)), (toInterval(3, 7)) == Equal
    * relate(toInterval(3, 8)), (toInterval(3, 7)) == StartedBy
    * relate(toInterval(4, 6)), (toInterval(3, 7)) == During
    * relate(toInterval(6, 7)), (toInterval(3, 7)) == Finishes
    * relate(toInterval(6, 8)), (toInterval(3, 7)) == OverlappedBy
    * relate(toInterval(7, 8)), (toInterval(3, 7)) == MetBy
    * relate(toInterval(8, 9)), (toInterval(3, 7)) == After
    * }}}
   **/
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
