import scala.{Vector => X}

case class Move(piece: Piece, spot: (Int, Int)) {
  val spots: X[(Int, Int)] =
    piece.blocks.map { case (row, column) => (row + spot._1, column + spot._2) }

  val rows: Set[Int] = spots.foldLeft(Set.empty[Int])((set, spot) => set + spot._1)
  val columns: Set[Int] = spots.foldLeft(Set.empty[Int])((set, spot) => set + spot._2)
}