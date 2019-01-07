package filesystem

import filesystem.files.Directory

class State(val workingDirectory: Directory,
            val output: String,
            val isRunning: Boolean) {

  def show(): Unit = {
    if(!output.isEmpty)
      println(output)
    print(s"${workingDirectory.name}$SHELL_TOKEN")
  }

}

object State {

  def apply(workingDirectory: Directory,
            output: String = "",
            isRunning: Boolean = true): State
      = new State(workingDirectory, output, isRunning)
}