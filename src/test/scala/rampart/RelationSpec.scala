package rampart

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RelationSpec extends AnyFlatSpec with Matchers {
  "Relation" should "be invertible" in {
    import Relation.invert

    Relation.values.map(r => invert(invert(r))) shouldBe Relation.values
  }
}
