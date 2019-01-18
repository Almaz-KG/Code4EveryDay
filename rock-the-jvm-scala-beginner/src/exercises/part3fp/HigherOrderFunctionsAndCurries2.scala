package exercises.part3fp

/*
  * 1. Expand MyList
  *    - foreach method A => Unit
  *       [1, 2, 3].foreach(x => println(x))
  *
  *    - sort function ((A, A) => Int) => MyList
  *       [1, 2, 3].sort((x, y) => y - x) => [3, 2, 1]
  *
  *    - zipWith(list[A], (A, A) => B) => MyList[B]
  *       [1, 2, 3].zipWith([4, 5, 6], (x, y) => x + y) => [4, 7, 9]
  *
  *    - fold(start_value: T)(A => T) => T
  *       [1, 2, 3].fold(0)(x + y) => 6
  *
  */
object HigherOrderFunctionsAndCurries2 {

  abstract class MyList[+T] {

    def isEmpty: Boolean

    def head: T

    def tail: MyList[T]

    def add[B >: T](value: B): MyList[B]

    def addAll[B >: T](values: MyList[B]): MyList[B]

    def +[B >: T](value: B): MyList[B] = this.add(value)

    def +[B >: T](values: MyList[B]): MyList[B] = this.addAll(values)

    def filter(predicate: T => Boolean): MyList[T]

    def map[B](transformer: T => B): MyList[B]

    def flatMap[B](transformer: T => MyList[B]): MyList[B]

    def foreach(holder: T => Unit)

    def sort(comparator: (T, T) => Int): MyList[T]

    def zip[B, C](anotherList: MyList[B], operator: (T, B) => C): MyList[C]

    def fold[B](startValue: B)(operator: (T, B) => B): B


    private[part3fp] def _toString: String

    override def toString: String = s"[${_toString}]"

  }

  object Empty extends MyList[Nothing] {

    override def isEmpty: Boolean = true

    override def head: Nothing = throw new NoSuchElementException("head of empty list")

    override def tail: MyList[Nothing] = throw new UnsupportedOperationException("tail of empty list")

    override def add[B >: Nothing](value: B): MyList[B] = new Cons[B](value, this)

    override def addAll[B >: Nothing](values: MyList[B]): MyList[B] = values

    override def filter(predicate: Nothing => Boolean): MyList[Nothing] = this

    override def map[B >: Nothing](transformer: Nothing => B): MyList[B] = this

    override def flatMap[B >: Nothing](transformer: Nothing => MyList[B]): MyList[B] = this

    override private[part3fp] def _toString: String = ""

    override def foreach(holder: Nothing => Unit): Unit = ()

    override def sort(comparator: (Nothing, Nothing) => Int): MyList[Nothing] = this

    override def zip[B, C](anotherList: MyList[B], operator: (Nothing, B) => C): MyList[C] =
      if (!anotherList.isEmpty) throw new RuntimeException("Lists do not have the same sizes")
      else Empty

    override def fold[B](startValue: B)(operator: (Nothing, B) => B): B = startValue
  }

  class Cons[+T](h: T, t: MyList[T]) extends MyList[T] {

    override def isEmpty: Boolean = false

    override def head: T = h

    override def tail: MyList[T] = t

    override def add[B >: T](value: B): MyList[B] = new Cons[B](value, this)

    override def addAll[B >: T](values: MyList[B]): MyList[B] = new Cons[B](head, tail.addAll(values))

    override def filter(predicate: T => Boolean): MyList[T] = {
      if (predicate(h))
        new Cons(h, tail.filter(predicate))
      else
        this.tail.filter(predicate)
    }

    override def map[B](transformer: T => B): MyList[B] = {
      new Cons[B](transformer(head), tail.map(transformer))
    }

    override def flatMap[B](transformer: T => MyList[B]): MyList[B] =
      transformer(head) + tail.flatMap(transformer)

    override private[part3fp] def _toString: String = {
      if (tail.isEmpty) h + ""
      else
        s"$h, ${tail._toString}"
    }

    override def foreach(holder: T => Unit): Unit = {
      holder(head)
      tail.foreach(holder)
    }

    override def sort(comparator: (T, T) => Int): MyList[T] = {

      def insert(value: T, sortedList: MyList[T]): MyList[T] = {
        if (sortedList.isEmpty) new Cons[T](value, Empty)
        else if (comparator(value, sortedList.head) <= 0) new Cons[T](value, sortedList)
        else new Cons[T](sortedList.head, insert(value, sortedList.tail))
      }

      val sortedTail = t.sort(comparator)
      insert(h, sortedTail)
    }

    override def zip[B, C](anotherList: MyList[B], operator: (T, B) => C): MyList[C] = {
      if (anotherList.isEmpty) throw new RuntimeException("Lists do not have the same sizes")
      else
        new Cons[C](operator(head, anotherList.head), tail.zip(anotherList.tail, operator))
    }

    override def fold[B](startValue: B)(operator: (T, B) => B): B = tail.fold(operator(head, startValue))(operator)
  }

  def toCurry(f: (Int, Int) => Int): Int => Int => Int = x => y => f(x, y)

  def fromCurry(f: Int => Int => Int): (Int,  Int) => Int = (x, y) => f(x)(y)

  def compose[A, B, C](f: A => B, g: C => A): C => B = x => f(g(x))

  def andThen[A, B, C](f: A => B, g: B => C): A => C = x => g(f(x))

  val supperAdder: Int => Int => Int = toCurry(_ + _)

  def main(args: Array[String]): Unit = {
    val list: MyList[Int] = new Cons[Int](1, new Cons[Int](2, new Cons[Int](3, Empty)))
    val anotherList: MyList[Int] = new Cons[Int](9, new Cons[Int](8, new Cons[Int](7, Empty)))

    list.foreach(print)
    println()
    val sortedList = list.sort((x, y) => y - x)
    val sortedAnotherList = anotherList.sort((x, y) => y - x)

    sortedList.foreach(x => print(x + " "))
    println()
    sortedAnotherList.foreach(x => print(x + " "))
    println()

    val zippedList = list.zip(anotherList, (x: Int, y: Int) => x + y)
    zippedList.foreach(x => print(x + " "))
    println()

    try {
      list.zip(new Cons(10, new Cons(9, Empty)), (x: Int, y: Int) => x + y)
    } catch {
      case e: RuntimeException=> println(e.getMessage)
      case t: Throwable => println("Ooooops!\nUnexpected exception"); throw new Exception(t)
    }

    println(list.fold(0)((x, y) => x + y))
    println(anotherList.fold(0)((x, y) => x + y))
    println(anotherList.fold(0)(_ + _))

    // superAdder example
    val add4 = supperAdder(4)
    println(add4(16))

    val simpleAdder = fromCurry(supperAdder)
    println(simpleAdder(4, 16))

    val add2 = (x: Int) => x + 2
    val times3 = (x: Int) => x * 3

    println(compose(add2, times3)(4))
    println(andThen(add2, times3)(4))
  }
}
