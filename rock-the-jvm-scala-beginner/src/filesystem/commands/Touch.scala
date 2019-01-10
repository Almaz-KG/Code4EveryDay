package filesystem.commands
import filesystem.State
import filesystem.files.{Directory, File}

class Touch(names: Array[String]) extends Command {

  override def apply(state: State): State = { ???

//    val wd: Directory = state.workingDirectory
//
//    val newFiles = names
//        .filter(n => !n.contains(filesystem.DIRECTORY_SEPARATOR))
//        .filter(n => {!wd.getChildOption(n).exists(c => c.isDirectory)})
//        .map(n => new File(wd, n))
//
//    val oldFilesFiltered: Seq[File] = wd
//      .contents
//      .filter(f => !newFiles.map(nf => nf.name).contains(f.name))

//    wd.contents = newFiles ++ oldFilesFiltered

//    State(wd)
  }
}
