package dev.`110416`.munkres
import org.scalatest.matchers.should.Matchers
import org.scalatest.flatspec.AnyFlatSpec
import scala.language.postfixOps
class MunkresTest extends AnyFlatSpec with Matchers:
    val munkres = Munkres
    // 4 x 4
    val m1 = Array(
      Array(5.0, 4.0, 7, 6.0),
      Array(6.0, 7.0, 3.0, 2),
      Array(8, 11, 2.0, 5),
      Array(9, 8, 6.0, 7)
    )
    // 5 x 5
    val m2 = Array(
      Array(993, 947, 817, 561, 137),
      Array(503, 617, 437, 921, 27),
      Array(713, 937, 657, 831, 417),
      Array(373, 657, 227, 41, 57),
      Array(233, 527, 897, 301, 697)
    )
    val m3 = Array(
      Array(10.1, 10.2, 8.3),
      Array(9.4, 8.5, 1.6),
      Array(9.7, 7.8, 4.9)
    )
    "Munkres.cost(nxn matrix)" should "calculate assignment cost" in {
        munkres.cost(m1) shouldBe 17.0
        munkres.cost(m2) shouldBe 1685
        munkres.cost(m3) shouldBe 19.5
    }
    val verticalRect = Array(
      Array(1.0, 1.0),
      Array(1.0, 1.0),
      Array(1.0, 1.0),
      Array(1.0)
    )
    val horizontalRect = Array(
      Array(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0),
      Array(1.0, 1.0, 1.0, 1.0, 1.0, 1.0)
    )
    val afterPadding1 = munkres.padRectangle(verticalRect)
    val afterPadding2 = munkres.padRectangle(horizontalRect)
    "Munkres.padRectangle(nxm matrix)" should "create n'xn' matrix where n' is max(n,m)" in {
        afterPadding1.length shouldBe 4
        afterPadding1.forall(row => row.length == 4) shouldBe true
        afterPadding2.length shouldBe 7
        afterPadding2.forall(row => row.length == 7) shouldBe true
    }
