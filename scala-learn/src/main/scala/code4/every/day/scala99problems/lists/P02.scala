package code4.every.day.scala99problems.lists

trait P02 {

  def penultimate[A](list: List[A]): Option[A] = {
    if (list == null || list.isEmpty)
      None
    else
      Some(list.takeRight(2).head)
  }

}
