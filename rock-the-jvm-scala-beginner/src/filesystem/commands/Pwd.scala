package filesystem.commands
import filesystem.State

object Pwd extends Command {

  override def apply(state: State): State = {

    State(state.workingDirectory, state.workingDirectory.path)

  }
}
