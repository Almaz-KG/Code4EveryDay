package filesystem.commands

import filesystem.State
import filesystem.files.{Directory, File}

class Echo(args: Array[String]) extends Command {

  override def apply(state: State): State = {
    if (args.isEmpty)
      state
    else if (isValidRedirection) {
      val overwriteMode = isOverwriteMode
      val fileName = args(redirectionIndex + 1)
      val content = args.slice(0, redirectionIndex)

      val newState = writeToFile(state, fileName, content, overwriteMode)

      newState
    } else if (args(args.length - 1) == ">" || args(args.length - 1) == ">>") {
      state.setMessage("echo: syntax error near unexpected token `newline'")
    } else {
      state.setMessage(args.mkString(" "))
    }
  }

  private def createOrUpdateFile(workingDirectory: Directory, fileName: String,
                                 content: Array[String], overwriteMode: Boolean): File = {
    if (workingDirectory.containFile(fileName)) {
      if (overwriteMode) {
        new File(workingDirectory.path, fileName, content.mkString(" "))
      } else {
        val oldFile = workingDirectory.getChild(fileName)
        val newContent = oldFile.content + content.mkString(" ")
        new File(workingDirectory.path, fileName, newContent)
      }
    } else {
      new File(workingDirectory.path, fileName, content.mkString(" "))
    }
  }

  private def writeToFile(state: State, fileName: String, content: Array[String], overwriteMode: Boolean): State = {
    def updateStructure(currentDirectory: Directory, path: List[String], newEntry: File): Directory = {
      if (path.isEmpty) currentDirectory.addOrReplaceFile(newEntry)
      else {
        val oldEntry = currentDirectory.getChild(path.head).asDirectory
        currentDirectory.replaceFile(oldEntry, updateStructure(oldEntry, path.tail, newEntry))
      }
    }

    if (fileName.startsWith(filesystem.DIRECTORY_SEPARATOR))
      state.setMessage(s"echo: `$fileName`: Permission denied")
    else {
      val wd = state.workingDirectory
      // 1. all the directories in the full path
      val allDirsInPath = wd.getAllParents

      // 2. create new directory entry in the wd
      val newEntry: File = createOrUpdateFile(wd, fileName, content, overwriteMode)

      // 3. update the whole directory structure starting from the root
      // (the directory structure is IMMUTABLE)
      val newRoot = updateStructure(state.root, allDirsInPath, newEntry)

      // 4. find new working directory INSTANCE given wd's full path, in the NEW directory structure
      val newWd = newRoot.findDescendantDirectory(wd)
      state.copy(root = newRoot, workingDirectory = newWd, output = "")
    }
  }

  private def redirectionIndex: Int = if (args.indexOf(">") != -1) args.indexOf(">") else args.indexOf(">>")

  private def isValidRedirection: Boolean = redirectionIndex != -1 && (args(args.length - 1) != ">" && args(args.length - 1) != ">>")

  private def isOverwriteMode: Boolean = args.contains(">")

}
