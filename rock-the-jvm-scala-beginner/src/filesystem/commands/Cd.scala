package filesystem.commands
import filesystem.State
import filesystem.files.Directory
import filesystem.files.root.RootDirectory

class Cd(directory: String) extends Command {

  override def apply(state: State): State = {
    val wd = state.workingDirectory


    if ("." == directory)
      state
    else if (".." == directory.trim){
      if(state.workingDirectory == RootDirectory)
        state
      else
        State(state.root.parent, state.root)
    } else if (!wd.containFile(directory))
      State(state.root, wd, s"cd: $directory: No such file of directory")
    else {
      val child = wd.getChild(directory)
      if (child.isEmpty)
        State(state.root, wd, s"cd: $directory: No such file of directory")
      else {
        if(child.get.isDirectory)
          State(state.workingDirectory, child.get.asInstanceOf[Directory])
        else
          State(state.root, wd, s"cd: $directory: Not a directory")
      }
    }
  }
}
