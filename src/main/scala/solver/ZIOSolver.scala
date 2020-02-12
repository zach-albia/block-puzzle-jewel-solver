package solver

import zio.DefaultRuntime
import zio.stream._

case class ZIOSolver(
    implicit concurrency: Int = Runtime.getRuntime.availableProcessors)
    extends Solver {

  private val runtime = new DefaultRuntime {}

  override def bestMoveSeq(gameState: Game): Option[LegalMoveSeq] = {
    val permutations = gameState.hand.permutations
    runtime.unsafeRun(
      Stream
        .fromIterable(permutations.toSet)
        .flatMapPar(concurrency)(allLegalMoveSeqs(gameState))
        .run(Sink.foldLeft(Option.empty[LegalMoveSeq])(
          (a: Option[LegalMoveSeq], b: LegalMoveSeq) => a match {
            case None => Some(b)
            case Some(s) => Some(chooseBetterMoveSeq(s, b))
          }))
    )
  }

  def allLegalMoveSeqs(startingState: Game)(handPermutation: Vector[Piece]) = {
    allLegalMoves(startingState)(handPermutation)
      .filter(_.size == handPermutation.size)
      .map(LegalMoveSeq(startingState, _))
  }

  def allLegalMoves(currentGameState: Game)(
    handPermutation: Vector[Piece]): Stream[Nothing, List[Move]] = {
    if (handPermutation.isEmpty) Stream(List.empty[Move])
    else {
      for {
        move <- currentLegalMoves(currentGameState, handPermutation.head)
        nextGameState = currentGameState.move(move).get
        nextMoves <- allLegalMoves(nextGameState)(handPermutation.tail)
      } yield { move :: nextMoves }
    }
  }

  private def currentLegalMoves(gameState: Game, piece: Piece) =
    Stream
      .fromIterable(gameState.board.spots)
      .filter(spot => gameState.isLegal(Move(piece, spot)))
      .map(Move(piece, _))

  private def chooseBetterMoveSeq(moveA: LegalMoveSeq, moveB: LegalMoveSeq) =
    if (moveA > moveB) moveB else moveA
}
