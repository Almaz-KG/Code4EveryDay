package filesystem.commands
import filesystem.State
import filesystem.files.{Directory, File}

import scala.annotation.tailrec

abstract class CreateFile(args: Array[String]) extends Command {

  override def apply(state: State): State = {
    var newState = state

    @tailrec
    def _apply(directoryNames: Array[String], outputs: List[String]): List[String]= {

      if (directoryNames.isEmpty) outputs
      else {
        val currentName = directoryNames.head
        if (currentName.isEmpty)
          _apply(directoryNames.tail, outputs :+ s"$commandName: missing operand")
        else {
          if (currentName.startsWith(filesystem.DIRECTORY_SEPARATOR))
            _apply(directoryNames.tail, outputs :+ s"$commandName: $currentName: Permission denied")
          else if (currentName.contains(filesystem.DIRECTORY_SEPARATOR)) {
              _apply(directoryNames.tail, outputs :+ s"$commandName: cannot create directory `$currentName`: No such file or directory")
          } else if (state.workingDirectory.containFile(currentName)) {
            _apply(directoryNames.tail, outputs :+ s"$commandName: $currentName: File exists")
          } else if (!isValidFileName(currentName)) {
            _apply(directoryNames.tail, outputs :+ s"$commandName: $currentName: is invalid name")
          } else {
            newState = createFile(currentName, newState)
            _apply(directoryNames.tail, outputs)
          }
        }
      }
    }

    val results = _apply(args, List())
    val message = results.mkString("\n")
    newState.copy(output = message)
  }

  private[this] def createFile(name: String, state: State): State = {
    def updateStructure(currentDirectory: Directory, path: List[String], newEntry: File): Directory = {
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
    val newEntry: File = createEntry(name, wd)

    // 3. update the whole directory structure starting from the root
    // (the directory structure is IMMUTABLE)
    val newRoot = updateStructure(state.root, allDirsInPath, newEntry)

    // 4. find new working directory INSTANCE given wd's full path, in the NEW directory structure
    val newWd = newRoot.findDescendantDirectory(wd)
    state.copy(root = newRoot, workingDirectory = newWd, output = "")
  }

  protected def isValidFileName(name: String): Boolean

  protected def createEntry(name: String, parent: Directory): File

  protected def commandName: String
}
