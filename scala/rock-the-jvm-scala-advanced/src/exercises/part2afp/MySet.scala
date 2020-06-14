package exercises.part2afp

import scala.annotation.tailrec

/*
  Implement a functional set
 */
trait MySet[A] extends (A => Boolean) {

  def contains(elem: A): Boolean

  def +(elem: A): MySet[A]

  def -(elem: A): MySet[A] = remove(elem)

  def &(set: MySet[A]): MySet[A] = intersection(set)

  def --(set: MySet[A]): MySet[A] = difference(set)

  def ++(anotherSet: MySet[A]): MySet[A]

  def map[B](f: A => B): MySet[B]

  def flatMap[B](f: A => MySet[B]): MySet[B]

  def filter(f: A => Boolean): MySet[A]

  def foreach(f: A => Unit): Unit

  def remove(elem: A): MySet[A]

  def intersection(set: MySet[A]): MySet[A]

  def difference(set: MySet[A]): MySet[A]

  // new operator
  def unary_! : MySet[A]

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

  override def remove(elem: A): MySet[A] = this

  override def difference(set: MySet[A]): MySet[A] = this

  override def intersection(set: MySet[A]): MySet[A] = this

  override def unary_! : MySet[A] = new PropertyBasedSet[A](_ => true)
}

class PropertyBasedSet[A](property: A => Boolean) extends MySet[A] {
  override def contains(elem: A): Boolean = property(elem)

  override def +(elem: A): MySet[A] = new PropertyBasedSet[A](x => property(x) || x == elem)

  override def ++(anotherSet: MySet[A]): MySet[A] = new PropertyBasedSet[A](x => property(x) || anotherSet(x))

  override def map[B](f: A => B): MySet[B] = politelyFail

  override def flatMap[B](f: A => MySet[B]): MySet[B] = politelyFail

  override def foreach(f: A => Unit): Unit = politelyFail

  override def filter(f: A => Boolean): MySet[A] = new PropertyBasedSet[A](x => property(x) && f(x))

  override def remove(elem: A): MySet[A] = filter(x => x != elem)

  override def intersection(set: MySet[A]): MySet[A] = filter(set)

  override def difference(set: MySet[A]): MySet[A] = filter(!set)

  override def unary_! : MySet[A] = new PropertyBasedSet[A](x => !property(x))

  def politelyFail = throw new IllegalArgumentException("Ooooops!")
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

  override def remove(elem: A): MySet[A] = {
    if (head == elem) tail
    else tail.remove(elem) + head
  }

  override def intersection(set: MySet[A]): MySet[A] = filter(set)

  override def difference(set: MySet[A]): MySet[A] = filter(!set)

  override def unary_! : MySet[A] = new PropertyBasedSet[A](x => !this(x))
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
  val s = MySet(1, 2, 3, 4)
//  s foreach print
  println(s.contains(0))

  s + 5 ++ MySet(2, 0) map(x => x * 3) filter (x => x % 2 != 0) foreach println

  val negative = !s
  println(negative(2))
  println(negative(5))

  val negativeEven = negative.filter(_ % 2 == 0)
  println(negativeEven(5))

  val negativeEven5 = negativeEven + 5
  println(negativeEven5(5))
}