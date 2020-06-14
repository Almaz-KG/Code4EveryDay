package lectures.part5typesystem

object TypeMembers extends App {

  class Animal
  class Dog extends Animal
  class Cat extends Animal

  class AnimalCollection {
    type AnimalType // abstract type member
    type BoundedAnimal <: Animal
    type SuperBoundedAnimal >: Dog <: Animal
    type AnimalC = Cat
  }

  val ac = new AnimalCollection
  val dog: ac.AnimalType = ???

  val cat1: ac.AnimalC = new Cat
  //  val cat2: ac.BoundedAnimal = new Cat

  val pup: ac.SuperBoundedAnimal = new Dog

  type CatAlias = Cat
  val anotherCat: CatAlias = new Cat


  // AN ALTERNATIVE TO GENERICS
  trait MyList {
    type T

    def add(element: T): MyList
  }

  class NonEmptyList(value: Int) extends MyList {
    override type T = Int

    override def add(element: Int): MyList = ???
  }


  type CatsType = cat1.type
  val newCat: CatsType = cat1
  //  new CatsType
}
