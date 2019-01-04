package exercises.part2

/*
  1. Generic trait MyPredicate[T] with method test(T): Boolean
  2. Generic trait MyTransformer[A,B] with transform(A): B
  3. MyList
      + def map(myTransformer): MyList
      + def filter(myPredicate): MyList
      + def flatMap(myTransformer from A to MyList[B]): MyList[B]
*/
object AnonymousClassesExercise1 extends App {

  trait MyPredicate[T] {
    private[part2] def test(value: T): Boolean
  }

  trait MyTransformer[-A, +B] {
    def transform(value: A): B
  }

  abstract class MyList[+T] {

    def isEmpty: Boolean

    def head: T

    def tail: MyList[T]

    def add[B >: T](value: B): MyList[B]

    def addAll[B >: T](values: MyList[B]): MyList[B]

    def +[B >: T](value: B): MyList[B] = this.add(value)

    def +[B >: T](values: MyList[B]): MyList[B] = this.addAll(values)

    def filter[B >: T](predicate: MyPredicate[B]): MyList[B]

    def map[B](transformer: MyTransformer[T, B]): MyList[B]

    def flatMap[B](transformer: MyTransformer[T, MyList[B]]): MyList[B]

    private[part2] def _toString: String

    override def toString: String = s"[${_toString}]"

  }

  object Empty extends MyList[Nothing] {

    override def isEmpty: Boolean = true

    override def head: Nothing = throw new NoSuchElementException("head of empty list")

    override def tail: MyList[Nothing] = throw new UnsupportedOperationException("tail of empty list")

    override def add[B >: Nothing](value: B): MyList[B] = new Cons[B](value, this)

    override def addAll[B >: Nothing](values: MyList[B]): MyList[B] = values

    override def filter[T](predicate: MyPredicate[T]): MyList[T] = this

    override def map[B >: Nothing](transformer: MyTransformer[Nothing, B]): MyList[B] = this

    override def flatMap[B >: Nothing](transformer: MyTransformer[Nothing, MyList[B]]): MyList[B] = this

    override private[part2] def _toString: String = ""
  }

  class Cons[+T](h: T, t: MyList[T]) extends MyList[T] {

    override def isEmpty: Boolean = false

    override def head: T = h

    override def tail: MyList[T] = t

    override def add[B >: T](value: B): MyList[B] = new Cons[B](value, this)

    override def addAll[B >: T](values: MyList[B]): MyList[B] = new Cons[B](head, tail.addAll(values))

    override def filter[B >: T](predicate: MyPredicate[B]): MyList[B] = {
      if (predicate.test(h))
        new Cons(h, tail.filter(predicate))
      else
        this.tail.filter(predicate)
    }

    override def map[B](transformer: MyTransformer[T, B]): MyList[B] = {
      new Cons[B](transformer.transform(head), tail.map(transformer))
    }

    override def flatMap[B](transformer: MyTransformer[T, MyList[B]]): MyList[B] =
      transformer.transform(head) + tail.flatMap(transformer)

    override private[part2] def _toString: String = {
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

  //  println(list.flatMap((value: Int) => new Cons(value, new Cons(value + 1, Empty))))

  println(list.flatMap((value: Int) => new Cons[Int](value, new Cons[Int](value + 1, Empty))))

}
