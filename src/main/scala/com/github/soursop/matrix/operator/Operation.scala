package com.github.soursop.matrix.operator

object Operation {

  trait Op {
    def unwrap(): Op = this
  }
  type Copyable = {
    def copy(nodes: List[Op]): Ops
  }
  trait Reducer[T <: Op] {
    def + (op: Op): T
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
      case Minus(_) => copy(nodes :+ op)
      case some: Ops if (some.nodes.length == 1) => copy(nodes :+ some.nodes.head)
      case _ => copy(nodes :+ op)
    }
    override def unwrap(): Op = if (nodes.length == 1) nodes.head else this
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
    parsing("", Plus(Nil), 0, 0, text).unwrap
  }
  def parsing(buffer: String, ops: Ops, sign: Int, depth: Int, text: String): Op = {
    def reduce(next: Ops): Op = {
      if (depth == 0) parsing("", next, sign + 1, depth, text.tail)
      else parsing(buffer + text.head, ops, sign + 1, depth, text.tail)
    }
    if (text.isEmpty) buffer.trim match {
      case "" => ops
      case trim if (trim.endsWith("'")) =>
        ops + Transpose(if (sign > 0) parsing(trim.init) else Terminal(trim.init))
      case trim =>
        ops + (if (sign > 0) parsing(trim) else Terminal(trim))
    } else {
      text.head match {
        case '(' => parsing(buffer, ops, sign, depth + 1, text.tail)
        case ')' => parsing(buffer, ops, sign, depth - 1, text.tail)
        case '/' => reduce(Divide(Nil) + (ops + parsing(buffer)))
        case '*' => reduce(Multiply(Nil) + (ops + parsing(buffer)))
        case '+' => reduce(Plus(Nil) + (ops + parsing(buffer)))
        case '-'  =>
          if (depth == 0) ops + parsing(buffer) + parsing("", Minus(Nil), sign + 1, depth, text.tail)
          else parsing(buffer + text.head, ops, sign + 1, depth, text.tail)
        case c => parsing(buffer + c, ops, sign, depth, text.tail)
      }
    }
  }

  def main(args: Array[String]): Unit = {
    // build
    val query = "- ((2:3 * 3:2') - 4) + 2' - 2'"
//val query = "- ((2:3 * 3:2') - 4)"
    //    val query = "- 4 + 2'"
//    val query = "- 2' - 2'"
    val words = parsing(query)

    println(words)
  }

}
