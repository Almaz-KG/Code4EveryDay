package exercises.part3fp

object WhatsAFunctionExercise1Part2 extends App {

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
  }

  val list: MyList[Int] = new Cons[Int](1, new Cons[Int](2, new Cons[Int](3, new Cons[Int](4, Empty))))
  val anotherList: MyList[Int] = new Cons[Int](5, new Cons[Int](6, Empty))

  println(list)
  println(list + anotherList)

  println("====================")
  println("Filter")
  println(list.filter((value: Int) => value % 2 == 0))
  println(list.filter((value: Int) => value % 2 != 0))
  println(list.filter((x: Int) => (x & (x - 1)) == 0)) // power of two

  println("====================")
  println("Map")
  println(list.map((value: Int) => value + 1))
  println(list.map((value: Int) => value * 2))
  println(list.map((value: Int) => s"Str($value)"))

  println("====================")
  println("flatMap")

  println(list.flatMap((value: Int) => new Cons(value, new Cons(value + 1, Empty))))
  println(list.flatMap((value: Int) => new Cons[Int](value, new Cons[Int](value + 1, Empty))))

}
