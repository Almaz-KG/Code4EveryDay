package exercises.part5typesystem


/**
  * DB keyed by Int or Strings but maybe others
  */
object PathDependentTypes extends App {

  class MySuperDb {

    trait ItemLike {
      type Key
    }

    trait Item[K] extends ItemLike {
      override type Key = K
    }

    class IntItem extends Item[Int]

    class StringItem extends Item[String]

    def get[ItemType <: MySuperDb#ItemLike](key: ItemType#Key): ItemType = ???

  }

  val db = new MySuperDb

  db.get[MySuperDb#IntItem](42)          //  OK
  db.get[MySuperDb#StringItem]("home")   //  OK

  // db.get[MySuperDb#IntItem]("Scala")     //  NOT OK
}
