package lectures.part5typesystem

object AdvancedInheritance extends App {

  // convenience
  trait Writer[T] {
    def write(value: T): Unit
  }

  trait Closeable {
    def close(status: Int): Unit
  }

  trait GenericStream[T] {
    // some methods

    def head: T

    def foreach(f: T => Unit): Unit
  }

  def processStream[T](stream: GenericStream[T] with Writer[T] with Closeable): Unit = {
    stream.foreach(println)
    stream.write(stream.head)
    stream.close(0)
  }

  // Diamond inheritance problem
  trait Animal { def name: String}
  trait Lion extends Animal { override def name: String = "Lion" }
  trait Tiger extends Animal { override def name: String = "Tiger" }
  class Mutant extends Lion with Tiger // {
  //    override def name: String = super.name +  "\nALIEN"
  //  }

  /**
    *           Animal
    *
    *    Lion            Tiger
    *
    *           Mutant
    */

  println(new Mutant().name)
  /**
    *   Mutant extends Animal[Lion] with { override def name: String = "Lion" }
    *       with extends Animal [Tiger] with { override def name: String = "Tiger" }
    *
    *   >>> This reduces for this kind of declaration
    *   Mutant extends Animal[Lion] with extends Animal [Tiger] with { override def name: String = "Tiger" }
    *
    *   So, LAST OVERRIDE GETS PICKED
    */

  // TYPE LINEARIZATION
  trait Cold { def print(): Unit = println("Cold") }
  trait Green extends Cold { override def print(): Unit = { println("Green"); super.print() } }
  trait Blue extends Cold { override def print(): Unit = { println("Blue"); super.print() } }

  class Red { def print(): Unit =  println("Red") }

  class White extends Red with Green with Blue {
    override def print(): Unit = {
      println("White")
      super.print()
    }
  }

  new White().print()
  /**
    *                     Cold
    *                    /    \
    *     Red         Green   Blue
    *        \         /    /
    *             White
    *
    *  Cold = AnyRef with <Cold>
    *  Green = Cold with <Green>
    *          AnyRef with <Cold> with <Green>
    *  Blue = Cold with <Blue>
    *          AnyRef with <Cold> with <Blue>
    *  Red  = AnyRef with <Red>
    *
    *  White => Red with Green with Blue with White
    *  White => AnyRef with <Red> with <Cold> with <Green> with <Blue> with <White>
    */
}
