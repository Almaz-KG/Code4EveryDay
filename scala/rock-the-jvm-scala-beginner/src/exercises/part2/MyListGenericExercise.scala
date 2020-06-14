package exercises.part2

object MyListGenericExercise extends App {

  abstract class MyList[+T] {

    def isEmpty: Boolean

    def head: T

    def tail: MyList[T]

    def add[B >: T](value: B): MyList[B]

    def _toString: String

    override def toString: String = s"[${_toString}]"

  }

  object Empty extends MyList[Nothing] {

    override def isEmpty: Boolean = true

    override def head: Nothing = throw new NoSuchElementException("head of empty list")

    override def tail: MyList[Nothing] = throw new UnsupportedOperationException("tail of empty list")

    override def add[B >: Nothing](value: B): MyList[B] = new Cons(value, Empty)

    override def _toString: String = ""
  }

  class Cons[+T](h: T, t: MyList[T]) extends MyList[T] {
    override def isEmpty: Boolean = false

    override def head: T = h

    override def tail: MyList[T] = t

    override def add[B >: T](value: B): MyList[B] = new Cons(value, this)

    override def _toString: String = if (tail.isEmpty) head.toString else s"$head, ${tail._toString}"
  }

  val myListInts: MyList[Int] = new Cons(1, new Cons(2, new Cons(3, Empty)))
  val myListStrings: MyList[String] = new Cons("A", new Cons("B", new Cons("C", Empty)))
  val myList1: MyList[Int] = Empty
  val myList2: MyList[String] = Empty

  println(myListInts.toString)
  println(myListStrings.toString)
  println(myList1)
  println(myList2)


}
