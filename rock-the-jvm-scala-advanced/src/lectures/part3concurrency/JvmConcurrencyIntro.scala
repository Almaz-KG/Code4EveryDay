package lectures.part3concurrency

import java.util.concurrent.Executors

object JvmConcurrencyIntro extends App {
  /*
      interface Runnable {
        public void run()
      }
   */
  val runnable: Runnable = () => {
    println("Running in parallel")
  }
  // JVM threads
  val aThread = new Thread(runnable)

  aThread.start() // gives the signal to the JVM to start a JVM thread
  // create a JVM thread => OS Thread
  runnable.run() // PAY ATTENTION: doesn't to anything in parallel !!

  aThread.join()  // blocks until aThread finishes running

  val threadHello = new Thread(() => (1 to 5).foreach(_ => println("Hello")))
  val threadGoodbye = new Thread(() => (1 to 5).foreach(_ => println("GoodBye")))

  threadGoodbye.start()
  threadHello.start()
  // different runs produce different results!

  // Executors
  val executorsPool = Executors.newFixedThreadPool(10)
  executorsPool.execute(() => println("Something in the thread pool"))
  executorsPool.execute(() => {
    Thread.sleep(1000)
    println("Done after 1 second")
  })
  executorsPool.execute(() => {
    Thread.sleep(1000)
    println("almost done")
    Thread.sleep(1000)
    println("done after 2 seconds")
  })

  executorsPool.shutdown()
  //  executorsPool.execute(() => println("Ooooops!"))
  //
  //  executorsPool.shutdownNow()
  println(executorsPool.isShutdown)
}
