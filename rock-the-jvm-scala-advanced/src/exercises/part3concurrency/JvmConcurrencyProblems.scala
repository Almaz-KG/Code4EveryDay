package exercises.part3concurrency


object JvmConcurrencyProblems extends App {
  /*
    Exercise

    1) Construct 50 "inception" threads
        Thread1 => Thread2 => Thread3 => ... => ThreadN
          Each thread must print greeting message
            -> "Hello, I am #number thread
          IN REVERSE ORDER
   */
  def exercise1(): Unit = {
    val max_thread_count = 50

    def threadGenerator(index: Int): Thread = new Thread(() => {
      println(s"Hello, I am $index thread")
      if (index > 0)
        threadGenerator(index - 1).start()
    })

    threadGenerator(max_thread_count).start()
  }

  def exercise2(): Unit = {
    def logic(): Int = {
      var x = 0
//      val threads = (1 to 100).map(_ => new Thread(() => x.synchronized{ x += 1 } ))
      val threads = (1 to 100).map(_ => new Thread(() => x += 1 ))
      threads.foreach(_.start())
      threads.foreach(_.join())

      // What is the biggest value possible for x ?
      // What is the smallest value possible for x ?

      // ANSWERS
      // Biggest value is 100 (if all threads will increment the value of x one after one
      // The smallest value is 1 (if exist some slowest thread which rewrite the result of others
      x
    }

    val ints = (0 to 1000).map(_ => logic())

    println(ints)
    println(ints.max)
    println(ints.min)
  }

  def exercise3(): Unit = {
    var message = ""
    val awesomeThread = new Thread(() => {
      Thread.sleep(1000)
      message = "Scala is awesome"
    })

    message = "Scala sucks"
    awesomeThread.start()
    Thread.sleep(2000)

    /*
      - What's the value of message ?
      - Is it guaranteed? Why or why not?
     */
    println(message)
  }

  exercise1()
  exercise2()
  exercise3()
}
