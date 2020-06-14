package exercises.part2

/*
  Expand MyList to use case classes and case objects
*/
object CaseClassExercise1 extends App {

  trait Transformer[-T, +B] {
    def transform(value: T): B
  }

  trait Predicate[T] {
    def test(value: T): Boolean
  }

  abstract class MyList[+T] {

    def isEmpty: Boolean

    def head: T

    def tail: MyList[T]

    def +[B >: T](value: B): MyList[B] = add(value)

    def +[B >: T](values: MyList[B]): MyList[B] = addAll(values)

    def add[B >: T](value: B): MyList[B]

    def addAll[B >: T](values: MyList[B]): MyList[B]

    def filter[B >: T](predicate: Predicate[B]): MyList[B]

    def map[B](transformer: Transformer[T, B]): MyList[B]

    def flatMap[B](transformer: Transformer[T, MyList[B]]): MyList[B]

    protected[part2] def _toString: String

    override def toString: String = s"[${_toString}]"

  }

  case object Empty extends MyList[Nothing] {

    override def isEmpty: Boolean = true

    override def head: Nothing = throw new NoSuchElementException("head of empty list")

    override def tail: MyList[Nothing] = throw new UnsupportedOperationException("tail of empty list")

    override def add[B >: Nothing](value: B): MyList[B] = new Cons[B](value, Empty)

    override def addAll[B >: Nothing](values: MyList[B]): MyList[B] = values

    override def filter[B >: Nothing](predicate: Predicate[B]): MyList[B] = Empty

    override def map[B](transformer: Transformer[Nothing, B]): MyList[B] = Empty

    override def flatMap[B](transformer: Transformer[Nothing, MyList[B]]): MyList[B] = Empty

    override protected[part2] def _toString: String = ""
  }

  case class Cons[T](h: T, t: MyList[T]) extends MyList[T]{

    override def isEmpty: Boolean = false

    override def head: T = h

    override def tail: MyList[T] = t

    override def add[B >: T](value: B): MyList[B] = new Cons[B](value, this)

    override def addAll[B >: T](values: MyList[B]): MyList[B] = new Cons[B](h, tail.addAll(values))

    override def filter[B >: T](predicate: Predicate[B]): MyList[B] = {
      if (predicate.test(head))
        new Cons[B](head, tail.filter(predicate))
      else
        tail.filter(predicate)
    }

    override def map[B](transformer: Transformer[T, B]): MyList[B] = {
      new Cons[B](transformer.transform(head), tail.map(transformer))
    }

    override def flatMap[B](transformer: Transformer[T, MyList[B]]): MyList[B] = {
      transformer.transform(head) + tail.flatMap(transformer)
    }

    override protected[part2] def _toString: String = {
      if (tail.isEmpty) head.toString
      else
        s"$head, ${tail._toString}"
    }
  }

  val listOfIntegers: MyList[Int] = new Cons[Int](1, new Cons[Int](2, new Cons[Int](3, Empty)))
  val listOfIntegers2: MyList[Int] = new Cons[Int](1, new Cons[Int](2, new Cons[Int](3, Empty)))

  println(listOfIntegers)

  println(listOfIntegers.filter((value: Int) => value % 2 == 0))
  println(listOfIntegers.filter((value: Int) => value % 2 != 0))

  println(listOfIntegers.map((value: Int) => value + 1))
  println(listOfIntegers.map((value: Int) => value * 2))

  println(listOfIntegers.flatMap((value: Int) => new Cons[Int](value, new Cons[Int](value + 1, Empty))))

  println(listOfIntegers == listOfIntegers2)
}
