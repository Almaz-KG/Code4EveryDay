package lectures.part1basics

object Expressions extends App {

  // EXPRESSIONS
  val x = 1 + 2
  println(x)

  println(2 + 3 * 4)
  // Math operations on Scala
  // + - * / & | ^ << >> >>>(right shift with zero extension)

  println(1 == x)
  // ==, !=, >, <, >=, <=

  println(!(1 == x))
  // !, &&, ||

  var aVariable = 2
  aVariable += 3
  println(aVariable)
  // +=, -=, *=, /=

  // All this operators has a side effect


  // INSTRUCTIONS (DO) VS EXPRESSIONS (VALUE)

  // IF expression
  val aCondition = true
  var aConditionedValue = if (aCondition) 15 else -3 // IF EXPRESSION - NOT IF INSTRUCTION
  println(aConditionedValue)

  println("PLEASE, NEVER WRITE CODE LIKE IT AGAIN, IT IS NOT ABOUT SCALA")
  println("##########")
  var i = 0
  while (i < 10){
    println(i)
    i += 1
  }

  println("##########")


  // EVERYTHING in Scala is expression
  val aWeirdValue = (aVariable = 3) // Scala Unit === Java void
  println(aWeirdValue)



  // Code block
  val aCodeBlock: String = {
    var z = 1
    var b = "Hello"

    if (z < 2) b else "Goodbye!"
  }

  println(aCodeBlock)

}







