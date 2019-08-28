package lectures.part1advancedscala

import lectures.part1advancedscala.AdvancedPatternMatching.MyPerson

object AdvancedPatternMatching2 extends App {

  // infix pattern
  case class Or[A, B](a: A, b: B) // Either

  val either = Or(2, "two")

  val humanDescription = either match {
    case number Or string => s"$number is written as '$string'"
  }
  println(humanDescription)

  // decomposing sequences
  val numbers = List(1, 2, 3, 4, 5)
  val varargs = numbers match {
    case List(1, _*) => "start with 1"
    case List(8, _*) => "start with 8"
  }
  println(varargs)

  abstract class MyList[+A] {
    def head: A = ???
    def tail: MyList[A] = ???
  }

  case object Empty extends MyList[Nothing]
//  case class Cons[+A]() extends MyList[A]
  case class Cons[+A](override val head: A, override val tail: MyList[A]) extends MyList[A]

  object MyList {
    def unapplySeq[A](list: MyList[A]): Option[Seq[A]] =
      if (list == Empty) Some(Seq.empty)
      else unapplySeq(list.tail).map(list.head +: _)
  }

  val myList: MyList[Int] = Cons(1, Cons(2, Cons(3, Empty)))
  val myListDescription = myList match {
    case MyList(1, 2, _*) => "My list start with 1 and 2"
    case _ => "Something else"
  }

  println(myListDescription)

  // custom return types for unapply
  // isEmpty: Boolean and get: Any

  abstract class Wrapper[T] {
    def isEmpty: Boolean
    def get: T
  }

  object PersonWrapper {
    def unapply(arg: MyPerson): Wrapper[String] = new Wrapper[String] {
      override def isEmpty: Boolean = false

      override def get: String = arg.name
    }
  }

  val myPerson = new MyPerson("Bob", 23)
  val personDescription = myPerson match {
    case PersonWrapper(name) => s"This person's name is $name"
    case _ => "This is unknown person"
  }

  println(personDescription)
}
