package filesystem.commands
import filesystem.State

object ExitCommand extends Command {

  override def apply(state: State): State = {
    State(state.root, state.workingDirectory, "Goodbye", isRunning = false)
  }

}
