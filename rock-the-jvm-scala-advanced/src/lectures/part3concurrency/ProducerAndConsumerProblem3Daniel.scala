package lectures.part3concurrency

import scala.collection.mutable
import scala.util.Random

object ProducerAndConsumerProblem3Daniel extends App {
  /*
      Producer 1                  Consumer 1
      Producer 2 -> [ ? ? ? ] ->  Consumer 2
      Producer N                  Consumer N
   */
  class Consumer(id: String, buffer: mutable.Queue[Int]) extends Thread {
    override def run(): Unit = {
      val rnd = new Random

      while (true){
        buffer.synchronized {
          while (buffer.isEmpty) {
            println(s"[consumer-$id] buffers is empty, so waiting...")
            buffer.wait()
          }
          println(s"[consumer-$id] I have consumed value " + buffer.dequeue())
          buffer.notify()
        }

        Thread.sleep(rnd.nextInt(500))
      }
    }
  }

  class Producer(id: String, buffer: mutable.Queue[Int], capacity: Int) extends Thread {
    override def run(): Unit = {
      val rnd = new Random
      var i = 0
      while (true) {
        buffer.synchronized {
          while (buffer.size == capacity) {
            println(s"[producer-$id] buffer is full, so waiting...")
            buffer.wait()
          }

          println(s"[producer-$id] I have produced value " + i)
          buffer.enqueue(i)
          buffer.notify()
          i += 1
        }
        Thread.sleep(rnd.nextInt(500))
      }
    }
  }

  def producerAndConsumerBufferParallel(): Unit = {

    val buffer: mutable.Queue[Int] = new mutable.Queue[Int]
    val capacity = 20

    new Consumer("c1", buffer).start()
    new Consumer("c2", buffer).start()
    new Consumer("c3", buffer).start()

    new Producer("p1", buffer, capacity).start()
    new Producer("p2", buffer, capacity).start()
    new Producer("p3", buffer, capacity).start()
//    new Producer("p4", buffer, capacity).start()
//    new Producer("p5", buffer, capacity).start()
  }

  producerAndConsumerBufferParallel()
}
