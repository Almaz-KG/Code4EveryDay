package lectures.part3concurrency

import scala.collection.mutable
import scala.util.Random

object ProducerAndConsumerProblem3Almaz extends App {
  /*
      Producer 1                  Consumer 1
      Producer 2 -> [ ? ? ? ] ->  Consumer 2
      Producer N                  Consumer N

   */
  def producerAndConsumerBufferParallel(): Unit = {
    val buffer: mutable.Queue[Int] = new mutable.Queue[Int]
    val capacity = 3

    val consumerLogic: Runnable = () => {
      val name = Thread.currentThread().getName
      val rnd = new Random

      while (true){
        buffer.synchronized {
          while (buffer.isEmpty) {
            println(s"[consumer-$name] buffers is empty, so waiting...")
            buffer.wait()
          }
          println(s"[consumer-$name] I have consumed " + buffer.dequeue())
          buffer.notify()
        }

        Thread.sleep(rnd.nextInt(500))
      }
    }

    val producerLogic: Runnable = () => {
      val name = Thread.currentThread().getName
      val rnd = new Random
      var i = 0
      while (true) {
        buffer.synchronized {
          while (buffer.size == capacity) {
            println(s"[producer-$name] buffer is full, so waiting...")
            buffer.wait()
          }

          println(s"[producer-$name] I have produced value " + i)
          buffer.enqueue(i)
          buffer.notify()
          i += 1
        }
        Thread.sleep(rnd.nextInt(500))
      }
    }

    val consumer1 = new Thread(consumerLogic, "c1")
    val consumer2 = new Thread(consumerLogic, "c2")
    val consumer3 = new Thread(consumerLogic, "c3")

    val producer1 = new Thread(producerLogic, "p1")
    val producer2 = new Thread(producerLogic, "p1")
    val producer3 = new Thread(producerLogic, "p1")

    consumer1.start()
    consumer2.start()
    consumer3.start()

    producer1.start()
    producer2.start()
    producer3.start()
  }

  producerAndConsumerBufferParallel()
}
