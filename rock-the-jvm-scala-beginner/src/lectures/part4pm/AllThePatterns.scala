package lectures.part4pm

import exercises.part2.AnonymousClassesExercise1.{Cons, Empty, MyList}

object AllThePatterns {

  def main(args: Array[String]): Unit = {

      // 1 - constants - Switch on steroids
    val x: Any = "Scala"
    val constants = x match {
      case 1 => "a number"
      case "Scala" => "The scala"
      case true => "The truth"
      case AllThePatterns => "All the patterns"
    }

    println(constants)

    // 2 - match anything
    // 2.1 - wildcards
    val matchAnything = x match {
      case _ =>
    }

    // 2.2 variable
    val matchVariable = x match {
      case something => s"I've found $something"
    }

    // 3 - tuples
    val aTuple = (1, 2)
    val matchAtuple = aTuple match {
      case (1, 1) => "Is ONEs"
      case (_, 2) => s"This is tuple with second value eq 2"
      case (something, 3) => s"I've found tuple with value eq 3 $something"
      case (3, something) => s"I've found tuple with value eq 3 $something"
    }

    val nestedTuple = (1, (2, 3))
    val matchNestedTuple = nestedTuple match {
      case (_, (2, v)) => s"Yeah, 2 here with $v"
      case _ => nestedTuple
    }

    // 4 - case classes (constructor pattern)
    // pattern matching be nested with case classes as well
    val aList: MyList[Int] = Cons(1, Cons(2, Empty))
    val matchMyList = aList match {
      case Empty => "Empty"
      case Cons(head, Cons(subHead, subTail)) => s"$head - $subTail"
    }


    // 5 - List patterns
    val aStandardList = List(1, 2, 3, 42)
    val matchStandardList = aStandardList match {
      case List(1, _, _) => // extractor
      case List(1, _*) => // var args
      case 1 :: List(_) => // infix pattern
      case List(1, 2, 3) :+ 42 => // infix pattern
    }

    // 6 - type specifiers
    val unknown: Any = 2
    val unknownMatch = unknown match {
      case list: List[Int] => // List of Int only
      case _ =>
    }

    // 7 - name binding
    val nameBindingMatch = aList match {
      case noneEmptyList @ Cons(_, _) => // name binding => use the name later
      case Cons(1, rest @ Cons(2, _)) => // name binding inside nested patterns
    }

    // 8 - multi-patterns
    val multiPattern = aList match {
      case Empty | Cons(1, _) => // compound pattern (multi-pattern)
    }

    // 9 - if guards
    val secondElementSpecial = aList match {
      case Cons(_, Cons(elem, _)) if elem % 2 == 0 => // Only even second element here
    }


    // Questions
    val numbers = List(1, 2, 3)
    val numbersMatch = numbers match {
      case listOfStrings: List[String] => "a list of strings"
      case listOfNumbers: List[Int] => "a list of numbers"
      case _ => ""
    }

    // JVM trick question
    println(numbersMatch)
  }
}
