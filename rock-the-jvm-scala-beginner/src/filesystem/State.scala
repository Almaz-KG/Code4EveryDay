package filesystem

import filesystem.files.Directory

case class State(root: Directory,
                 workingDirectory: Directory,
                 output: String,
                 isRunning: Boolean) {

  def show(): Unit = {
    if(!output.isEmpty)
      println(output)
    print(s"${workingDirectory.name}$SHELL_TOKEN")
  }

  def setMessage(message: String): State = this.copy(output = message)
}