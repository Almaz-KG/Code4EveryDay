package filesystem.files.root

import filesystem.files.{Directory, File}

private[root] object _RootDir extends Directory(parent = null, name = "", contents = Seq()) {
  override def path: String = ""
}

object RootDirectory extends Directory(parent = _RootDir, name = "", contents = Seq()) {
}
