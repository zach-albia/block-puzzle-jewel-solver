package solver

case class LegalMoveSeq(game: Game, moves: List[Move]) extends Ordered[LegalMoveSeq] {

  lazy val endState: Game =
    moves.foldLeft(game)((game, move) => game.move(move).get)

  lazy val heuristic: Heuristic =
    Heuristic(endState.board.blocksOccupied, endState.board.closednessIndex)

  override def compare(that: LegalMoveSeq): Int =
    this.heuristic compare that.heuristic
}
