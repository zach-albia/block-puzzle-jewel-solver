package solver

import cats.effect.IO
import fs2.Stream

import scala.concurrent.ExecutionContext.Implicits.global

object Solver {
  val emptyStream = Stream(List.empty[Move]).covary[IO]
  private def i(): Int = Runtime.getRuntime.availableProcessors()

  def bestMoveSeq(gameState: Game)
                 (implicit concurrency: Int = i()): Option[LegalMoveSeq] =
    Stream(gameState.hand.permutations.toStream.distinct: _*).covary[IO]
      .map(allLegalMoveSeqs(gameState, concurrency))
      .join(concurrency)
      .reduce(chooseBetterMove)
      .compile.toList.unsafeRunSync().headOption

  private def allLegalMoveSeqs(startingState: Game, concurrency: Int = i())
                              (handPermutation: Vector[Piece]) = {
    def allLegalMoves(currentGameState: Game)(handPermutation: Vector[Piece]): Stream[IO, List[Move]] = {
      if (handPermutation.isEmpty) emptyStream
      else {
        val moves = currentLegalMoves(currentGameState, handPermutation.head)
        moves.head.last.map[Stream[IO, List[Move]]](firstElement =>
          if (firstElement.isEmpty) emptyStream
          else for {
            move <- moves
            nextGameState = currentGameState.move(move).get
            nextMoves <- allLegalMoves(nextGameState)(handPermutation.tail)
          } yield move +: nextMoves
        ).join(concurrency)
      }
    }

    allLegalMoves(startingState)(handPermutation)
      .filter(_.size == handPermutation.size)
      .map(LegalMoveSeq(startingState, _))
  }

  private def currentLegalMoves(gameState: Game, piece: Piece) =
    Stream(gameState.board.spots: _*)
      .filter(spot => gameState.isLegal(Move(piece, spot)))
      .map(Move(piece, _))
      .covary[IO]

  private def chooseBetterMove(moveA: LegalMoveSeq, moveB: LegalMoveSeq) =
    if (moveA > moveB) moveB else moveA
}
