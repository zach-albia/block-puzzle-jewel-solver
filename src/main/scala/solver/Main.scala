package solver

import scala.{Vector => X}

object Main {
  import Piece._

  def main(args: Array[String]): Unit = {
    val start = Game(Board.starting, X(One))
    for {
      a <- start.move(One, (0, 0))
      _ = println(s"Current state:\n$a")
    } yield ()
  }
}
