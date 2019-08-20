package solver

object Main {
  import Piece._

  def main(args: Array[String]): Unit = {
    val t0 = System.nanoTime()
    val result = Solver.bestMoveSeq(Game.starting(Hori5, Vert4, One))(12)
    val t1 = System.nanoTime()
    println(result)
    println(s"Elapsed time: ${(t1 - t0) / Math.pow(10, 9)} seconds")
  }
}
