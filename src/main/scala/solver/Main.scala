package solver

import java.io.IOException

import cats.implicits._
import zio._
import zio.console._
import zio.interop.catz._

object Main extends App {

  override def run(args: List[String]): ZIO[zio.ZEnv, Nothing, Int] =
    (for {
      _     <- putStr("Enter \"b\" to enter a board: ")
      input <- getStrLn
      board <- if (input.toLowerCase() == "b") askForBoard else ZIO.succeed(Board.starting)
      _     <- getStrLn
      game  <- Ref.make(Game(board, Vector.empty))
      _     <- solverLoop(game)
    } yield ())
      .foldM(e => putStrLn(e.getMessage) *> ZIO.succeed(1), _ => ZIO.succeed(1))

  private def askForBoard: ZIO[Console, IOException, Board] =
    for {
      _ <- putStrLn("Enter board below:")
      blocks <- ZIO
                 .sequence(List.fill(8)(for {
                   input <- getStrLn.map(_.toCharArray.map(_ != '0').toVector)
                 } yield input))
                 .map(_.toVector)
    } yield Board(blocks)

  private def solverLoop(game: Ref[Game]): ZIO[Console, IOException, Unit] =
    for {
      input   <- askForHand
      current <- game.get.map(_.deal(input: _*))
      soln    <- current.map(ZIOSolver.bestMoveSeq).sequence.map(_.flatten)
      _ <- soln.fold[ZIO[Console, IOException, Unit]](putStrLn("Game over"))(
            soln =>
              putStrLn(prettyPrint(soln)) *>
                game.set(soln.endState) *>
                solverLoop(game)
          )
    } yield ()

  def prettyPrint(soln: LegalMoveSeq): String =
    s"""Resulting Board:
       |${soln.endState.board.toString}
       |Moves:
       |${soln.moves.map(move => s"${move.piece.toString} to ${move.spot}").mkString("\n")}
       |""".stripMargin

  private def askForHand: ZIO[Console, IOException, Vector[Piece]] =
    for {
      _     <- putStr("Enter pieces:")
      input <- getStrLn
      result <- input
                 .replaceAll("\\s*", "")
                 .split(",")
                 .toVector
                 .map(Piece.fromString)
                 .sequence
                 .fold(putStrLn("Please try again.") *> askForHand)(ZIO.succeed)
    } yield result

}
