package exercises.part5typesystem


// ENFORCE A TYPE TO BE APPLICABLE TO SOME TYPES ONLY
object TypeMembers extends App {

  trait MList {
    type A
    def head: A
    def tail: MList
  }

  trait ApplicableToNumbers {
    type A <: Number
  }

//  class CustomList(hd: String, tl: CustomList) extends MList with ApplicableToNumbers {
//    override type A = String
//
//    override def head: String = hd
//
//    override def tail: MList = tl
//  }

  class IntList(hd: Integer, tl: IntList) extends MList with ApplicableToNumbers {
    type A = Integer

    def head: Integer = hd

    def tail: MList = tl
  }
}
