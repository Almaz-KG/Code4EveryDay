package exercises.part3concurrency

import scala.concurrent.{Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Random, Success, Try}

/*
  1. Fulfill a future immediately with a value
  2. inSequence(fa, fb)
  3. firstValue(fa, fb): future (first value of two features)
  4. lastValue(fa, fb): future (last value of two features)
  5. retryUntil(action => Future[T], condition: T => Boolean): Future[T]
 */
object FutureExamples extends App {

  lazy val fastFuture = Future {
    println("[fast-future] started")
    Thread.sleep(200)
    println("[fast-future] completed")
    "Fast future"
  }
  lazy val slowFuture = Future {
    println("[slow-future] started")
    Thread.sleep(500)
    println("[slow-future] completed")
    "Slow future"
  }

  def exercise1(): Unit = {
    def fulfillImmediately[T](value: T): Future[T] = Future(value)
    fulfillImmediately(42).onComplete {
      case Success(s) => println(s)
      case Failure(e) => e.printStackTrace()
    }
    Thread.sleep(100)
  }

  def exercise2(): Unit = {
    def inSequence[A, B](f1: Future[A], f2: Future[B]): Future[B] = f1.flatMap(_ => f2)

    inSequence(fastFuture, slowFuture).onComplete {
      case Success(value) => println(s"Result is '$value'")
      case Failure(exception) => exception.printStackTrace()
    }

    Thread.sleep(2000)
  }

  def exercise3(): Unit = {
    def first[A](fa: Future[A], fb: Future[A]): Future[A] = {
      val promise = Promise[A]
      fa.onComplete(promise.tryComplete)
      fb.onComplete(promise.tryComplete)
      promise.future
    }

    first(fastFuture, slowFuture).onComplete {
      case Success(value) => println(s"Result is '$value'")
      case Failure(exception) => exception.printStackTrace()
    }

    Thread.sleep(1000)
  }

  def exercise4(): Unit = {
    def last[A](fa: Future[A], fb: Future[A]): Future[A] = {
      // 1. promise which both futures will try to complete
      // 2. promise which the LAST future will complete

      val bothPromise = Promise[A]
      val lastPromise = Promise[A]

      val checkAndComplete = (result: Try[A]) =>
        if (!bothPromise.tryComplete(result))
          lastPromise.complete(result)

      fa.onComplete(checkAndComplete)
      fb.onComplete(checkAndComplete)

      lastPromise.future
    }

    last(fastFuture, slowFuture).onComplete {
      case Success(value) => println(s"Result is '$value'")
      case Failure(exception) => exception.printStackTrace()
    }

    Thread.sleep(1000)
  }

  def exercise5(): Unit = {
    def retryUntil[T](action: () => Future[T], condition: T => Boolean): Future[T] = {
      action()
        .filter(condition)
        .recoverWith {
          case _ => retryUntil(action, condition)
        }
    }

    val random = new Random()
    val action = () => Future {
      Thread.sleep(200)
      val value = random.nextInt(100)
      println(s"Generated value $value")
      value
    }

    retryUntil(action, (x: Int) => x < 10).foreach(r => println(s"Settled is $r"))
    Thread.sleep(5000)
  }

  exercise5()

}
