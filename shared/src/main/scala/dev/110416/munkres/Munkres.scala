package dev.i10416.munkres

import scala.annotation.tailrec
import scala.scalajs.js.annotation.JSExportTopLevel
import scala.scalajs.js.annotation.JSExport
import scala.collection.mutable.{ Set => MSet }
import java.util.LinkedList

/** Munkres Algorithm (also known as Hungarian algorithm or the Kuhn-Munkres algorithm)
  * implementation for Scala
  *
  * For more detail, visit
  *   - https://csclab.murraystate.edu/~bob.pilgrim/445/munkres.html
  *   - https://github.com/bmc/munkres
  */
@JSExportTopLevel("Munkres")
object Munkres:

    import math.Numeric.Implicits.infixNumericOps
    import math.Ordering.Implicits.infixOrderingOps
    import scala.reflect.ClassTag

    trait FiniteRange[T]:
        def maxValue: T
        def minValue: T

    given FiniteRange[Float] with
        def maxValue = Float.MaxValue
        def minValue = Float.MinValue

    given FiniteRange[Int] with
        def maxValue = Int.MaxValue
        def minValue = Int.MinValue

    given FiniteRange[Double] with
        def maxValue = Double.MaxValue
        def minValue = Double.MinValue

    type Matrix[T] = Array[Array[T]]

    def cost[T : Numeric : FiniteRange : ClassTag](matrix: Matrix[T]): T =
        minimize(matrix).foldLeft(Numeric[T].zero) { case (acc, (x, y)) =>
            acc + matrix(x)(y)
        }

    @JSExport
    /** returns the combination that minimizes total cost */
    def minimize[T : FiniteRange : ClassTag : Numeric](matrix: Matrix[T]): Seq[(Int, Int)] =
        val normalized = padRectangle(matrix)
        val n = normalized.length
        val m = subtractMinsFromMatrix(normalized)
        val zeros = selectZerosFromMatrix(m)

        collectZerosFromMatrixRec(m, zeros, n, n)

        // / return minimum values of each row as an array
    private def selectMinsFromRow[T : Numeric : ClassTag](matrix: Matrix[T]): Array[T] =
        val prealloc = Array.fill[T](matrix.length)(Numeric[T].zero)
        var i = 0
        while i < matrix.length do
            prealloc(i) = matrix(i).min
            i += 1
        prealloc
    // / return minimum values of each column as an Array
    private def selectMinsFromCol[T : Numeric : FiniteRange : ClassTag](
        matrix: Matrix[T]
    ): Array[T] =
        matrix.foldLeft(Array.fill[T](matrix.length)(summon[FiniteRange[T]].maxValue)) {
            (mins, row) =>
                row.zip(mins).map { (value, maybeMin) =>
                    if value < maybeMin then value else maybeMin
                }
        }

    // find all locations of zero as (row index,column index): (Int,Int)
    // note that this function expect unitary matrix
    //  0 a b
    //  d 0 f
    //  g h 0
    //  => Set((0,0),(1,1),(2,2))
    private[munkres] def selectZerosFromMatrix[T: Numeric](matrix: Matrix[T]): Set[(Int, Int)] =
        val size = matrix.length
        @tailrec
        def loopOverRowRec(rowIdx: Int, acc: MSet[(Int, Int)] = MSet.empty): MSet[(Int, Int)] =
            rowIdx match
                case outOfBounds if outOfBounds >= size => acc
                case rowIdx =>
                    @tailrec
                    def loopOverColRec(colIdx: Int, acc: MSet[(Int, Int)]): Unit =
                        colIdx match
                            case oob if oob >= size => ()
                            case colIdx =>
                                val elem = matrix(rowIdx)(colIdx)
                                if Numeric[T].zero == elem
                                then
                                    acc.add((rowIdx, colIdx))
                                    loopOverColRec(colIdx + 1, acc)
                                else loopOverColRec(colIdx + 1, acc)
                    loopOverColRec(0, acc)
                    loopOverRowRec(rowIdx + 1, acc)
        loopOverRowRec(0).toSet

    // / find the locations where horizontal lines are crossed with vertical ones.
    private def getIntersections(
        rowLines: Seq[Int],
        colLines: Seq[Int]
    ): Set[(Int, Int)] =
        rowLines.foldLeft(Set(): Set[(Int, Int)]) { case (acc, rowIdx) =>
            colLines.foldLeft(acc) { case (set, colIdx) =>
                set + ((rowIdx, colIdx))
            }
        }
    private def getRemains[T: Numeric](
        mat: Array[Array[T]],
        rowLines: Seq[Int],
        colLines: Seq[Int]
    ): Map[(Int, Int), T] =
        mat.zipWithIndex.foldLeft(Map(): Map[(Int, Int), T]) { case (acc, (row, rowIdx)) =>
            row.zipWithIndex.foldLeft(acc) { case (m, (value, colIdx)) =>
                if (rowLines.contains(rowIdx) || colLines.contains(colIdx))
                    m
                else
                    m.updated((rowIdx, colIdx), value)
            }
        }

    // First, subtract the smallest value in a row from the each element of the row. Then, subtract
    // the smallest value in a column from each element of the column.
    //
    private def subtractMinsFromMatrix[T : Numeric : FiniteRange : ClassTag](
        matrix: Matrix[T]
    ): Matrix[T] =
        val minsFromRow = selectMinsFromRow(matrix)
        val tmpMatrix = matrix
            .zip(minsFromRow)
            .map { (row, min) => row.map(_ - min) }
        val minsFromCol = selectMinsFromCol(tmpMatrix)
        tmpMatrix.map(row => row.zipWithIndex.map((value, colIdx) => value - minsFromCol(colIdx)))

    @tailrec
    private def hideZerosByLines(
        n: Int,
        zeros: Set[(Int, Int)],
        result: (Seq[Int], Seq[Int]) = (Seq(), Seq())
    ): (Seq[Int], Seq[Int]) =
        if (zeros.isEmpty) return result
        getTheLineToHide(zeros) match
            case (Some((rowIdx, locationsInRow)), Some((colIdx, locationsInCol))) =>
                result match
                    case (Nil, seq) => (rowIdx +: Nil, seq)
                    case (seq, Nil) => (seq, colIdx +: Nil)
                    case _          => (rowIdx +: result._1, result._2)
            case (Some((rowIdx, locations)), None) =>
                hideZerosByLines(n - 1, zeros.diff(locations), (rowIdx +: result._1, result._2))
            case (None, Some((colIdx, locations))) =>
                hideZerosByLines(n - 1, zeros.diff(locations), (result._1, colIdx +: result._2))
            case _ => result

    private def sortZeros(
        zeros: Set[(Int, Int)]
    ): (Seq[(Int, Set[(Int, Int)])], Seq[(Int, Set[(Int, Int)])]) =
        (
          zeros.groupBy(_._1).toSeq.sortBy(-_._2.size),
          zeros.groupBy(_._2).toSeq.sortBy(-_._2.size)
        )

    private def shouldHideRow(
        zeros: Set[(Int, Int)],
        horizontal: Set[(Int, Int)],
        vertical: Set[(Int, Int)]
    ) =
        val remainingIndependentZeroWhenHideRow =
            zeros.diff(horizontal).groupBy(_._1).filter(_._2.size == 1).size
        val remainingIndependentZeroWhenHideCol =
            zeros.diff(vertical).groupBy(_._1).filter(_._2.size == 1).size
        remainingIndependentZeroWhenHideRow <= remainingIndependentZeroWhenHideCol

    // / find the line which hides the largest amount of zeros.
    private def getTheLineToHide(
        zeros: Set[(Int, Int)]
    ): (Option[(Int, Set[(Int, Int)])], Option[(Int, Set[(Int, Int)])]) =
        sortZeros(zeros) match
            case ((rowIdx, horizontal) +: tail1, Nil) =>
                (Some(rowIdx, horizontal), None)
            case (Nil, (colIdx, vertical) +: tail2) => (None, Some(colIdx, vertical))
            case ((rowIdx, horizontal) +: tail1, (colIdx, vertical) +: tail2)
                if horizontal.size == vertical.size && zeros.size == 1 =>
                (Some((rowIdx, horizontal)), Some(colIdx, vertical))
            case ((rowIdx, horizontal) +: tail1, (colIdx, vertical) +: tail2)
                if shouldHideRow(zeros, horizontal, vertical) =>
                (Some((rowIdx, horizontal)), None)
            case ((rowIdx, horizontal) +: tail1, (colIdx, vertical) +: tail2) =>
                (None, Some(colIdx, vertical))
            case (Nil, Nil) => (None, None)
    @tailrec
    private def collectZerosFromMatrixRec[T: Numeric](
        m: Matrix[T],
        zeros: Set[(Int, Int)],
        rowCount: Int,
        colCount: Int
    ): Seq[(Int, Int)] = {
        tryCollectZerosFromMatrix(zeros, rowCount, colCount) match
            case Right(result) => result
            case Left(lines) =>
                val (hidedRows, hidedCols) = hideZerosByLines(lines, zeros)
                val intersection = getIntersections(hidedRows, hidedCols)
                val remains = getRemains(m, hidedRows, hidedCols)
                val minFromRemains =
                    if (remains.values.isEmpty) Numeric[T].zero else remains.values.min
                val tmpMatrix = remains.keys.foldLeft(m) { case (mat, (row, col)) =>
                    mat(row)(col) = mat(row)(col) - minFromRemains
                    mat
                }
                val next = intersection.foldLeft(tmpMatrix) { case (mat, (row, col)) =>
                    mat(row)(col) = mat(row)(col) + minFromRemains
                    mat
                }
                val nextZoros = selectZerosFromMatrix(next)
                collectZerosFromMatrixRec(next, nextZoros, rowCount, colCount)

    }
    // / select zeros from identical (row,col), without using the same row or col more than once.
    // / when right, return the locations where valid zeros exist. otherwise,returns the max line number available to hide zeros.
    private def tryCollectZerosFromMatrix(
        zeros: Set[(Int, Int)],
        rowCount: Int,
        colCount: Int
    ): Either[Int, Seq[(Int, Int)]] =
        val collect = zeros.foldLeft((Seq(), Seq()): (Seq[Int], Seq[Int])) {
            case ((row, col), (x, y)) =>
                if !row.contains(x) && !col.contains(y) then (row appended x, col appended y)
                else (row, col)

        }
        collect match
            case (row, col) if row.length >= rowCount && col.length >= colCount =>
                Right(row.zip(col).map { case (r, c) => (r, c) })
            case (row, col) => Left(row.length)

    // / transform N x M Matrix into N' x N' Matrix (where N' = max(N,M)) by padding with zeros
    private[munkres] def padRectangle[T : FiniteRange : Numeric : ClassTag](
        matrix: Matrix[T]
    ): Matrix[T] =
        val rowCount = matrix.length
        val colCount = matrix.foldLeft(0) { (maybeMaxCol, row) =>
            math.max(row.length, maybeMaxCol)
        }
        val n = math.max(rowCount, colCount)
        val tmp = matrix.map { row =>
            row.appendedAll(Array.fill[T](n - row.length)(Numeric[T].zero))
        }
        (0 to n - rowCount - 1).foldLeft(tmp) { (acc, _) =>
            acc.appended(Array.fill(n)(Numeric[T].zero))
        }
