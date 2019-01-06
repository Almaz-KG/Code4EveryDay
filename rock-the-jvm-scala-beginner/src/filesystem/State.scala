package filesystem

import filesystem.files.Directory

case class State(root: Directory,
                 workingDirectory: Directory,
                 output: String,
                 isRunning: Boolean) {

  def show(): Unit = {
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