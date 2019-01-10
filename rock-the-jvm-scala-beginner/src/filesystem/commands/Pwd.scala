package filesystem.commands
import filesystem.State

object Pwd extends Command {

  override def apply(state: State): State = {
    state.copy(output = state.workingDirectory.path)
  }
}
