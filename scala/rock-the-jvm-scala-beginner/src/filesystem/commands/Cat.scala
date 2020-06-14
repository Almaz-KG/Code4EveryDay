package filesystem.commands

import filesystem.State
import filesystem.files.Directory

class Cat(fileNames: Array[String]) extends Command {

  override def apply(state: State): State = {
    if (fileNames.isEmpty)
      state.setMessage("cat: Please, specify file name")
    else {
      val output = fileNames
        .map(fn => fileContent(state.workingDirectory, fn))
        .mkString("\n")

      state.setMessage(output)
    }
  }

  private def fileContent(directory: Directory, fileName: String): String = {
    if (!directory.containFile(fileName))
      s"cat: `$fileName`: No such file or directory"
    else {
      directory.getChild(fileName).content
    }
  }
}
