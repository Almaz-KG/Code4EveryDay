package lectures.part3concurrency

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object FuturesAndPromises extends App {

  def calculateMeaningOfLife: Int = {
    Thread.sleep(2000)
    42
  }

  val aFuture = Future {
    calculateMeaningOfLife // calculates on another thread
  }

  println(aFuture.value) // Option[Try[Int]]
  println("Waiting on the future...")

  aFuture.onComplete {
    case Success(value) => println(s"The meaning of life is $value")
    case Failure(e) => println(s"I have failed with $e")
  }

  Thread.sleep(2500)
}
