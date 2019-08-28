package lectures.part3concurrency

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Promise
import scala.util.Success

object Promises1 extends App {

  val promiseInt = Promise[Int] // controller over a future
  val future = promiseInt.future

  // thread-1 [consumer]
  future.onComplete{
    case Success(value) => println(s"[consumer] I have received $value")
  }

  // thread-2 [producer]
  val producer = new Thread(() => {
    println("[producer] crunching numbers")
    Thread.sleep(1000)
    // fulfilling

    promiseInt.success(42)
    println("[producer] done")
  })

  producer.start()
  Thread.sleep(1500)
}
