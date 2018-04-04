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

//  trait Terminal extends Op
//  case class DoubleMatrix(height: Int, width: Int) extends Terminal
//  case class Vector(size: Int) extends Terminal


  def parsing(text: String): Op = {
    def search(buffer: String, ops: Ops, open: Int, close: Int, idx: Int): Op = {
      def byTranspose(ops: Ops, value: String): Ops = {
        def byBracket(value: String): Op = if (open > 0) parsing(value) else Terminal(value)
        value.trim match {
          case "" => ops
          case trim if (trim.endsWith("'")) =>
            ops + (byBracket(trim.init) match {
              case result: Ops if text.take(text.lastIndexOf("'")).trim.lastOption.exists(_!=')')
                => result.copy(result.nodes.init :+ Transpose(result.nodes.last))
              case result => Transpose(result)
            })
          case trim => ops + byBracket(trim)
        }
      }
      if (idx >= text.length) byTranspose(ops, buffer)
      else text(idx) match {
        case '(' => search(if (open == close) buffer else buffer + text(idx), ops, open + 1, close, idx + 1)
        case ')' => search(if (open == close + 1) buffer else buffer + text(idx), ops, open, close + 1, idx + 1)
        case '/' if open == close => search("", Divide(Nil) + byTranspose(ops, buffer), open, close, idx + 1)
        case '*' if open == close => search("", Multiply(Nil) + byTranspose(ops, buffer), open, close, idx + 1)
        case '+' if open == close => search("", Plus(Nil) + byTranspose(ops, buffer), open, close, idx + 1)
        case '-' if open == close => byTranspose(ops, buffer) + search("", Minus(Nil), open, close, idx + 1)
        case c => search(buffer + c, ops, open, close, idx + 1)
      }
    }
    search("", Plus(Nil), 0, 0, 0).unwrap
  }

  def main(args: Array[String]): Unit = {
    // build
//    val query = "- (- 4 + (2:3 * 3:2')) + 2' - 2'"
  val query = "((2:3 * 3:2)' + 2) - 2"
    //    val query = "- 4 + 2'"
//    val query = "- 2' - 2'"
    val ops = parsing(query)

    println(ops)
  }

}
