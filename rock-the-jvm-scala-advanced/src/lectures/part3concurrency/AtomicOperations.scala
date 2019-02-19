package lectures.part3concurrency

import java.util.concurrent.atomic.AtomicReference
import java.util.function.UnaryOperator

object AtomicOperations extends App {
  val atomic = new AtomicReference[Int](2)

  val currentValue = atomic.get() // thread-safe read
  atomic.set(4) // thread-safe write

  val oldValue = atomic.getAndSet(5) // thread-safe combo
  println(oldValue)

  atomic.compareAndSet(38, 45)
  // if (value = 38) value = 45
  // reference equality

  val updateFunction : Int => Int = x => x + 1
  val unaryOperator = new UnaryOperator[Int] {
    override def apply(x: Int): Int = x + 1
  }
  val newValue1 = atomic.updateAndGet(unaryOperator)
  val oldValue1 = atomic.getAndUpdate(unaryOperator)

  val newValue2 = atomic.accumulateAndGet(12, _ + _) // thread-safe accumulation
  val oldValue2: Int = atomic.getAndAccumulate(12, _ + _)
}
