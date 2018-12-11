package code4.every.day.scala99problems.lists

trait P01 {

  def last[A](list: List[A]): Option[A] = {
    if (list != null && list.nonEmpty)
      Some(list.last)
    else None
  }
}
