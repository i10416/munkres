package dev.i10416.munkres
import org.scalatest.matchers.should.Matchers
import org.scalatest.flatspec.AnyFlatSpec
import scala.language.postfixOps
class MunkresTest extends AnyFlatSpec with Matchers:
    "SelectZerosFromMatrix" should "find all zeros from matrix" in {
        val m0x0 = Array.empty[Array[Int]]
        val m2x2 = Array(
          Array(1, 0),
          Array(0, 1)
        )
        val m3x3 = Array(
          Array(0, 1, 1),
          Array(1, 0, 2),
          Array(-1, -2, 0)
        )
        val m3x3WithoutZero = Array(
          Array(1, 2, 3),
          Array(1, 2, 3),
          Array(1, 2, 3)
        )
        Munkres.selectZerosFromMatrix(m0x0) shouldBe Set.empty
        Munkres.selectZerosFromMatrix(m2x2) shouldBe Set((0, 1), (1, 0))
        Munkres.selectZerosFromMatrix(m3x3) shouldBe Set((0, 0), (1, 1), (2, 2))
        Munkres.selectZerosFromMatrix(m3x3WithoutZero) shouldBe Set.empty
    }
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
    // 3 x 3
    val m3 = Array(
      Array(10.1, 10.2, 8.3),
      Array(9.4, 8.5, 1.6),
      Array(9.7, 7.8, 4.9)
    )
    // 3 x 3
    val m4 = Array(
      Array(5, 9, 1),
      Array(10, 3, 2),
      Array(8, 7, 4)
    )
    // 3 x 3
    val m5 = Array(
      Array(5.1, 9.2, 1.3),
      Array(10.4, 3.5, 2.6),
      Array(8.7, 7.8, 4.9)
    )

    val m6 = Array(
      Array(12, 9, 27, 10, 23),
      Array(7, 13, 13, 30, 19),
      Array(25, 18, 26, 11, 26),
      Array(9, 28, 26, 23, 13),
      Array(16, 16, 24, 6, 9)
    )

    "Munkres.cost(nxn matrix)" should "calculate assignment cost" in {
        munkres.cost(m1) shouldBe 17.0
        munkres.cost(m2) shouldBe 1685
        munkres.cost(m3) shouldBe 19.5
        munkres.cost(m4) shouldBe 12.0
        munkres.cost(m5) shouldBe 13.5
        munkres.cost(m6) shouldBe 51.0
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
    "Munkres.padRectangle(n x m matrix)" should "create n' x n' matrix where n' is max(n,m)" in {
        afterPadding1.length shouldBe 4
        afterPadding1.forall(row => row.length == 4) shouldBe true
        afterPadding2.length shouldBe 7
        afterPadding2.forall(row => row.length == 7) shouldBe true
    }
