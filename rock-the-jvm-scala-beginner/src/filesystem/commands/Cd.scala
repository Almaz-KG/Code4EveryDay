package filesystem.commands
import filesystem.State
import filesystem.files.Directory

import scala.annotation.tailrec

class Cd(dir: String) extends Command {

  override def apply(state: State): State = {
    val wd = state.workingDirectory

    val absolutePath =
      if (dir.startsWith(filesystem.DIRECTORY_SEPARATOR)) dir
      else if (wd.isRoot) wd.path + dir
      else wd.path + filesystem.DIRECTORY_SEPARATOR + dir

    // 3. find the directory to cd to, given the path
    val destinationDirectory = changeDir(state.root, absolutePath)

    // 4. change the state given the new directory
    if (destinationDirectory == null || !destinationDirectory.isDirectory)
      state.setMessage(s"cd: $dir: no such directory")
    else
      state.copy(workingDirectory = destinationDirectory.asDirectory, output = "")
  }

  private def changeDir(root: Directory, path: String): Directory = {
    @tailrec
    def collapseRelativeTokens(path: List[String], result: List[String]): List[String] = {
      if (path.isEmpty) result
      else if (".".equals(path.head)) collapseRelativeTokens(path.tail, result)
      else if ("..".equals(path.head)) {
        if (result.isEmpty) null
        else collapseRelativeTokens(path.tail, result.init)
      } else collapseRelativeTokens(path.tail, result :+ path.head)
    }

    val tokens: List[String] = path.substring(1).split(filesystem.DIRECTORY_SEPARATOR).toList
    val tokensCollapsed = collapseRelativeTokens(tokens, List())

    @tailrec
    def _changeDir(dir: Directory, directories: List[String]): Directory = {
      if (directories.isEmpty || directories.head.isEmpty) dir
      else if (directories.tail.isEmpty) {
        dir
          .getChildOption(directories.head)
          .filter(f => f.isDirectory)
          .map(f => f.asDirectory)
          .orNull
      } else {
        val nextDirOption = dir.getChildOption(directories.head)
          .filter(f => f.isDirectory)
          .map(f => f.asDirectory)

        if (nextDirOption.isEmpty) null
        else _changeDir(nextDirOption.get, directories.tail)
      }
    }
    if (tokensCollapsed == null) null
    else _changeDir(root, tokensCollapsed)
  }
}
