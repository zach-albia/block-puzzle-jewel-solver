import scala.{Vector => X}

object Common {
  val boardSize: (Int, Int) = (8, 8)
  val piecesPerRound: Int = 3
  def chars(occupied: Boolean): String = if (occupied) "*" else "O"

  def removeFirst[T](xs: X[T], x: T): X[T] = {
    val i = xs.indexOf(x)
    if (i == -1) xs else xs.patch(i, Nil, 1)
  }
}