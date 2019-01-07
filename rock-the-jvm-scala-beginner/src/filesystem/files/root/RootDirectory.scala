package filesystem.files.root

import filesystem.files.{Directory, File}

private[root] object _RootDir extends Directory(parent = null, name = "", contents = Seq()) {
  override def path: String = ""
}

class RootDirectory(children: Seq[File]) extends Directory(parent = _RootDir, name = "", contents = children) {

  override def isRoot: Boolean = true

  override def path: String = filesystem.DIRECTORY_SEPARATOR
}

object RootDirectory {

  def apply(): RootDirectory = new RootDirectory(Seq[File]())

  def path: String = filesystem.DIRECTORY_SEPARATOR
}
