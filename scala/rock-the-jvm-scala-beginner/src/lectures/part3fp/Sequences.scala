package lectures.part3fp

import scala.util.Random

object Sequences {

  def main(args: Array[String]): Unit = {

    // Seq
    val aSequence = Seq(3, 4, 3, 1)
    println(aSequence)
    println(aSequence.reverse)
    println(aSequence(2))
    println(aSequence ++ Seq(5, 6, 7))
    println(aSequence.sorted)

    // Ranges
    val range: Seq[Int] = 1 to 10
    println(range)

    // Lists
    val aList = List(1, 2, 3)
    println(aList)
    val prepended = 42 :: aList
    println(prepended)
    val prepended2 = 42 +: aList
    println(prepended2)
    val appended = aList :+ 42
    println(appended)

    val apples5 = List.fill(5)("apple")
    println(apples5)
    println(apples5.mkString("-|-"))

    val array1 = Array(1, 2, 3, 4)
    val array3 = Array.ofDim[Int](3)
    val array5 = Array.ofDim[String](3)

    array3(2) = 100

    println(array1.mkString("-"))
    println(array3.mkString(" "))
    println(array5.mkString(" "))

    // array and seq
    val numbersSeq: Seq[Int] = array1 // implicit conversion
    println(numbersSeq)

    // vectors
    val vector1: Vector[Int] = Vector(1, 2, 3)
    println(vector1)


    // vectors vs lists
    def getWriteTime(collection: Seq[Int]): Double = {
      val size = collection.size
      val rnd = new Random
      val maxRuns = 1000
      val times = (0 to maxRuns).map (_ => {
        val currentTime = System.nanoTime()
        collection.updated(rnd.nextInt(size), 0)
        System.nanoTime() - currentTime
      })

      times.sum * 1.0 / maxRuns
    }

//    val list = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
//    val vector = Vector(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    val maxCapacity = 1000000
    // keeps reference to tail
    // updating an element in the middle takes long time
    val list: List[Int] = (0 to maxCapacity).toList

    // depth of the tree is small
    // needs to replace an entire 32-element chunk
    val vector: Vector[Int] = (0 to maxCapacity).toVector

    println(getWriteTime(list))
    println(getWriteTime(vector))









  }
}
