package filesystem.commands
import filesystem.State

object Ls extends Command {
  override def apply(state: State): State = {
    val output: String = state
      .workingDirectory
      .contents
      .map(f => f.toString)
      .mkString("\n")

    State(state.root, state.workingDirectory, output)
  }
}
