package exercises.part5typesystem

object Variances extends App {

  /**
    * 1. Invariant, covariant, contravariant implementation class for Parking
    *
    *     Parking[T](things: List[T]) {
    *       def park(vehicle: T)
    *       def impound(vehicles: List[T])
    *       def checkVehicles(conditions: String): List[T]
    *     }
    *
    * 2. Used someone else's API: IList[T]
    * 3. Make Parking class as monad
    */

  class Vehicle(val name: String)
  class Bike(name: String) extends Vehicle(name)
  class Car(name: String) extends Vehicle(name)

  class IList[T]

  class InvariantParking[T](vehicles: List[T]) {

    def park(vehicle: T): InvariantParking[T] = ???

    /**
      * Removes provided vehicles from the park
      */
    def impound(vehicles: List[T]): InvariantParking[T] = ???

    def checkVehicles(condition: T => Boolean): List[T] = ???

    def flatMap[S](f: T => InvariantParking[S]): InvariantParking[S] = ???

  }

  class CovariantParking[+T](vehicles: List[T]) {

    def park[B >: T](vehicle: B): CovariantParking[B] = ???

    def impound[B >: T](vehicles: List[B]): CovariantParking[B] = ???

    def checkVehicles(condition: T => Boolean): List[T] = ???

    def flatMap[S](f: T => CovariantParking[S]): CovariantParking[S] = ???

  }

  class ContravariantParking[-T](vehicles: List[T]) {

    def park(vehicle: T): ContravariantParking[T] = ???

    def impound(vehicles: List[T]): ContravariantParking[T] = ???

    def checkVehicles[S <: T](condition: S => Boolean): List[S] = ???

    def flatMap[R <: T, S](f: R => ContravariantParking[S]): ContravariantParking[S] = ???
  }

  /**
    * VARIANCES RULES
    * - use covariance = COLLECTION OF THINGS
    * - use contravariance = GROUP OF ACTIONS
    */

  class InvariantParkingMyList[T](vehicles: IList[T]) {

    def park(vehicle: T): InvariantParkingMyList[T] = ???

    /**
      * Removes provided vehicles from the park
      */
    def impound(vehicles: List[T]): InvariantParkingMyList[T] = ???

    def checkVehicles(condition: T => Boolean): List[T] = ???

    def flatMap[S](f: T => InvariantParkingMyList[S]): InvariantParkingMyList[S] = ???
  }

  class CovariantParkingMyList[+T](vehicles: IList[T]) {

    def park[B >: T](vehicle: B): CovariantParkingMyList[B] = ???

    def impound[B >: T](vehicles: IList[B]): CovariantParkingMyList[B] = ???

    def checkVehicles[B >: T](condition: B => Boolean): IList[B] = ???

    def flatMap[S](f: T => CovariantParkingMyList[S]): CovariantParkingMyList[S] = ???
  }

  class ContravariantParkingMyList[-T](vehicles: IList[T]) {

    def park(vehicle: T): ContravariantParkingMyList[T] = ???

    def impound[B <: T](vehicles: IList[B]): ContravariantParkingMyList[B] = ???

    def checkVehicles[B <: T](condition: B => Boolean): IList[B] = ???

    def flatMap[R <: T, S](f: R => ContravariantParkingMyList[S]): ContravariantParkingMyList[R] = ???
  }

}
