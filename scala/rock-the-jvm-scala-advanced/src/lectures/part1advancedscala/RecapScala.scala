package lectures.part1advancedscala

import scala.annotation.tailrec
import scala.util.Failure

object RecapScala extends App {

  val aCondition: Boolean = false
  val aConditionedVal = if (aCondition) 42 else 65
  // instruction vs expressions


  // compiler infer types for us
  val aCodeBlock = {
    if (aCondition) 54
    0 // The actual value
  }

  // Unit = Void (for side effect meaning purpose)
  val theUnit = println("Hello, Scala")

  // functions
  def aFunction(x: Int): Int = x + 1

  // recursion: stack and tail
  @tailrec
  def factorial(n: Int, accumulator: Int): Int = if (n <= 0) accumulator else  factorial(n - 1, accumulator * n)

  // Object orientation
  class Animal
  class Dog extends Animal

  val aDog: Animal = new Dog // subtyping polymorphism

  trait Carnivore {
    def eat(a: Animal): Unit
  }

  class Crocodile extends Animal with Carnivore {
    override def eat(a: Animal): Unit = println(s"Crunch $a!")
  }

  // method notations
  val aCroc = new Crocodile
  aCroc.eat(aDog)
  aCroc eat aDog

  // 1 + 2
  // 1.+(2)

  // anonymous classes
  val aCarnivore = new Carnivore {
    override def eat(a: Animal): Unit = println("Anonymous implementation")
  }

  // generics
  abstract class MyList[+T] // variance and variance problems in this course

  // singletons and companions
  object MyList

  // case classes
  case class Person(name: String, age: Int)

  // exceptions and try-catch-final expression
  //  val throwsException = throw new RuntimeException
  //  println(throwsException)

  val aPotentialFailure = try {
    //throw new StackOverflowError()
    throw new RuntimeException
  } catch {
    case e: RuntimeException => "Runtime exception"
    case _ => "Oooops!"
  } finally {
    println("This is finally message")
  }

  println(aPotentialFailure)

  // packaging and imports

  // functional programming
  val incrementer = new Function1[Int, Int] {
    override def apply(v1: Int): Int = v1 + 1
  }
  println(incrementer(1))

  val anonymousIncrementer: Int => Int = _ + 1
  println(anonymousIncrementer(42))

  List(1, 2, 3).map(anonymousIncrementer).foreach(println)

  // map, flatMap, filter

  // for-comprehensions
  val pair = for {
    num <- List(1, 2, 3, 4, 5, 6, 7, 8) if num % 2 == 0
    char <- List('a', 'b', 'c')
  } yield num + "-" + char

  println(pair)

  // Scala collections: Seq, Array, Lists, Vectors, Maps, Tuples
  val aMap = Map("A" -> 1, "B" -> 2, "C" -> 3)
  println(aMap)

  // "collections": Options, Try
  val aOption = Some(2)
  val aTry = Failure(new RuntimeException)

  // pattern matching - switch on steroids
  val x = 2
  val patternMatchingValue = x match {
    case 1 => "One"
    case 2 => "Two"
    case _ => "Some value"
  }

  val bob = Person("Bob", 22)
  val greeting = bob match {
    case Person(n, _) if n.startsWith("B") => s"Hi guys! I am $n"
    case Person(_, a) if a > 23 => "I am so old"
    case _ => "Hello"
  }
  println(greeting)
}
