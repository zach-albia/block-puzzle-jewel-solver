package solver

import scala.{Vector => X}

object Main {
  import Piece._

  def main(args: Array[String]): Unit = {
    val start = Game(Board.starting, X(Vert4, Box4, Hori5))
    val bestMove = Solver.bestMoveSeq(start)
    bestMove.foreach(move => {
      println(move)
      println(move.heuristic)
    })
  }
}
