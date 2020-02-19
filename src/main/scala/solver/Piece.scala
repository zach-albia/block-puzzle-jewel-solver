package solver

import scala.{ Vector => X }

import cats.implicits._

sealed abstract case class Piece private (blocks: X[(Int, Int)]) {
  override def toString: String =
    getClass.getSimpleName.replace("$", "")
}

object Piece {

  def fromString(str: String): Option[Piece] = str match {
    case "One"   => One.some
    case "Vert2" => Vert2.some
    case "Hori2" => Hori2.some
    case "Vert3" => Vert3.some
    case "Hori3" => Hori3.some
    case "UpRt3" => UpRt3.some
    case "UpLf3" => UpLf3.some
    case "DnRt3" => DnRt3.some
    case "DnLf3" => DnLf3.some
    case "Hori4" => Hori4.some
    case "Vert4" => Vert4.some
    case "Box4"  => Box4.some
    case "RtS4"  => RtS4.some
    case "UpS4"  => UpS4.some
    case "InvrT" => InvrT.some
    case "RghtT" => RghtT.some
    case "RLngL" => RLngL.some
    case "LTalL" => LTalL.some
    case "Hori5" => Hori5.some
    case "Vert5" => Vert5.some
    case "UpLf5" => UpLf5.some
    case "UpRt5" => UpRt5.some
    case "DnLf5" => DnLf5.some
    case "DnRt5" => DnRt5.some
    case "Box9"  => Box9.some
    case _       => None
  }

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
        X((0, 0), (0, 1), (0, 2), (1, 0), (1, 1), (1, 2), (2, 0), (2, 1), (2, 2))
      )
}
