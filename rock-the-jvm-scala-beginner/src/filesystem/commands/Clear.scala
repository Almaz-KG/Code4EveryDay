package filesystem.commands
import filesystem.State

object Clear extends Command {
  override def apply(state: State): State = {
    State(state.workingDirectory, "\n" * 100)
  }
}
