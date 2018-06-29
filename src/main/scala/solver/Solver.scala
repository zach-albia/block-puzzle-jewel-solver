package solver

import fs2.Stream
import cats.effect.IO
import scala.concurrent.ExecutionContext.Implicits.global

object Solver {
  def bestMoveSeq(gameState: Game): Option[LegalMoveSeq] =
    Stream(gameState.hand.permutations.toStream.distinct: _*).covary[IO]
      .map(allLegalMoveSeqs(gameState))
      .join(Runtime.getRuntime.availableProcessors)
      .reduce(chooseBetterMove)
      .compile.toList.unsafeRunSync().headOption

  private def allLegalMoveSeqs(startingState: Game)(handPermutation: Vector[Piece]) = {
    def allLegalMoves(currentGameState: Game)(handPermutation: Vector[Piece]): Stream[IO, List[Move]] = {
      val emptyStream = Stream(List.empty[Move]).covary[IO]
      if (handPermutation.isEmpty) emptyStream
      else {
        val moves = currentLegalMoves(currentGameState, handPermutation.head)
        if (moves.head.compile.toList.unsafeRunSync().isEmpty) emptyStream
        else for {
          move <- moves
          nextGameState = currentGameState.move(move).get
          nextMoves <- allLegalMoves(nextGameState)(handPermutation.tail)
        } yield move +: nextMoves
      }
    }

    allLegalMoves(startingState)(handPermutation).map(LegalMoveSeq(startingState, _))
      .filter(_.moves.size == handPermutation.size)
  }

  private def currentLegalMoves(gameState: Game, piece: Piece) =
    Stream(gameState.board.spots: _*)
      .filter(spot => gameState.isLegal(Move(piece, spot)))
      .map(Move(piece, _))
      .covary[IO]

  private def chooseBetterMove(moveA: LegalMoveSeq, moveB: LegalMoveSeq) =
    if (moveA > moveB) moveB else moveA
}
