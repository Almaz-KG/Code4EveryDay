package filesystem.commands

import filesystem.State
import filesystem.files.Directory

import scala.annotation.tailrec

class Rm(names: Array[String]) extends Command {

  override def apply(state: State): State = {
    var newState = state
    val wd = state.workingDirectory

    @tailrec
    def _apply(directoryNames: Array[String], outputs: List[String]): List[String]= {

      if (directoryNames.isEmpty) outputs
      else {
        val currentName = directoryNames.head
        if (currentName.isEmpty)
          _apply(directoryNames.tail, outputs :+ s"rm: missing operand")
        else {
          if (currentName.startsWith(filesystem.DIRECTORY_SEPARATOR))
            _apply(directoryNames.tail, outputs :+ s"rm: $currentName: Permission denied")
          else if (!state.workingDirectory.containFile(currentName)) {
            _apply(directoryNames.tail, outputs :+ s"rm: cannot remove `$currentName`: No such file or directory")
          } else {
            val absolutePath =
              if (currentName.startsWith(filesystem.DIRECTORY_SEPARATOR)) currentName
              else if (wd.isRoot) wd.path + currentName
              else wd.path + filesystem.DIRECTORY_SEPARATOR + currentName

            newState = removeFile(absolutePath, state)
            _apply(directoryNames.tail, outputs)
          }
        }
      }
    }

    val results = _apply(names, List())
    val message = results.mkString("\n")
    newState.copy(output = message)
  }

  private[this] def removeFile(path: String, state: State): State = {

    def updateStructure(currentDirectory: Directory, path: List[String]): Directory = {
      if (path.isEmpty) currentDirectory
      else if (path.tail.isEmpty) currentDirectory.removeFileIfExists(path.head)
      else {
        val nextDirectory = currentDirectory.getChild(path.head)
        if (!nextDirectory.isDirectory) currentDirectory
        else {
          val newNextDirectory = updateStructure(nextDirectory.asDirectory, path.tail)
          if (newNextDirectory == nextDirectory) currentDirectory
          else {
            currentDirectory.replaceFile(nextDirectory, newNextDirectory)
          }
        }
      }
    }

    val tokens = path.substring(1).split(filesystem.DIRECTORY_SEPARATOR).toList
    val newRoot: Directory = updateStructure(state.root, tokens)

    if (newRoot == state.root)
      state.setMessage(path + ": no such file or directory")
    else {
      State(newRoot, newRoot.findDescendantDirectory(state.workingDirectory), "", isRunning = true)
    }
  }
}
