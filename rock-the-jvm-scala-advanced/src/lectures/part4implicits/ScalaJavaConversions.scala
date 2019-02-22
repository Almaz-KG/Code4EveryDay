package lectures.part4implicits


import java.{ util => java_utils}

object ScalaJavaConversions extends App {

  import collection.JavaConverters._

  val javaSet: java_utils.Set[Int] = new java_utils.HashSet[Int]()
  (1 to 5).foreach(javaSet.add)
  println(javaSet)

  val scalaSet = javaSet.asScala

  /**
    *   Bunch of Java to Scala converters
    *   - Iterator
    *   - Iterable
    *   - java.List => scala.collection.mutable.List
    *   - java.List => scala.collection.mutable.Buffer
    *   - etc...
    */

  import collection.mutable._
  val numbersBuffer = ArrayBuffer[Int](1, 2, 3)
  val javaNumberBuffer = numbersBuffer.asJava

  println(javaNumberBuffer.asScala eq numbersBuffer)

  val numbers = List(1, 2, 3)
  val javaNumbers = numbers.asJava
  val backToScala = javaNumbers.asScala

  println(backToScala eq numbers) // false - the are different object
  println(backToScala == numbers) // true - the are with the same elements

  class ToScala[T](value: => T) {
    def asScala: T = value
  }

  implicit def asScalaOptional[T](o: java_utils.Optional[T]): ToScala[Option[T]] = new ToScala[Option[T]](
    if (o.isPresent) Some(o.get())
    else None
  )

  val javaOptional: java_utils.Optional[Int] = java_utils.Optional.of(123)
  val scalaOptional = javaOptional.asScala

  println(scalaOptional)
}
