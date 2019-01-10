package filesystem.commands
import filesystem.State

object ExitCommand extends Command {

  override def apply(state: State): State = {
    state.copy(output = "Goodbye", isRunning = false)
  }

}
