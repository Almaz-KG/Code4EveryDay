package lectures.part4implicits

object PimpMyLibrary extends App {
  /**
    * Enrich the string class
    *   - asInt
    *   - encrypt string by caesar cipher
    *     - John -> Lnjp
    * Keep enriching the Int class
    *   - times (function)
    *     3.times(() => my_function)
    *   - *
    *     3 * List(1, 2) => List(1, 2, 1, 2, 1, 2)
    */
  implicit class RichInt(val value: Int) extends AnyVal{
    def isEvent: Boolean = value % 2 == 0
    def sqrt: Double = Math.sqrt(value)

    def times(f: () => Unit): Unit = {
      (1 to value).foreach(_ => f())
    }

    def *[A] (list: List[A]): List[A] = {

      def append(source: List[A], result: List[A], count: Int): List[A] = {
        if (count <= 0) result
        else append(source, result ++ source, count - 1)
      }

      append(list, List(), value)
    }

  }

  implicit class RichString(val value: String) {
    def asInt: Integer = Integer.valueOf(value) // java.lang.Integer => Int

    def encrypt(cypherDistance: Int): String = value.map(c => {
      if (c.isUpper) ((c.toInt + cypherDistance - 65) % 26 + 65).toChar
      else ((c.toInt + cypherDistance - 97) % 26 + 97).toChar
    })
  }

  println(new RichInt(12).isEvent)
  println(new RichInt(12).sqrt)

  // type enrichment = pimping
  println(12.isEvent) // new RichInt(12).isEvent
  println(12.sqrt)

  import scala.concurrent.duration._
  println(3.seconds)

  println("1000".asInt + 1019)
  println("Almaz".encrypt(1))

  3.times(() => println("Hello world!"))
  println(3  * List(1, 2, 3))
}
