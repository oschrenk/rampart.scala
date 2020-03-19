package rampart

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class IntervalSpec extends AnyFlatSpec with Matchers {

  "Interval" should "relate" in {
    import Interval.{relate, toInterval}
    import Relation._
    import cats.implicits._

    relate(toInterval(1, 2), toInterval(3, 7)) shouldBe Before
    relate(toInterval(2, 3), toInterval(3, 7)) shouldBe Meets
    relate(toInterval(2, 4), toInterval(3, 7)) shouldBe Overlaps
    relate(toInterval(2, 7), toInterval(3, 7)) shouldBe FinishedBy
    relate(toInterval(2, 8), toInterval(3, 7)) shouldBe Contains
    relate(toInterval(3, 4), toInterval(3, 7)) shouldBe Starts
    relate(toInterval(3, 7), toInterval(3, 7)) shouldBe Equal
    relate(toInterval(3, 8), toInterval(3, 7)) shouldBe StartedBy
    relate(toInterval(4, 6), toInterval(3, 7)) shouldBe During
    relate(toInterval(6, 7), toInterval(3, 7)) shouldBe Finishes
    relate(toInterval(6, 8), toInterval(3, 7)) shouldBe OverlappedBy
    relate(toInterval(7, 8), toInterval(3, 7)) shouldBe MetBy
    relate(toInterval(8, 9), toInterval(3, 7)) shouldBe After
  }
}
