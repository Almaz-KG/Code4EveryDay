package filesystem.commands
import filesystem.State
import filesystem.files.Directory
import filesystem.files.root.RootDirectory

class Cd(directory: String) extends Command {

  override def apply(state: State): State = {
    val wd = state.workingDirectory
    apply(wd: Directory, directory.trim)
  }

  private def apply(wd: Directory, dir: String): State = {
    directory.trim match {
      case "/" => State(findRoot(wd))
      case x: String =>
        if (x.contains(filesystem.DIRECTORY_SEPARATOR)){
          val args = x.split(filesystem.DIRECTORY_SEPARATOR)
          val others = x.substring(x.indexOf(filesystem.DIRECTORY_SEPARATOR) + 1)

          val state: State = changeDir(wd, args(0))

          new Cd(others)(state)
        } else {
          changeDir(wd, x)
        }
    }
  }

  private def changeDir(wd: Directory, dir: String): State = {
    if ("." == dir.trim) {
      State(wd)
    } else if (".." == dir.trim ||  "../" == dir.trim) {
      if (wd.isRoot)
        State(wd)
      else
        State(wd.parent)
    } else if (!wd.containFile(dir)) {
      State(wd, s"cd: $dir: No such file of directory")
    } else {
      val child = wd.getChildOption(dir)
      if (child.isEmpty)
        State(wd, s"cd: $dir: No such file of directory")
      else {
        if(child.get.isDirectory) {
          State(child.get.asInstanceOf[Directory])
        }
        else
          State(wd, s"cd: $dir: Not a directory")
      }
    }
  }
}
