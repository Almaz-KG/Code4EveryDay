package exercises.part2

import exercises.part2.MyListExercise.{Cons, Empty, MyList}

import scala.reflect.ClassTag


/*
    Collection implementation MyList

    + def head(): first element of the list
    + def tail(): remainder of the list
    + def isEmpty(): Boolean
    + def add(value): new List[OLD_VALUE, new_value]
    + toString(): String (with comma-separated values)
 */

object MyListExercise extends App {

  class List[T: ClassTag]() {

    protected var currentIndex: Int = 0

    protected def this(values: Array[T], currentIndex: Int) = {
      this()
      this.values = values
      this.currentIndex = currentIndex
    }

    protected var values: Array[T] = new Array[T](0)

    def head(): T = {
      if (values.isEmpty)
        throw new NoSuchElementException("head of empty list")
      values(0)
    }

    def tail(): List[T] = {
      if (this.values.isEmpty)
        throw new UnsupportedOperationException("tail of empty list")

      new List[T](values.slice(1, values.length), currentIndex - 1)
    }

    def isEmpty: Boolean = values.length == 0

    def add(value: T): List[T] = new List[T](this.values :+ value, this.currentIndex + 1)

    def remove(value: T): List[T] = {
      if (!this.values.contains(value))
        this
      else {
        val index = this.values.indexOf(value)
        val values: Array[T] = this.values.slice(0, index) ++ this.values.slice(index + 1, currentIndex)

        new List[T](values, currentIndex - 1)
      }
    }

    override def toString: String = s"List(${values.slice(0, currentIndex).mkString(",")})"

  }

  def checkList(): Unit = {
    var list = new List[Int]()
    println(list.isEmpty)
    list = list.add(1)
    list = list.add(2)
    list = list.add(3)
    list = list.add(4)
    list = list.add(5)
    println(list.toString)
    println(list.head())
    println(list.tail().toString)
    println(list.isEmpty)

    list = list.remove(3)
    println("Element 3 was removed")
    println(list.toString)
    println(list.head())
    println(list.tail().toString)
    println(list.isEmpty)


    list = list.remove(9)
    println("Element 9 was removed")
    println(list.toString)
    println(list.head())
    println(list.tail().toString)
    println(list.isEmpty)

    list = list.remove(1)
    list = list.remove(2)
    list = list.remove(4)
    list = list.remove(5)
    println("All elements were removed")
    println(list.toString)
    // throws exception like scala.List
    //  println(list.head())
    //  println(list.tail().toString)
    println(list.isEmpty)
  }

  abstract class MyList {

    def head: Int

    def tail: MyList

    def isEmpty: Boolean

    def add(value: Int): MyList

    def _toString: String

    final override def toString: String = s"[${_toString}]"

  }

  object Empty extends MyList {
    override def head: Int = throw new NoSuchElementException("head of empty list")

    override def tail: MyList = throw new UnsupportedOperationException("tail of empty list")

    override def isEmpty: Boolean = true

    override def add(value: Int): MyList = new Cons(value, Empty)

    override def _toString: String = ""
  }

  class Cons(h: Int, t: MyList) extends MyList {
    override def head: Int = h

    override def tail: MyList = t

    override def isEmpty: Boolean = false

    override def add(value: Int): MyList = new Cons(value, this)

    override def _toString: String =
      if (tail.isEmpty) head.toString
      else s"$head, ${tail._toString}"

  }
}


object ListTest extends App {
  var list: MyList = Empty
  println(list.isEmpty)
  println(list.toString)
//  println(list.head)
//  println(list.tail.toString)

  list = list.add(1)
  list = list.add(2)
  list = list.add(3)
  list = list.add(4)
  list = list.add(5)
  println(list.toString)
  println(list.head)
  println(list.tail.toString)
  println(list.isEmpty)


  list = new Cons(1, new Cons(2, new Cons(3, Empty)))
  println(list.head)
  println(list.tail.tail.tail.isEmpty)
  println(list.add(4).head)

}


