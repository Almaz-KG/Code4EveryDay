package filesystem.commands
import filesystem.State

object UnknownCommand extends Command {

  override def apply(state: State): State = state.copy(output = "Command not found")

}
