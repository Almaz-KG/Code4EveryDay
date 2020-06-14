package lectures.part4implicits

object ImplicitsIntro extends App {

  val stringPair = "Daniel" -> 555
  val intPair = 1 -> 555

  case class Person(name: String){
    def greet = s"Hi, my name is $name!"
  }

  implicit def fromStringToPerson(name: String): Person = Person(name)

  println("Peter".greet) // println(fromStringToPerson("Peter").greet)

  class A {
    def greet: Int = 2
  }

//  implicit def fromStringToA(name: String): A = new A

  // implicit parameters
  def increment(x: Int)(implicit amount: Int) = x + amount
  implicit val defaultAmount: Int = 10

  println(increment(2))
//  NOT default args
}
