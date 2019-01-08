package filesystem.commands

import filesystem.State

trait Command {

  def apply(state: State): State

}

object Command {

  def emptyCommand(): Command = (state: State) => State(state.workingDirectory)

  def from(input: String): Command = {
    val params = input.split("\\s+")

    params match  {
      case Array("") => emptyCommand()
      case Array("touch", _*) => new Touch(params.slice(1, params.length))
      case Array("mkdir", _*) => new Mkdir(params(1))
      case Array("cd", _*) => if (params.nonEmpty) new Cd(params(1)) else new Cd("")
      case Array("cls", _*) => Clear
      case Array("clear", _*) => Clear
      case Array("pwd", _*) => Pwd
      case Array("ls", _*) => Ls
      case Array("tree", _*) => Tree
      case Array("exit", _*) => ExitCommand
      case _ => UnknownCommand
    }
  }

}
