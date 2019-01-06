package filesystem.commands

import filesystem.State

trait Command {

  def apply(state: State): State

}

object Command {

  def from(input: String): Command = UnknownCommand

}
