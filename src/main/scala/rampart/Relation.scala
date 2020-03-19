package rampart

sealed trait Relation
object Relation {
  case object After        extends Relation
  case object Before       extends Relation
  case object Contains     extends Relation
  case object During       extends Relation
  case object Equal        extends Relation
  case object FinishedBy   extends Relation
  case object Finishes     extends Relation
  case object Meets        extends Relation
  case object MetBy        extends Relation
  case object OverlappedBy extends Relation
  case object Overlaps     extends Relation
  case object StartedBy    extends Relation
  case object Starts       extends Relation

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
