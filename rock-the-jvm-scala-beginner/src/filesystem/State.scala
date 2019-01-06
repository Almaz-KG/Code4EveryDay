package filesystem

import filesystem.files.Directory

class State(val root: Directory,
            val workingDirectory: Directory,
            val output: String,
            val isRunning: Boolean) {

  def show(): Unit = {
    if(!output.isEmpty)
      println(output)
    print(s"$SHELL_TOKEN")
  }

}

object State {

  def apply(root: Directory,
            workingDirectory: Directory,
            output: String = "",
            isRunning: Boolean = true): State
      = new State(root, workingDirectory, output, isRunning)
}