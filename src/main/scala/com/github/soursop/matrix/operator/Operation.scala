package com.github.soursop.matrix.operator

object Operation {

  trait Op
  type Copyable = {
    def copy(nodes: List[Op]): Ops
  }
  object Ops {
    type Copyable = {
      def copy(nodes: List[Op]): Ops
    }
    implicit def toCopyable( base: Ops ): Ops with Copyable = base.asInstanceOf[Ops with Copyable]
  }
  sealed abstract class Ops(val nodes: List[Op]) extends Op {
    this : Ops.Copyable =>
  }
  case class Multiply(override val nodes: List[Op]) extends Ops(nodes)
  case class Divide(override val nodes: List[Op]) extends Ops(nodes)
  case class Plus(override val nodes: List[Op]) extends Ops(nodes)
  case class Minus(override val nodes: List[Op]) extends Ops(nodes)
  case class Transpose(node: Op) extends Op
  case class Terminal(text: String) extends Op

//  def toTail(op: List[Op]): Ops = this(nodes :: op)
//  def toHead(op: Op): Ops = this(op :: nodes)
//  trait Terminal extends Op
//  case class Matrix(height: Int, width: Int) extends Terminal
//  case class Vector(size: Int) extends Terminal


  def parsing(text: String): Op = {
    take("", None, 0, text)
  }

  def sameType[T, U](a: T, b: U)(implicit evidence: T =:= U) = true

  def take(buffer: String, ops: Option[Ops], depth: Int, text: String): Op = {
    def wrap(ops: Option[Ops], op: Ops): Ops = ops match {
      case None => op
      case Some(exist: Ops) =>
          op.copy(exist :: op.nodes)
    }
    if (text.isEmpty) {
      ops match {
        case None => Terminal(buffer)
        case Some(ops) => ops.copy(ops.nodes :+ Terminal(buffer))
      }
    } else {
      text.head match {
        case '(' => take(buffer, ops, depth + 1, text.tail)
        case ')' => take(buffer, ops, depth - 1, text.tail)
        case '+' if (depth == 0) =>
          val w = wrap(ops, Plus(parsing(buffer) :: Nil))
          println('+', ops, buffer, "\n", w)
          take("", Some(w), depth, text.tail)
        case '-' if (depth == 0) =>
          val w = wrap(ops, Minus(parsing(buffer) :: Nil))
          println('-', ops, buffer, "\n", w)
          take("", Some(w), depth, text.tail)
        case c => take(buffer + c, ops, depth, text.tail)
      }
    }
  }

  def main(args: Array[String]): Unit = {
    // build
    val query = "((2:3 * 3:2') + 4) + 2' - 2'"
    val words = parsing(query)

    println(words)
  }

}
