package lectures.part3fp

object AnonymousFunctions {


  def main(args: Array[String]): Unit = {

    // anonymous function (lambda)
    val doubler: Int => Int = x => x * 2

    // multiple params in lambda
    val adder: (Int, Int) => Int = (x, y) => x + y

    // no params
    val justFunction: () => Int = () => 1

    // curly braces with lambdas
    val stringToInt = { string: String => string.toInt}

    // MOAR syntactic sugar
//    val niceIncrementer: Int => Int = x => x + 1
    val niceIncrementer: Int => Int = _ + 1

//    val niceAdder: (Int, Int) => Int = (x, y) => x + y
    val niceAdder: (Int, Int) => Int = _ + _

    println(doubler (2))
    println(adder (2, 2))
    println(justFunction())
    println(stringToInt("777"))
    println(niceIncrementer(1))
    println(niceAdder(2, 1))
  }

}
