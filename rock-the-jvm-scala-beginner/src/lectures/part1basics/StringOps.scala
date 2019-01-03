package lectures.part1basics

object StringOps extends App {

  val str: String = "Hello, I am learning Scala"

  println(str)
  println(str.charAt(2))
  println(str.concat("IS SCALA BETTER THAN JAVA?"))
  println(str.substring(7, 11))
  println(str.split(" ").toList)
  println(str.startsWith("Hello"))
  println(str.replace(' ', '-'))
  println(str.toLowerCase)
  println(str.toUpperCase)
  println(str.length)

  val strNumber = "42"
  val number = strNumber.toInt

  println(number)
  println('a' +: strNumber :+ 'z')
  println(str.reverse)
  println(str.take(2))


  // Scala specific string features

  // S-interpolation
  val name = "David"
  val age = 26
  val text1 = s"Hello, my name is $name and I am $age years old"
  val text2 = s"Hello, my name is $name and I will be turning ${1 + age} years old"

  println(text1)
  println(text2)

  // F - interpolation
  val speed = 1.2248f
  val text3 = f"$name%s can eat $speed%2.3f burgers per minute"

  println(text3)

  // raw - interpolation
  println(raw"This is a \n newline")

  val escaped = "This is a \n newline"
  println(raw"$escaped")
}
