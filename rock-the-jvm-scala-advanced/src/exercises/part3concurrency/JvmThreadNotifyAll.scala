package exercises.part3concurrency

object JvmThreadNotifyAll extends App {

  val bell = new Object

  (1 to 10).foreach(i => new Thread(() => {
    bell.synchronized{
      println(s"[thread-$i] waiting...")
      bell.wait()
      println(s"[thread-$i] hooray!")
    }
  }).start())

  new Thread(() => {
    Thread.sleep(1000)

    bell.synchronized {
      println("[main-thread] bell")
//      bell.notify()
      bell.notifyAll()
    }
  }).start()

}
