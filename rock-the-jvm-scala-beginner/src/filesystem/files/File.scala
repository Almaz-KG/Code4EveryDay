package filesystem.files

class File(val parent: Directory, val name: String) {

  def getChildOption(name: String): Option[File] = throw new IllegalStateException("File not contains any children files")

  def getChild(name: String): File = throw new IllegalStateException("File not contains any children files")

  def path: String = {
    val prefix = if (filesystem.DIRECTORY_SEPARATOR == parent.path) "" else parent.path

    prefix + filesystem.DIRECTORY_SEPARATOR + name
  }

  def isDirectory: Boolean = false

  def isFile: Boolean = true

  override def toString: String = s"[f] $name"

}
