package solver

object Main {
  import Piece._

  def main(args: Array[String]): Unit =
    println(Solver.bestMoveSeq(Game.starting(Hori5, Vert4, RLngL))(4))
}
