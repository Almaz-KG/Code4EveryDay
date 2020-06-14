package lectures.part4pm

import scala.util.Random

object PatternMatching {

  def main(args: Array[String]): Unit = {

    // switch in steroids
    val rnd = new Random().nextInt(10)

    println(rnd)
    val description = rnd match {
      case 1 => "the ONE"
      case 2 => "double or nothing"
      case 3 => "third time is the charm"
      case _ => 42 //"something else"
    }

    println(description)

    // 1. Decompose values
    case class Person(name: String, age: Int)
    val bob = Person("Bob", 20)

    val greeting = bob match {
      case Person(n, a) if a < 21 => s"Hi, my name is $n and I can't drink in the USA"
      case Person(n, a) => s"Hi, my name is $n and am $a years old"
      case _ => "I don't know who I am"
    }
    println(greeting)

    /*
      1. Cases are matched is order
      2. What if no cases match? - MatchError
      3. Type of the pattern matching expression? - Unified type of all the types in all the cases
      4. Pattern matching works really well with case classes

     */

    // Pattern matching sealed hierarchies
    sealed class Animal
    case class Dog(breed: String) extends Animal
    case class Parrot(greeting: String) extends Animal

    val animal: Animal = Dog("Terra Nova")

    animal match {
      case Dog(bread) => println(s"This is a dog with $bread breed")
    }

    // match everything
    val isEven = rnd match {
      case n if n % 2 == 0 => true
      case _ => false
    }

    val isEvenCond = if (rnd % 2 ==0) true else false
    val isEvenNormal = rnd % 2 ==0

    println(isEven)
    println(isEvenCond)
    println(isEvenNormal)


    trait Expression
    case class Number(n: Int) extends Expression
    case class Sum(el1: Expression, el2: Expression) extends Expression
    case class Prod(el1: Expression, el2: Expression) extends Expression

    /*
        Exercise
          Simple function uses pattern matching
            - takes an Expression => human readable form

           Sum(Number(2), Number(3) => 2 + 3
           Sum(Number(2), Sum(Number(3), Number(4)) => 2 + (3 + 4)
           Prod(Sum(Number(2), Number(1)), Number(3)) => (2 + 1) * 3
           Sum(Prod(Number(1), Number(2)), Number(3)) => (1 * 2) + 3
     */
    def printExpression(expression: Expression): Unit = {
      lazy val toStr: Expression => String = {
        case Number(n) => n.toString
        case Sum(x, y) => s"${toStr(x)} + ${toStr(y)}"
        case Prod(x, y) => {
          def toStrWithParentheses(e: Expression): String = e match {
            case Prod(_, _) => toStr(e)
            case Number(_) => toStr(e)
            case _ => "(" + toStr(e) + ")"
          }

          toStrWithParentheses(x) + " * " + toStrWithParentheses(y)
        }
        case expr => expr.toString
      }

      println(toStr(expression))
    }

    printExpression(Sum(Number(2), Number(3)))
    printExpression(Sum(Number(2), Sum(Number(3), Number(4))))
    printExpression(Prod(Sum(Number(2), Number(1)), Prod(Number(3), Sum(Number(4), Number(5)))))
    printExpression(Sum(Prod(Number(1), Number(2)), Number(3)))
  }
}
