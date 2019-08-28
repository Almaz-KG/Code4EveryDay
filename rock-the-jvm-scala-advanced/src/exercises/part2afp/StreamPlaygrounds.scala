package exercises.part2afp

import scala.annotation.tailrec

abstract class MyStream[+A] {
  def isEmpty: Boolean
  def head: A
  def tail: MyStream[A]

  def #::[B >: A](element: B): MyStream[B] // prepender operator
  def ++[B >: A](anotherStream: => MyStream[B]): MyStream[B] // concatenate two streams

  def foreach(f: A => Unit): Unit
  def map[B](f: A => B): MyStream[B]
  def flatMap[B](f: A=> MyStream[B]): MyStream[B]
  def filter(predicate: A => Boolean): MyStream[A]

  def take(n: Int): MyStream[A]
  def takeAsList(n: Int): List[A]

  @tailrec
  final def toList[B >: A](accumulator: List[B] = Nil): List[B] =
    if (isEmpty) accumulator
    else tail.toList(accumulator :+ head)

}

object EmptyStream extends MyStream[Nothing] {
  override def isEmpty: Boolean = true

  override def head: Nothing = throw new NoSuchElementException

  override def tail: MyStream[Nothing] = throw new NoSuchElementException

  override def #::[B >: Nothing](element: B): MyStream[B] = new NoneEmptyStream(element, this)

  override def ++[B >: Nothing](anotherStream: => MyStream[B]): MyStream[B] = anotherStream

  override def foreach(f: Nothing => Unit): Unit = ()

  override def map[B](f: Nothing => B): MyStream[B] = this

  override def flatMap[B](f: Nothing => MyStream[B]): MyStream[B] = this

  override def filter(predicate: Nothing => Boolean): MyStream[Nothing] = this

  override def take(n: Int): MyStream[Nothing] = this

  override def takeAsList(n: Int): List[Nothing] = Nil
}

class NoneEmptyStream[+A](_head: A, _tail: => MyStream[A]) extends MyStream[A] {
  override def isEmpty: Boolean = false

  override val head: A = _head
  override lazy val tail: MyStream[A] = _tail // call by need

  override def #::[B >: A](element: B): MyStream[B] = new NoneEmptyStream[B](element, this)

  override def ++[B >: A](anotherStream: => MyStream[B]): MyStream[B] = new NoneEmptyStream[B](head, tail ++ anotherStream)

  override def foreach(f: A => Unit): Unit = {
    f(head)
    tail.foreach(f)
  }

  override def map[B](f: A => B): MyStream[B] = new NoneEmptyStream[B](f(head), tail.map(f))
  override def flatMap[B](f: A => MyStream[B]): MyStream[B] = f(head) ++ tail.flatMap(f)
  override def filter(predicate: A => Boolean): MyStream[A] =
    if (predicate(head)) new NoneEmptyStream(head, tail.filter(predicate))
    else tail.filter(predicate)

  override def take(n: Int): MyStream[A] =
    if (n <= 0) EmptyStream
    else if (n == 1) new NoneEmptyStream(head, EmptyStream)
    else new NoneEmptyStream(head, tail.take(n - 1))

  override def takeAsList(n: Int): List[A] = take(n).toList()
}

object MyStream {
  def from[A](start: A)(generator: A => A): MyStream[A] = new NoneEmptyStream[A](start, MyStream.from(generator(start))(generator))
}

object StreamPlaygrounds extends App {
  val myStreamInt = MyStream.from[Int](1)(n => n + 1)

  println(myStreamInt.head)
  println(myStreamInt.tail.head)
  println(myStreamInt.tail.tail.head)

  val startWith0 = 0 #:: myStreamInt
  println("===========")
  println(startWith0.head)
  println(startWith0.tail.head)
  println(startWith0.tail.tail.head)

  println("===========")
  println(startWith0.takeAsList(10))

  println("===========")
  println(startWith0.map(_ * 2).takeAsList(10))

  println("===========")
  println(startWith0.flatMap(x => new NoneEmptyStream[Int](x, new NoneEmptyStream[Int](x + 1, EmptyStream))).takeAsList(10))

  println("===========")
  println(startWith0.filter(_ < 20).take(2).take(20).toList())

  // Exercises on streams
  // 1. Stream of fibonacci numbers
  // 2. Stream of prime numbers with Eratosthemes's sieve
  def fromTupled[A](first: A, second: A)(generator: (A, A) => A): MyStream[A] =
    new NoneEmptyStream[A](first, fromTupled(second, generator(first, second))(generator))

  val fibonacciStream = fromTupled[BigInt](1, 1)((x, y) => x + y)
  println(fibonacciStream.take(20).toList())

  def eratosthemesSieve(numbers: MyStream[Int]): MyStream[Int] =
    if (numbers.isEmpty) EmptyStream
    else new NoneEmptyStream(numbers.head, eratosthemesSieve(numbers.tail.filter(_ % numbers.head != 0)))

  val primeNumbers = eratosthemesSieve(MyStream.from(2)(_ + 1))
  println(primeNumbers.take(20).toList())
}
