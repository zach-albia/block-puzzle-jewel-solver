package solver

trait SolverAlg {
  def bestMoveSeq(gameState: Game): Option[LegalMoveSeq]
}
