package filesystem.commands
import filesystem.State

object UnknownCommand extends Command {

  override def apply(state: State): State =
    State(state.root, state.workingDirectory, output = "Command not found")

}
