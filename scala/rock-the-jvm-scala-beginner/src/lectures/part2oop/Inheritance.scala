package lectures.part2oop

object Inheritance extends App {

  class Animal {
    val creatureType = "wild"

    def eat() : Unit = println("nom-nom-nom")

  }

  // Single class inheritance
  class Cat extends Animal {

    def crunch(): Unit = {
      this.eat()
      println("crunch crunch")
    }

  }

  // overriding
  class Dog(override val creatureType: String) extends Animal {

    override def eat(): Unit = println("crunch crunch")

  }

  class Person(val name: String, val age: Int)

  // constructors
  class Adult(name: String, age: Int, val idCard: Long) extends Person(name, age)

  // type substitution: Polymorphism
  val unknownAnimal: Animal = new Dog("wild")
  unknownAnimal.eat()


  // preventing override
  // 1. use final keyword on the member of class
  // 2. use final keyword on the entire class
  // 3. seal the class = extended classes is only in this file, prevent extensions in other files

  val cat = new Cat
  cat crunch()
  println(cat.creatureType)

  val dog = new Dog("K9")
  dog.eat()
  println(dog.creatureType)

  val adult = new Adult("Hermes", 21, 213L)
  adult.name

}
