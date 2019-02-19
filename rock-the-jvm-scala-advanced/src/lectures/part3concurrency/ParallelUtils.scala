package lectures.part3concurrency

import java.util.concurrent.ForkJoinPool

import scala.collection.parallel.{ForkJoinTaskSupport, Task, TaskSupport}
import scala.collection.parallel.immutable.ParVector

// Parallel collections
object ParallelUtils extends App {
  val parallelList = List(1, 2, 3).par
  val parallelVector = ParVector[Int](1, 2, 3)

  def measure[T](operation: => T): Long = {
    val time = System.currentTimeMillis()
    operation
    System.currentTimeMillis() - time
  }

  val listSize = 10000
  val list = (1 to listSize).toList
  val serialTime = measure { list.map (_ + 1) }
  println(s"Serial time is $serialTime")

  val parallelTime = measure { list.par.map (_ + 1)  }

  println(s"Parallel time is $parallelTime")

  /**
    *  Parallel version of list works faster than serial if the size of list is big enough. So, it faster because,
    *  parallel version uses map-reduce paradigm
    *
    *  BUT: Be careful, there are some methods on collection that have undefined/unpredictable behaviour - non-associative
    *  Safe operations are:
    *  - map
    *  - flatMap
    *  - filter
    *  - others / etc...
    *
    *  Unsafe operations are:
    *  - reduce
    *  - fold
    *  - others / etc...
    */
  /**
      Map reduce model
      - split the elements of collection into chunks - by Splitter
      - execute operation
      - recombine - by Combiner
   */
  println("Serial subtraction of elements: " + List(1, 2, 3).reduce(_ - _))
  println("Parallel subtraction of elements: " + List(1, 2, 3).par.reduce(_ - _))

  // Need synchronization
  var sum = 0
  (1 to 100).par.foreach(sum += _)
  println(sum)

  // configuring parallel collection
  parallelVector.tasksupport = new ForkJoinTaskSupport(new ForkJoinPool(2))
  /**
      Alternatives
      - ThreadPoolTaskSupport - now deprecated
      - ExecutionContextTaskSupport
      - Own tast support implementation
   */
  parallelVector.tasksupport = new TaskSupport {
    override val environment: AnyRef = ???

    override def execute[R, Tp](fjtask: Task[R, Tp]): () => R = ???

    override def executeAndWaitResult[R, Tp](task: Task[R, Tp]): R = ???

    override def parallelismLevel: Int = 4
  }
}
