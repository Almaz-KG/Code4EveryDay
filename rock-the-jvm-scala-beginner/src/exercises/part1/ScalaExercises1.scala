package exercises.part1

object ScalaExercises1 {


  // 1. Difference between "Hello World" and println("Hello World")
  // Answer: Difference a lot of things
  //  First: The types of variables: "Hello World" is String type, and println("Hello world") is Unit
  // Second: "Hello world" is expression, and println("Hello world") is instruction (has side effects)


  // 2. What type of this expression
  // val someValue = { 2 < 3 }
  // Answer: Because last expression of block is (2 < 3), and result of this expression has boolean type,
  //         then the type of value someValue will be a Boolean

  // What type of this expression
  // val someOtherValue = {§
  //    if (someValue) 239 else 986
  //    42
  // }
  // Answer: The type if someOtherValue value is Int because last expression is '42'
}
