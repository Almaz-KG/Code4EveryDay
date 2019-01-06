package filesystem.commands
import filesystem.State
import filesystem.files.{Directory, File}

class Mkdir(directories: Array[String]) extends Command {

  override def apply(state: State): State = {
    if (directories.isEmpty)
      State(state.root, state.workingDirectory, output = "usage mkdir [directories]")
    else {
      val workingDirectory = state.workingDirectory

      val acceptedDirs: Seq[Directory] = directories
        .filter(name => !workingDirectory.containFile(name))
        .map(name => buildDirectory(workingDirectory, name))

      val files: Seq[File] = state.workingDirectory.contents ++ acceptedDirs
      val newWd = Directory(workingDirectory.parent, workingDirectory.name, files)

      val existsDirs = directories
        .filter(name => workingDirectory.containFile(name))

      val output: String = existsDirs
        .map(name => s"mkdir: $name: File exists")
        .mkString("\n")

      State(state.root, newWd, output)
    }
  }

  private def buildDirectory(root: Directory, name: String): Directory = {
    if (name.contains(filesystem.DIRECTORY_SEPARATOR)){
      val dirNames = name.split(filesystem.DIRECTORY_SEPARATOR)

      val parent: Directory = Directory(root, dirNames(0))
      val childName = name.substring(name.indexOf(filesystem.DIRECTORY_SEPARATOR) + 1)
      val child: Directory = buildDirectory(parent, childName)

      Directory(parent.parent, parent.name, Seq(child))
    } else {
      Directory.empty(root, name)
    }
  }
}
