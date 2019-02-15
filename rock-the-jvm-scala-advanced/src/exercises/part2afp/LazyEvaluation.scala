package exercises.part2afp

object LazyEvaluation extends App {

  abstract class MyStream[+A] {
    def isEmpty: Boolean
    def head: A
    def tail: MyStream[A]

    def #::[B >: A](element: B): MyStream[B] // prepender operator
    def ++[B >: A](anotherStream: MyStream[B]): MyStream[B] // concatenate two streams

    def foreach(f: A => Unit): Unit
    def map[B](f: A => B): MyStream[B]
    def flatMap[B](f: A=> MyStream[B]): MyStream[B]
    def filter(predicate: A => Boolean): MyStream[A]

    def take(n: Int): MyStream[A]
    def takeAsList(n: Int): List[A]
  }

  object MyStream {
    def from[A](start: A)(generator: A => A): MyStream[A] = ???
  }

  class MyFiniteStream[A](_head: A, _tail: MyFiniteStream[A]) extends MyStream[A] {
    override def isEmpty: Boolean = false

    override def head: A = _head

    override def tail: MyFiniteStream[A] = _tail

    override def #::[B >: A](element: B): MyStream[B] = ???

    override def ++[B >: A](anotherStream: MyStream[B]): MyStream[B] = ???

    override def foreach(f: A => Unit): Unit = {
      f(_head)
      tail.foreach(f)
    }

    override def map[B](f: A => B): MyStream[B] = {
      new MyFiniteStream[B](f(head), tail.map(f).asInstanceOf[MyFiniteStream[B]])

    }

    override def flatMap[B](f: A => MyStream[B]): MyStream[B] = ???

    override def filter(predicate: A => Boolean): MyStream[A] = {
      val tailRes = tail.filter(predicate).asInstanceOf[MyFiniteStream[A]]

      if (predicate(head)) new MyFiniteStream[A](head, tailRes)
      else
        tailRes
    }

    override def take(n: Int): MyStream[A] = ???

    override def takeAsList(n: Int): List[A] = ???
  }

  class MyInfiniteStream[A](start: A, generator: A => A) extends MyStream[A] {
    lazy val nextValue = generator(start)

    override def isEmpty: Boolean = false

    override def head: A = start

    override def tail: MyStream[A] = new MyInfiniteStream[A](nextValue, generator)

    override def #::[B >: A](element: B): MyStream[B] = ???

    override def ++[B >: A](anotherStream: MyStream[B]): MyStream[B] = ???

    override def foreach(f: A => Unit): Unit = ???

    override def map[B](f: A => B): MyStream[B] = ???

    override def flatMap[B](f: A => MyStream[B]): MyStream[B] = ???

    override def filter(predicate: A => Boolean): MyStream[A] = ???

    override def take(n: Int): MyStream[A] = ???

    override def takeAsList(n: Int): List[A] = ???
  }


  /*
      naturals = MyStream.from(1)(x => x + 1) = stream of natural numbers (potentially infinite!

      naturals.take(10) // lazily evaluated stream of the first 10 naturals (finite stream)
      naturals.foreach(println) // will crash JVM
      natural.map(_ * 2) // stream of all even numbers (potentially infinite)
   */
}
