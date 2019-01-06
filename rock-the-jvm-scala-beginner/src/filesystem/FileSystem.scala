package filesystem

import java.util.Scanner

import filesystem.commands.Command
import filesystem.files.Directory
import filesystem.files.root.RootDirectory

object FileSystem {

  def main(args: Array[String]): Unit = {

    val root: Directory = RootDirectory
    val scanner: Scanner = new Scanner(System.in)
    var state: State = State(root, root)

    do {
      state.show()
      val command: String = scanner.nextLine()
      state = Command.from(command)(state)
    } while (state.isRunning)
  }

}
