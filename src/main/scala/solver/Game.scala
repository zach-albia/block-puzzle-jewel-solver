package solver

import scala.{Vector => X}

case class Game(board: Board, hand: X[Piece]) {
  import Common._

  def move(piece: Piece, spot: (Int, Int)): Option[Game] =
    move(Move(piece, spot))

  def move(move: Move): Option[Game] =
    if (isLegal(move)) doMove(move) else None

  def isLegal(move: Move): Boolean =
    hand.contains(move.piece) &&
    move.spots.forall({ case (row, column) =>
      withinBoard(row, column) && !board.isOccupied(row, column)})

  private def withinBoard(row: Int, column: Int) =
    0 <= row && row <= board.blocks.size &&
    0 <= column && column <= board.blocks(row).size

  private def doMove(move: Move): Option[Game] = Some(
    Game(
      occupy(move).clear(move),
      removeFirst(hand, move.piece)
    )
  )

  private def occupy(move: Move) = {
    move.spots.foldLeft(board)((board, spot) => board.occupy(spot))
  }

  def deal(hand: X[Piece]): Option[Game] =
    if (this.hand.isEmpty) Some(this.copy(hand = hand)) else None
}
