package lectures.part2oop

object AnonymousClasses extends App {


  abstract class Animal {
    def eat: Unit
  }

  val funnyAnimal: Animal = new Animal {
    override def eat: Unit = println("Ha-ha-ha")
  }

  // Equivalent for
  //  class AnonymousClasses$$anon$1 extends Animal {
  //    override def eat: Unit = println("Ha-ha-ha")
  //  }
  //  val funnyAnimal: Animal = new AnonymousClasses$$anon$1

  funnyAnimal.eat
  println(funnyAnimal.getClass)

  class Person(val name: String) {
    def sayHi: String = s"Hi, my name is $name, how can I help you?"
  }

  val jim = new Person("Jim"){
    override def sayHi: String = s"Hi, my name is $name, how can I be of service?"
  }

}
