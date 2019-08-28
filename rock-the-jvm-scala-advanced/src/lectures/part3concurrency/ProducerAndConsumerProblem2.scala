package lectures.part3concurrency

import scala.collection.mutable
import scala.util.Random

object ProducerAndConsumerProblem2 extends App {
  /*
      Producer -> [ ? ? ? ] -> Consumer
   */
  def producerAndConsumerBuffer(): Unit = {
    val buffer: mutable.Queue[Int] = new mutable.Queue[Int]
    val capacity = 3

    val consumer = new Thread(() => {
      println("[consumer] waiting...")
      val rnd = new Random

      while (true){
        buffer.synchronized {
          if (buffer.isEmpty) {
            println("[consumer] buffers is empty, so waiting...")
            buffer.wait()
          }
          println("[consumer] I have consumed " + buffer.dequeue())
          buffer.notify()
        }

        Thread.sleep(rnd.nextInt(500))
      }
    })

    val producer = new Thread(() => {
      println("[producer] computing...")
      val rnd = new Random
      var i = 0
      while (true) {
        buffer.synchronized {
          if (buffer.size == capacity) {
            println("[producer] buffer is full, so waiting...")
            buffer.wait()
          }

          println("[producer] I have produced, after long work, the value " + i)
          buffer.enqueue(i)
          buffer.notify()
          i += 1
        }
        Thread.sleep(rnd.nextInt(500))
      }
    })

    consumer.start()
    producer.start()
  }

  producerAndConsumerBuffer()
}
