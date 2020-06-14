package filesystem.commands
import filesystem.State

object Clear extends Command {
  override def apply(state: State): State = {
    state.copy(output = "\n" * 100)
  }
}
