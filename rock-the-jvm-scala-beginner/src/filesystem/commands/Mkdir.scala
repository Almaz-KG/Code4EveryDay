package filesystem.commands
import filesystem.State
import filesystem.files.Directory

class Mkdir(directory: String) extends Command {

  override def apply(state: State): State = {
    if (directory.isEmpty)
      State(state.workingDirectory, output = "usage mkdir [directories]")
    else {
      if(directory.startsWith(filesystem.DIRECTORY_SEPARATOR))
        State(state.workingDirectory, s"mkdir: $directory: Permission denied")
      else if (directory.contains(filesystem.DIRECTORY_SEPARATOR)) {
        // TODO: Implement here the "-p" parameter
        State(state.workingDirectory, s"mkdir: name must not contain ${filesystem.DIRECTORY_SEPARATOR}")
      } else if(state.workingDirectory.containFile(directory)){
          State(state.workingDirectory, s"mkdir: $directory: File exists")
      } else if(!isValidDirectoryName(directory)){
        State(state.workingDirectory, s"mkdir: $directory: is invalid name")
      } else {
        createDirectory(directory, state)
      }
    }
  }

  private def isValidDirectoryName(name: String): Boolean = !name.contains(".")

  private def createDirectory(name: String, state: State): State = {
    val wd = state.workingDirectory

    // all folders from root to working directory
    val folders: List[String] = getAllFolders(wd).map(f => f.name)

    val newDirectory = Directory.empty(wd, name)

    val newRoot = updateFileSystemStructure(findRoot(wd), folders, newDirectory)

    val newWd = newRoot.findDescendantDirectory(folders)

    newWd
      .map(wd => State(wd))
      .getOrElse(
        State(wd, "Something happend wrong"))
  }
}
