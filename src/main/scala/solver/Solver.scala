package solver

trait Solver {
  def bestMoveSeq(gameState: Game): Option[LegalMoveSeq]
}
