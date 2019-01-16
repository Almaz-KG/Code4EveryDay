package filesystem.commands

import filesystem.State

trait Command {

  def apply(state: State): State

}

object Command {

  def emptyCommand(): Command = new Command {
    override def apply(state: State): State = state.copy(output = "")
  }

  def from(input: String): Command = {
    val params = input.split("\\s+")

    params match  {
      case Array("") => emptyCommand()
      case Array("cat", tail@_*) => new Cat(tail.toArray)
      case Array("echo", tail@_*) => new Echo(tail.toArray)
      case Array("touch", tail@_*) => new Touch(tail.toArray)
      case Array("mkdir", tail@_*) => new Mkdir(tail.toArray)
      case Array("rm", tail@_*) => new Rm(tail.toArray)
      case Array("cd", _*) => if (params.nonEmpty) new Cd(params(1)) else new Cd("")
      case Array("cls", _*) => Clear
      case Array("clear", _*) => Clear
      case Array("pwd", _*) => Pwd
      case Array("ls", _*) => Ls
      case Array("tree", _*) => Tree
      case Array("exit", _*) => Exit
      case _ => UnknownCommand
    }
  }
}
