package filesystem.commands
import filesystem.State

object Ls extends Command {
  override def apply(state: State): State = {
    val message: String = state
      .workingDirectory
      .contents
      .map(f => f.toString)
      .mkString("\n")

    state.copy(output = message)
  }
}
