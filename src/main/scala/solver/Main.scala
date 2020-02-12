package solver

object Main {
  import Piece._

  def main(args: Array[String]): Unit = {
    val solver = ZIOSolver()(Runtime.getRuntime.availableProcessors)
    val t0 = System.nanoTime()
    val result = solver.bestMoveSeq(Game.starting(One, Vert2, Hori2))
    val t1 = System.nanoTime()
    println(result)
    println(s"Elapsed time: ${(t1 - t0) / Math.pow(10, 9)} seconds")
  }
}
