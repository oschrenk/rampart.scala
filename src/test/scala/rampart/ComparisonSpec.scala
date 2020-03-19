package rampart

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ComparisonSpec extends AnyFlatSpec with Matchers {
  "Comparison" should "be comparable" in {
    import Comparison._
    import cats.implicits._

    Comparison.compare(1, 2) shouldBe LessThan
    Comparison.compare(2, 2) shouldBe Equals
    Comparison.compare(2, 1) shouldBe GreaterThan
  }
}
