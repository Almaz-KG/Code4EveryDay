package lectures.part3fp

object WhatsAFunction extends App {

  trait MyFunction[A, B] {
    def apply(element: A): B
  }

  val doubler = new MyFunction[Int, Int] {
    override def apply(element: Int): Int = element * 2
  }

  val stringIntConverter = new MyFunction[String, Int] {
    override def apply(token: String): Int = token.toInt
  }

  val adder: (Int, Int) => Int = (a, b) => a + b

  println(doubler(2))
  println(stringIntConverter("315"))
  println(adder(4, 3))
}
