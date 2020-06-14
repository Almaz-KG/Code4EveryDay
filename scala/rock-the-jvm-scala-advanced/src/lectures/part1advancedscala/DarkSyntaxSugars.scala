package lectures.part1advancedscala

import scala.util.Try

object DarkSyntaxSugars extends App {

  // syntax sugar #1: method with single param

  def singleArgMethod(arg: Int): String = s"An arguments is $arg"

  val desc1 = singleArgMethod(1)
  val desc2 = singleArgMethod {
    // some code here
    23
  }

  val aTryInstance = Try { // like java's try { ... }
    throw new RuntimeException
  }

  List(1, 2, 3).map { x =>
    x + 1
  }


  // syntax sugar #2: single abstract method
  trait Action {
    def act(x: Int): Int
  }

  val anInstance1: Action = new Action {
    override def act(x: Int): Int = x + 1
  }

  val anInstance: Action = (x: Int) => x + 1

  // example: Runnables
  val aThread1 = new Thread(new Runnable {
    override def run(): Unit = println("Hello, Scala")
  })

  val aThread2 = new Thread(() => println("Hello, Scala"))

  abstract class AnAbstractType {
    def implemented: Int = 23

    def f(a: Int): Unit
  }

  val anAbstractInstance: AnAbstractType = (a: Int) => println(a + 1)

  // syntax sugar #3 the :: and #:: methods are special

  val prependedList = 2 :: List(3, 4)
  // 2.::(List(3, 4)) =>
  //  List(3, 4).::(2)
  //    ?!

  // Scala spec: Last char decides associativity of method
  println(prependedList)

  val anotherPrependedList = 1 :: 2 :: 3 :: List(4, 5)
  List(4, 5).::(3).::(2).::(1)  // equivalent
  println(anotherPrependedList)

  class MyStream[T] {
    def -->(value: T): MyStream[T] = this // actual implementation here
    def -->:(value: T): MyStream[T] = this // actual implementation here
  }

  val myStream1 = 1 -->: 2 -->: 3 -->: new MyStream[Int]
  val myStream2 = new MyStream[Int] --> 1 --> 2 --> 3

  // syntax sugar #4: multi-word method naming
  class TeenGirl(name: String) {
    def `and then said`(gossip: String) = println(s"$name said $gossip")
  }

  val teenGirl = new TeenGirl("Sara")
  teenGirl `and then said` "Anna loves John"

  // syntax sugar #5: infix types
  class Composite[A, B]

  val composite: Int Composite String = ???

  class -->[A, B]
  val towards: Int --> String = ???

  // syntax sugar #6: update() method is very special, much like apply()
  val anArray = Array(1, 2, 3)
  anArray(2) = 7 // rewrite to anArray.update(2, 7)
  // used in mutable collections
  // remember apply() and update()

  // syntax sugar #7: setters for mutable containers
  class Mutable {
    private var internalMember: Int = 0

    def member = internalMember
    def member_=(value: Int): Unit = internalMember = value
  }

  val mutableContainer = new Mutable
  mutableContainer.member = 3
  mutableContainer.member
}
