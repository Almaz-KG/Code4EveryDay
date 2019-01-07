package filesystem.commands
import filesystem.State
import filesystem.files.{Directory, File}

class Mkdir(directories: Array[String]) extends Command {

  override def apply(state: State): State = {
    if (directories.isEmpty)
      State(state.workingDirectory, output = "usage mkdir [directories]")
    else {
      val workingDirectory = state.workingDirectory

      val existsDirs = directories
        .filter(name => workingDirectory.containFile(name))

      directories
        .filter(name => !workingDirectory.containFile(name))
        .foreach(name => buildDirectory(workingDirectory, name))

      val output: String = existsDirs
        .map(name => s"mkdir: $name: File exists")
        .mkString("\n")

      State(state.workingDirectory, output)
    }
  }

  private def buildDirectory(root: Directory, name: String): Unit = {
    if (name.contains(filesystem.DIRECTORY_SEPARATOR)){
      val dirNames = name.split(filesystem.DIRECTORY_SEPARATOR)

      if (root.containFile(dirNames(0))){
        val folder = root.getChild(dirNames(0))

        if(folder.isDirectory){
          val children = name.substring(name.indexOf(filesystem.DIRECTORY_SEPARATOR) + 1)
          buildDirectory(folder.asInstanceOf[Directory], children)
        }
      } else {
        val parent = Directory(root, dirNames(0))
        root.contents = root.contents :+ parent
        val childName = name.substring(name.indexOf(filesystem.DIRECTORY_SEPARATOR) + 1)
        buildDirectory(parent, childName)
      }
    } else {
      if (!root.containFile(name)){
        root.contents = root.contents :+ Directory(root, name)
      }
    }
  }
}
