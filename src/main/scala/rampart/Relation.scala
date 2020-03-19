package rampart

/**
  * Describes how two [[Interval]]s relate to each other. There
  * are 13 possible relations. Taken together these relations
  * are mutually exclusive and exhaustive.
  *
  * Use `Relation.relate` to determine the relation between two [[Interval]]s.
 **/
sealed trait Relation
object Relation {

  /**
    * 'Interval' `x` is before 'Interval' `y`.
    *
    * {{{
    * 'greater' x '<' 'lesser' y
    * }}}
    *
    * {{{
    *  +---+
    *  | x |
    *  +---+
    *        +---+
    *        | y |
    *        +---+
    * }}}
    */
  case object Before extends Relation

  /**
    * 'Interval' `x` meets 'Interval' `y`.
    *
    * {{{
    * 'greater' x '==' 'lesser' y
    * }}}
    *
    * {{{
    *  +---+
    *  | x |
    *  +---+
    *      +---+
    *      | y |
    *      +---+
    * }}}
    */
  case object Meets extends Relation

  /**
    * 'Interval' `x` overlaps 'Interval' `y`.
    *
    * {{{
    * 'lesser' x '<' 'lesser' y '&&'
    * 'greater' x '' 'lesser' y '&&'
    * 'greater' x '<' 'greater' y
    * }}}
    *
    * {{{
    *  +---+
    *  | x |
    *  +---+
    *    +---+
    *    | y |
    *    +---+
    * }}}
    */
  case object Overlaps extends Relation

  /**
    * 'Interval' `x` is finished by 'Interval' `y`.
    *
    * {{{
    * 'lesser' x '<' 'lesser' y '&&'
    * 'greater' x '==' 'greater' y
    * }}}
    *
    * {{{
    *  +-----+
    *  |  x  |
    *  +-----+
    *    +---+
    *    | y |
    *    +---+
    * }}}
    */
  case object FinishedBy extends Relation

  /**
    * 'Interval' `x` contains 'Interval' `y`.
    *
    * {{{
    * 'lesser' x '<' 'lesser' y '&&'
    * 'greater' x '' 'greater' y
    * }}}
    *
    * {{{
    *  +-------+
    *  |   x   |
    *  +-------+
    *    +---+
    *    | y |
    *    +---+
    * }}}
    */
  case object Contains extends Relation

  /**
    * 'Interval' `x` starts 'Interval' `y`.
    *
    * {{{
    * 'lesser' x '==' 'lesser' y '&&'
    * 'greater' x '<' 'greater' y
    * }}}

    * {{{
    *  +---+
    *  | x |
    *  +---+
    *  +-----+
    *  |  y  |
    *  +-----+
    * }}}
    */
  case object Starts extends Relation

  /**
    * 'Interval' `x` is equal to 'Interval' `y`.
    *
    * {{{
    * 'lesser' x '==' 'lesser' y '&&'
    * 'greater' x '==' 'greater' y
    * }}}
    *
    * {{{
    *  +---+
    *  | x |
    *  +---+
    *  +---+
    *  | y |
    *  +---+
    * }}}
    */
  case object Equal extends Relation

  /**
    * 'Interval' `x` is started by 'Interval' `y`.
    *
    * {{{
    * 'lesser' x '==' 'lesser' y '&&'
    * 'greater' x '' 'greater' y
    * }}}
    *
    * {{{
    *  +-----+
    *  |  x  |
    *  +-----+
    *  +---+
    *  | y |
    *  +---+
    * }}}
    */
  case object StartedBy extends Relation

  /**
    * 'Interval' `x` is during 'Interval' `y`.
    *
    * {{{
    * 'lesser' x '' 'lesser' y '&&'
    * 'greater' x '<' 'greater' y
    * }}}
    *
    * {{{
    *    +---+
    *    | x |
    *    +---+
    *  +-------+
    *  |   y   |
    *  +-------+
    * }}}
    */
  case object During extends Relation

  /**
    * 'Interval' `x` finishes 'Interval' `y`.
    *
    * {{{
    * 'lesser' x '' 'lesser' y '&&'
    * 'greater' x '==' 'greater' y
    * }}}
    *
    * {{{
    *    +---+
    *    | x |
    *    +---+
    *  +-----+
    *  |  y  |
    *  +-----+
    * }}}
    */
  case object Finishes extends Relation

  /**
    * 'Interval' `x` is overlapped by 'Interval' `y`.
    *
    * {{{
    * 'lesser' x '' 'lesser' y '&&'
    * 'lesser' x '<' 'greater' y '&&'
    * 'greater' x '' 'greater' y
    * }}}
    *
    *    +---+
    *    | x |
    *    +---+
    *  +---+
    *  | y |
    *  +---+
    * }}}
    */
  case object OverlappedBy extends Relation

  /**
    * 'Interval' `x` is met by 'Interval' `y`.
    *
    * {{{
    * 'lesser' x '==' 'greater' y
    * }}}
    *
    *      +---+
    *      | x |
    *      +---+
    *  +---+
    *  | y |
    *  +---+
    * }}}
    */
  case object MetBy extends Relation

  /**
    * 'Interval' `x` is after 'Interval' `y`.
    *
    * {{{
    * 'lesser' x '' 'greater' y
    * }}}
    *
    * {{{
    *        +---+
    *        | x |
    *        +---+
    *  +---+
    *  | y |
    *  +---+
    * }}}
    */
  case object After extends Relation

  val values = Seq(
    After,
    Before,
    Contains,
    During,
    Equal,
    FinishedBy,
    Finishes,
    Meets,
    MetBy,
    OverlappedBy,
    Overlaps,
    StartedBy,
    Starts
  )

  /**
    * Inverts a [[Relation]]. Every 'Relation' has an inverse.
    *
    * {{{
    * invert(Before)       == After
    * invert(After)        == Before
    * invert(Meets)        == MetBy
    * invert(MetBy)        == Meets
    * invert(Overlaps)     == OverlappedBy
    * invert(OverlappedBy) == Overlaps
    * invert(Starts)       == StartedBy
    * invert(StartedBy)    == Starts
    * invert(Finishes)     == FinishedBy
    * invert(FinishedBy)   == Finishes
    * invert(Contains)     == During
    * invert(During)       == Contains
    * invert(Equal)        == Equal
    * }}}
    *
    * Inverting a Relation twice will return the original Relation.
    * {{{
    * invert(invert(r)) == r
    * }}}
    *
    * Inverting a Relation is like swapping the arguments to [[Interval#relate]].
    * {{{
    * invert(relate(x,y)) == relate(y,x)
    * }}}
   **/
  def invert(relation: Relation): Relation =
    relation match {
      case After        => Before
      case Before       => After
      case Contains     => During
      case During       => Contains
      case Equal        => Equal
      case FinishedBy   => Finishes
      case Finishes     => FinishedBy
      case Meets        => MetBy
      case MetBy        => Meets
      case OverlappedBy => Overlaps
      case Overlaps     => OverlappedBy
      case StartedBy    => Starts
      case Starts       => StartedBy
    }
}
