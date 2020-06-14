package lectures.part5typesystem

object Variance extends App {

  trait Animal
  class Dog extends Animal
  class Cat extends Animal
  class Crocodile extends Animal

  // What is variance
  // "inheritance" - type substitution of generics

  class Cage[T]

  // covariance
  class CCage[+T]
  val ccage: CCage[Animal] = new CCage[Cat] // Animal -> Cat

  // invariance
  class ICage[T]
  //  val icage1: ICage[Cat] = new ICage[Animal] // Cat -> Animal
  //  val icage1: ICage[Animal] = new ICage[Cat] // Cat -> Animal

  // contravariance
  class XCage[-T]
  val xCage: XCage[Cat] = new XCage[Animal]

  class InvariantCage[T](val animal: T) // invariant position

  class CovariantCage[+T](val animal: T) // covariant position

  //  class ContravariantCage[-T](val animal: T) // contracovariant position - DOES NOT WORKS
  //  Because we can do that:
  //    val catCage: XCage[Cat] = new XCage[Animal](new Crocodile)

  //  class CovariantVariableCage[+T](var animal: T) // types of vars are in contracovariant position
  //  IT DOES NOT WORK AGAIN, because we can do that
  //    val ccage: CovariantVariableCage[Animal] = new CovariantVariableCage[Cat](new Cat)
  //    ccage.animal = new Crocodile

  //  class ContracovariantVariableCage[-T](var animal: T) // types of vars are in covariant position
  //  IT DOES NOT WORK AGAIN, because we can do that
  //    val ccage: CovariantVariableCage[Cat] = new CovariantVariableCage[Animal](new Crocodile)

  class InvariantVariableCage[T](var animal: T) // DOES WORKS!


  //  trait AnotherCovariantCage[+T] {
  //    def addAnimal(animal: T) // CONTRAVARIANT POSITION
  //  }
  //  This example does not work,
  //  because we can do that
  //    val ccage: CCage[Animal] = new CCage[Dog]
  //    ccage.add(new Cat)
  //

  class AnotherCovariantCage[-T] {
    def addAnimal(animal: T) = true // COVARIANT POSITION - ITS WORKS FINE
  }

  val acc: AnotherCovariantCage[Cat] = new AnotherCovariantCage[Animal]
  acc.addAnimal(new Cat)
  class Kitty extends Cat
  acc.addAnimal(new Kitty)

  //  acc.addAnimal(new Crocodile) // DOES NOT WORK
  //  acc.addAnimal(new Dog) // DOES NOT WORK


  class MyList[+A] {
    def add[B >: A](element: B): MyList[B] = ??? // widening the type
  }

  val emptyList = new MyList[Kitty]
  val animal = emptyList.add(new Kitty)
  val moreAnimals = animal.add(new Cat)
  val evenMoreAnimals = moreAnimals.add(new Dog)

  val dangerZone = moreAnimals.add(new Object)

  // METHOD ARGUMENTS ARE IN CONTRAVARIANT POSITION

  // return types
  class PetShop[-T] {
  //  def get(isItAPuppy: Boolean): T // METHOD RETURN TYPES ARE IN COVARIANT POSITION
  //
  //  val catShop = new PetShop[Animal]{
  //    def get(isItAPuppy: Boolean): Animal = new Cat
  //  }
  //
  //  val dogShop: PetShop[Dog] = catShop
  //  dogShop.get(true) => RETURNS THE CAT!
  //

    def get[S <: T](isItAPuppy: Boolean, /* FOR EXAMPLE ONLY */ defaultAnimal: S): S = defaultAnimal
  }

  val shop: PetShop[Dog] = new PetShop[Animal]
  //  val cat = shop.get(isItAPuppy = true, new Cat)
  class TerraNova extends Dog
  val bigFurry = shop.get(isItAPuppy = true, new TerraNova)


  /**
    *   BIG RULES
    *   - method arguments are in CONTRAVARIANT positions
    *   - return types are in COVARIANT position
    */
}
