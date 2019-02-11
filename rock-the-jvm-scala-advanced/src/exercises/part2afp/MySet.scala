package exercises.part2afp

import scala.annotation.tailrec

/*
  Implement a functional set
 */
trait MySet[A] extends (A => Boolean) {

  def contains(elem: A): Boolean

  def +(elem: A): MySet[A]

  def ++(anotherSet: MySet[A]): MySet[A]

  def map[B](f: A => B): MySet[B]

  def flatMap[B](f: A => MySet[B]): MySet[B]

  def filter(f: A => Boolean): MySet[A]

  def foreach(f: A => Unit): Unit

  override def apply(elem: A): Boolean = contains(elem)
}

class EmptyMySet[A] extends MySet[A] {
  override def contains(elem: A): Boolean = false

  override def +(elem: A): MySet[A] = new NoneEmptySet[A](elem, this)

  override def ++(anotherSet: MySet[A]): MySet[A] = anotherSet

  override def map[B](f: A => B): MySet[B] = new EmptyMySet[B]

  override def flatMap[B](f: A => MySet[B]): MySet[B] = new EmptyMySet[B]

  override def filter(f: A => Boolean): MySet[A] = this

  override def foreach(f: A => Unit): Unit = ()
}

class NoneEmptySet[A](head: A, tail: MySet[A]) extends MySet[A] {
  override def contains(elem: A): Boolean = head == elem || tail.contains(elem)

  override def +(elem: A): MySet[A] =
    if (this.contains(elem)) this
    else new NoneEmptySet[A](elem, this)

  override def ++(anotherSet: MySet[A]): MySet[A] = tail ++ anotherSet + head

  override def map[B](f: A => B): MySet[B] = tail.map(f) + f(head)

  override def flatMap[B](f: A => MySet[B]): MySet[B] = tail.flatMap(f) ++ f(head)

  override def filter(f: A => Boolean): MySet[A] = {
    if (f(head)) new NoneEmptySet[A](head, tail.filter(f))
    else tail.filter(f)
  }

  override def foreach(f: A => Unit): Unit = {
    f(head)
    tail.foreach(f)
  }
}

object MySet {
  def apply[A](values: A*): MySet[A] = {

    @tailrec
    def buildSet(valSeq: Seq[A], acc: MySet[A]): MySet[A] = {
      if (valSeq.isEmpty)
        acc
      else
        buildSet(valSeq.tail, acc + valSeq.head)
    }

    buildSet(values, new EmptyMySet[A])
  }
}

object MySetPlayground extends App {
  val s = MySet(1, 2, 3, 4, 5)
//  s foreach print
  println(s.contains(0))

  s + 5 ++ MySet(2, 0) map(x => x * 3) filter (x => x % 2 != 0) foreach println
}