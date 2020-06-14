package lectures.part2oop

object AbstractDataTypes extends App {

  // abstract
  abstract class Animal {

    val creatureType: String

    def eat()

  }

  class Dog extends Animal {

    override val creatureType: String = "Canine"

    override def eat(): Unit = println("crunch crunch")

  }

  val x: Int = 4

  // traits
  trait Carnivore {

    def eat(animal: Animal): Unit

  }

  trait Coldblooded {

    def eat(): Unit = println("Coldblooded")

  }

  class Crocodile extends Animal with Carnivore with Coldblooded {

    override val creatureType: String = "croc"

    override def eat(): Unit = println("nom-nom")

    override def eat(animal: Animal): Unit = println(s"I am $creatureType and I'a eating ${animal.creatureType}")

  }

  val dog = new Dog
  val croc = new Crocodile
  croc.eat(dog)
  croc.eat()


  // trait vs abstract classes
  // 1. traits do not have constructor parameters
  // 2. extends only one abstract class by the same class, but multiple traits
  // 3. traits = behavior, abstract class = type of think


  // Scala's type hierarchy
  /*
                      scala.Any
       scala.AnyVal                 scala.AnyRef
   scala.[Int, Float, ... ]              all classes
                                    scala.Null (no reference)
                    scala.Nothing
   */
}
