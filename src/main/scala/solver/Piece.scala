package solver

import scala.{Vector => X}

sealed abstract case class Piece private (blocks: X[(Int, Int)]) {
  override def toString: String = this.getClass.getName
}

object Piece {

  // *
  object One extends Piece(X((0, 0)))

  // *
  // *
  object Vert2 extends Piece(X((0, 0), (1, 0)))

  // * *
  object Hori2 extends Piece(X((0, 0), (0, 1)))

  // *
  // *
  // *
  object Vert3 extends Piece(X((0, 0), (1, 0), (2, 0)))

  // * * *
  object Hori3 extends Piece(X((0, 0), (0, 1), (0, 2)))

  // * *
  // *
  object UpRt3 extends Piece(X((0, 0), (0, 1), (1, 0)))

  // * *
  //   *
  object UpLf3 extends Piece(X((0, 0), (0, 1), (1, 1)))

  // *
  // * *
  object DnRt3 extends Piece(X((0, 0), (1, 0), (1, 1)))

  //   *
  // * *
  object DnLf3 extends Piece(X((0, 1), (1, 0), (1, 1)))

  // * * * *
  object Hori4 extends Piece(X((0, 0), (0, 1), (0, 2), (0, 3)))

  // *
  // *
  // *
  // *
  object Vert4 extends Piece(X((0, 0), (1, 0), (2, 0), (3, 0)))

  // * *
  // * *
  object Box4 extends Piece(X((0, 0), (0, 1), (1, 0), (1, 1)))

  //   * *
  // * *
  object RtS4 extends Piece(X((0, 1), (0, 2), (1, 0), (1, 1)))

  // *
  // * *
  //   *
  object UpS4 extends Piece(X((0, 0), (1, 0), (1, 1), (2, 1)))

  //   *
  // * * *
  object InvrT extends Piece(X((0, 1), (1, 0), (1, 1), (1, 2)))

  //   *
  // * *
  //   *
  object RghtT extends Piece(X((0, 1), (1, 0), (1, 1), (2, 1)))

  // *
  // * * *
  object RLngL extends Piece(X((0, 0), (1, 0), (1, 1), (1, 2)))

  //   *
  //   *
  // * *
  object LTalL extends Piece(X((0, 1), (1, 1), (2, 0), (2, 1)))

  // * * * * *
  object Hori5 extends Piece(X((0, 0), (0, 1), (0, 2), (0, 3), (0, 4)))

  // *
  // *
  // *
  // *
  // *
  object Vert5 extends Piece(X((0, 0), (1, 0), (2, 0), (3, 0), (4, 0)))

  // * * *
  //     *
  //     *
  object UpLf5 extends Piece(X((0, 0), (0, 1), (0, 2), (1, 2), (2, 2)))

  // * * *
  // *
  // *
  object UpRt5 extends Piece(X((0, 0), (0, 1), (0, 2), (1, 0), (2, 0)))

  //     *
  //     *
  // * * *
  object DnLf5 extends Piece(X((0, 2), (1, 2), (2, 0), (2, 1), (2, 2)))

  // *
  // *
  // * * *
  object DnRt5 extends Piece(X((0, 0), (1, 0), (2, 0), (2, 1), (2, 2)))

  // * * *
  // * * *
  // * * *
  object Box9
      extends Piece(
        X((0, 0),
          (0, 1),
          (0, 2),
          (1, 0),
          (1, 1),
          (1, 2),
          (2, 0),
          (2, 1),
          (2, 2))
      )
}
