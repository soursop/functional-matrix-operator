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
    def + (op: Op): Ops = op match {
      case some: Ops if (getClass.equals(some.getClass)) =>
        copy(nodes ::: some.asInstanceOf[Ops].nodes)
      case some: Ops if (some.nodes.length == 1) => copy(nodes :+ some.nodes.head)
      case _ => copy(nodes :+ op)
    }
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
    parsing("", Plus(Nil), 0, text)
  }

  def parsing(buffer: String, ops: Ops, depth: Int, text: String): Op = {
    if (text.isEmpty) ops + Terminal(buffer.trim)
    else {
      text.head match {
        case '(' => parsing(buffer, ops, depth + 1, text.tail)
        case ')' => parsing(buffer, ops, depth - 1, text.tail)
        case '+' if depth == 0 =>
          parsing("", Plus(Nil) + (ops + parsing(buffer)), depth, text.tail)
        case '-' if depth == 0 =>
          parsing("", Minus(Nil) + (ops + parsing(buffer)), depth, text.tail)
        case c => parsing(buffer + c, ops, depth, text.tail)
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
