package solver

import fs2.{Pure, Stream}

object Solver {
  def bestMoveSeq(gameState: Game): Option[LegalMoveSeq] =
    Stream(gameState.hand.permutations.toStream.distinct: _*)
      .map(allLegalMoveSeqs(gameState))
      .map(bestMoveForPermutation)
      .reduce(betterMoveOption)
      .toList.head

  private def allLegalMoveSeqs(startingState: Game)(handPermutation: Vector[Piece]) = {
    def allLegalMoves(currentGameState: Game)(handPermutation: Vector[Piece]): Stream[Pure, List[Move]] =
      if (handPermutation.isEmpty) Stream(List.empty[Move])
      else for {
        move <- currentLegalMoves(currentGameState, handPermutation.head)
        nextGameState = currentGameState.move(move).get
        nextMoves <- allLegalMoves(nextGameState)(handPermutation.tail)
      } yield move +: nextMoves

    allLegalMoves(startingState)(handPermutation).map(LegalMoveSeq(startingState, _))
  }

  private def currentLegalMoves(gameState: Game, piece: Piece) =
    Stream(gameState.board.spots: _*)
      .filter(spot => gameState.isLegal(Move(piece, spot)))
      .map(Move(piece, _))

  private def bestMoveForPermutation(handMoves: Stream[Pure, LegalMoveSeq]): Option[LegalMoveSeq] =
    handMoves.reduce((a, b) => chooseBetterMove(a, b)).toList.headOption

  private def chooseBetterMove(moveA: LegalMoveSeq, moveB: LegalMoveSeq) =
    if (moveA > moveB) moveB else moveA

  private def betterMoveOption(a: Option[LegalMoveSeq], b: Option[LegalMoveSeq]): Option[LegalMoveSeq] = for {
    aa <- a
    bb <- b
  } yield chooseBetterMove(aa, bb)
}
