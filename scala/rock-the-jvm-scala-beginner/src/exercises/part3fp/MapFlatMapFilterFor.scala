package exercises.part3fp

object MapFlatMapFilterFor {

  abstract class MayBe[+T] {
    def value: T

    def map[B](function: T => B): MayBe[B]

    def flatMap[B](function: T => MayBe[B]): MayBe[B]

    def filter(predicate: T => Boolean): MayBe[T]
  }

  case object MayBe {
    def apply[T](value: T): MayBe[T] = {
      if (value == null) None
      else new Some[T](value)
    }
  }

  case class Some[T] private[part3fp] (value: T) extends MayBe[T] {

    override def map[B](function: T => B): MayBe[B] = Some[B](function(value))

    override def flatMap[B](function: T => MayBe[B]): MayBe[B] = function(value)

    override def filter(predicate: T => Boolean): MayBe[T] = if (predicate(value)) this else None
  }

  case object None extends MayBe[Nothing] {
    override def value: Nothing = throw new RuntimeException("Unable get a value from None")

    override def map[B](function: Nothing => B): MayBe[B] = None

    override def flatMap[B](function: Nothing => MayBe[B]): MayBe[B] = None

    override def filter(predicate: Nothing => Boolean): MayBe[Nothing] = None
  }

  def main(args: Array[String]): Unit = {
    /*
      1. MyList supports for comprehensions ? - YES, because MyList has a map functions
      2. A small collection of at most ONE element - MayBe[+T]
          - map
          - flatMap
          - filter
     */
    val mayBeNone: MayBe[Int] = None

    val mayBeInt = MayBe(5)

    println(mayBeInt)
    println(mayBeInt.map(_ * 2))
    println(mayBeInt.flatMap(x => MayBe(x % 2 == 0)))
    println(mayBeNone.flatMap(x => MayBe(x % 2 == 0)))
    println(mayBeInt.filter(x => x > 5))
  }
}
