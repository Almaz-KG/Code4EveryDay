package filesystem.commands

import filesystem.State

trait Command {

  def apply(state: State): State

}

object Command {

  def emptyCommand(): Command = (state: State) => State(state.root, state.workingDirectory)

  def from(input: String): Command = {
    val params = input.split("\\s+")

    params match  {
      case Array("") => emptyCommand()
      case Array("mkdir", _*) => new Mkdir(params.slice(1, params.length))
      case Array("cd", _*) => new Cd(params(1))
      case Array("pwd", _*) => Pwd
      case Array("ls", _*) => Ls
      case Array("exit", _*) => ExitCommand
      case _ => UnknownCommand
    }
  }

}
