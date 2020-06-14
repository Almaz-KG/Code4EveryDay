package lectures.part5typesystem

object PathDependentTypes extends App {

  class Outer {
    class Inner

    object InnerObject

    type InnerType

    def print(inner: Inner): Unit = println(inner)

    def printGeneral(inner: Outer#Inner): Unit = println(inner)
  }

  // path dependence types
  def aMethod: Int = {
    class HelperClass

    //    type HelperType NO
    //    type HelperType OK

    val outer = new Outer
    //    val inner = new Inner
    //    val inner = new Outer.Inner
    val inner = new outer.Inner
    outer.print(inner)

    val anotherOuter = new Outer
    //    val anotherInner: anotherOuter.Inner = new outer.Inner
    val anotherInner = new anotherOuter.Inner

    //    anotherOuter.print(inner)
    anotherOuter.print(anotherInner)

    anotherOuter.printGeneral(inner)
    anotherOuter.printGeneral(anotherInner)

    42
  }
}
