package solver

import scala.{Vector => X}

case class Board(blocks: Vector[Vector[Boolean]]) {
  def spots: Stream[(Int, Int)] = {
    val (rowSize, colSize) = size
    boardSpots(rowSize, colSize)
  }

  private def boardSpots(rowSize: Int, columnSize: Int) = for {
    row <- (0 until rowSize).toStream
    col <- (0 until columnSize).toStream
  } yield (row, col)

  val size: (Int, Int) = (blocks.size, blocks.head.size)

  lazy val blocksOccupied: Int =
    blocks.foldLeft(0)((count, row) => row.count(identity) + count)

  lazy val closednessIndex: Int =
    columnClosedness + rowClosedness

  lazy val columnClosedness: Int =
    segmentClosedness(column, size._2)

  lazy val rowClosedness: Int =
    segmentClosedness(row, size._1)

  private def segmentClosedness(segmentByIndex: Int => Stream[Boolean], size: Int) =
    (0 until size).toStream
      .map(i => score(segmentByIndex(i), i))
      .sum

  private def score(segment: Stream[Boolean], i: Int) =
    segment.map(occupied => if (occupied) 1 + closednessWeights(i) else 0).sum

  def isOccupied(row: Int, column: Int): Boolean =
    blocks(row)(column)

  def clear(lastMove: Move): Board = {
    val rows = lastMove.rows.map(rowFormsLine)
    val columns = lastMove.columns.map(columnsFormLine)
    this.clearRows(rows.filter(_._2).map(_._1))
        .clearColumns(columns.filter(_._2).map(_._1))
  }

  private def rowFormsLine(i: Int) =
    (i, row(i).forall(identity))

  private def row(i: Int) = blocks(i).toStream

  private def columnsFormLine(i: Int) =
    (i, column(i).forall(identity))

  private def column(i: Int) = blocks.map(_(i)).toStream

  def clearRows(indices: Set[Int]): Board =
    copy(indices.foldLeft(blocks)((blocks, i) => blocks.updated(i, X.fill(row(i).size)(false))))

  def clearColumns(indices: Set[Int]): Board =
    copy(indices.foldLeft(blocks)((blocks, i) => blocks.map(_.updated(i, false))))

  def occupy(spot: (Int, Int)): Board = Board(
    blocks.updated(spot._1, row(spot._1).updated(spot._2, true).toVector)
  )

  override def toString: String =
    "\n" + blocks.map(_.map(chars).mkString(" ")).mkString("\n") + "\n"
}

object Board {
  def apply(size: (Int, Int)) = new Board(X.fill(size._1, size._2)(false))
  def apply(blocks: X[Int]*) = new Board(blocks.toVector.map(_.map(_ != 0)))
  val starting = apply(boardSize)
}
