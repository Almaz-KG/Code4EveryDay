package exercises.part3concurrency

object JvmThreadDeadLock extends App {

  val object1 = "This is object1"
  val object2 = "This is object2"

  val thread1 = new Thread(() => {
    object1.synchronized {
      Thread.sleep(100)
      println("Trying to lock object2")
      object2.synchronized {
        println("Object2 locked")
      }
    }
  })

  val thread2 = new Thread(() => {
    object2.synchronized {
      Thread.sleep(100)
      println("Trying to lock object1")
      object1.synchronized {
        println("Object1 locked")
      }
    }
  })

  thread1.start()
//  Thread.sleep(1000)
  thread2.start()
}
