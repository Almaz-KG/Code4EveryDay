package lectures.part2oop

object Generics extends App {

  class MyList[A] {

  }

  object MyList {
    def empty[T] : MyList[T] = new MyList[T]
  }

  class MyMap[K, V] {

  }

  trait MyTrait[A] {

  }

  val listOfIntegers = new MyList[Int]
  val listOfStrings = new MyList[String]

  val emptyListOfIntegers = MyList.empty[Int]


  // variance problem
  abstract class Animal
  class Cat extends Animal
  class Dog extends Animal

  // List[Cat] extends List[Animal] = COVARIANCE
  class CovariantList[+T] {

    def add[B >: T](value: B): ContravariantList[B] = ???

  }

  val animal: Animal = new Cat
//  val animalList: MyList[Animal] = new MyList[Cat] // Not worked because type of myList is not covariant
  val animalList: CovariantList[Animal] = new CovariantList[Cat] // Works fine
  animalList.add(new Dog)
  // animalList.add(new Dog) IS CORRECT WAY ? - Hard question

  // Invariant mode
  class InvariantList[T]
  //  val invariantList: InvariantList[Animal] = new InvariantList[Cat]
  val invariantList: InvariantList[Animal] = new InvariantList[Animal]

  // Contravariant mode
  class ContravariantList[-T]

  val contravariantList: ContravariantList[Dog] = new ContravariantList[Animal]
//  val contravariantList: ContravariantList[Dog] = new ContravariantList[Animal]

  // bounded types
  class Cage[A <: Animal](animal: A)

  val cage: Cage[Animal] = new Cage(new Cat)

}
