package filesystem.commands
import filesystem.State
import filesystem.files.Directory

class Mkdir(directoryName: String) extends Command {

  override def apply(state: State): State = {
    if (directoryName.isEmpty)
      state.copy(output = "usage mkdir [directories]")
    else {
      if(directoryName.startsWith(filesystem.DIRECTORY_SEPARATOR))
        state.copy(output = s"mkdir: $directoryName: Permission denied")
      else if (directoryName.contains(filesystem.DIRECTORY_SEPARATOR)) {
        // TODO: Implement here the "-p" parameter
        state.copy(output = s"mkdir: name must not contain ${filesystem.DIRECTORY_SEPARATOR}")
      } else if(state.workingDirectory.containFile(directoryName)){
        state.copy(output = s"mkdir: $directoryName: File exists")
      } else if(!isValidDirectoryName(directoryName)){
        state.copy(output = s"mkdir: $directoryName: is invalid name")
      } else {
        createDirectory(directoryName, state)
      }
    }
  }

  private def isValidDirectoryName(name: String): Boolean = !name.contains(".")

  def createDirectory(name: String, state: State): State = {
    def updateStructure(currentDirectory: Directory, path: List[String], newEntry: Directory): Directory = {
      if (path.isEmpty) currentDirectory.addFile(newEntry)
      else {
        val oldEntry = currentDirectory.getChild(path.head).asDirectory
        currentDirectory.replaceFile(oldEntry, updateStructure(oldEntry, path.tail, newEntry))
      }
    }

    val wd = state.workingDirectory

    // 1. all the directories in the full path
    val allDirsInPath = wd.getAllParents

    // 2. create new directory entry in the wd
    val newEntry: Directory = Directory.empty(wd.path, name)

    // 3. update the whole directory structure starting from the root
    // (the directory structure is IMMUTABLE)
    val newRoot = updateStructure(state.root, allDirsInPath, newEntry)

    // 4. find new working directory INSTANCE given wd's full path, in the NEW directory structure
    val newWd = newRoot.findDescendantDirectory(wd)
    state.copy(root = newRoot, workingDirectory = newWd, output = "")
  }
}