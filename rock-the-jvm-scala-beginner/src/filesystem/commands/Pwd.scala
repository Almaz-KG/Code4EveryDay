package filesystem.commands
import filesystem.State

object Pwd extends Command {

  override def apply(state: State): State = {

    State(state.root, state.workingDirectory, state.workingDirectory.path)

  }
}
