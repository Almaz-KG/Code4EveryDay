package filesystem.commands
import filesystem.State
import filesystem.files.Directory

import scala.annotation.tailrec

class Mkdir(args: Array[String]) extends Command {

  override def apply(state: State): State = {
    var newState = state

    @tailrec
    def _apply(directoryNames: Array[String], outputs: List[String]): List[String]= {

      if (directoryNames.isEmpty) outputs
      else {
        val currentName = directoryNames.head
        if (currentName.isEmpty)
          outputs :+ "mkdir: missing operand"
        else {
          if (currentName.startsWith(filesystem.DIRECTORY_SEPARATOR))
            outputs :+ s"mkdir: $currentName: Permission denied"
          else if (currentName.contains(filesystem.DIRECTORY_SEPARATOR)) {
            if (canCreateSubDirs) {
              // val folders = currentName.split(filesystem.DIRECTORY_SEPARATOR)
              // TODO: Implement "-p" parameter like a Unix mkdir command
              outputs
            } else {
              outputs :+ s"mkdir: cannot create directory `$currentName`: No such file or directory"
            }
          } else if (state.workingDirectory.containFile(currentName)) {
            outputs :+ s"mkdir: $currentName: File exists"
          } else if (!isValidDirectoryName(currentName)) {
            outputs :+ s"mkdir: $currentName: is invalid name"
          } else {
            newState = createDirectory(currentName, newState)
            _apply(directoryNames.tail, outputs)
          }
        }
      }
    }

    val results = _apply(args, List())
    val message = results.mkString("\n")
    newState.copy(output = message)
  }

  private[this] def canCreateSubDirs: Boolean = args.contains("-p")

  private def isValidDirectoryName(name: String): Boolean = !name.contains(".")

  private def createDirectory(name: String, state: State): State = {
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