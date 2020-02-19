import scala.{ Vector => X }

package object solver {
  val boardSize: (Int, Int)            = (8, 8)
  val piecesPerRound: Int              = 3
  def chars(occupied: Boolean): String = if (occupied) "@" else "-"
  val closednessWeights                = List(0, 1, 2, 3, 3, 2, 1, 0)

  def removeFirst[T](xs: X[T], x: T): X[T] = {
    val i = xs.indexOf(x)
    if (i == -1) xs else xs.patch(i, Nil, 1)
  }
}
