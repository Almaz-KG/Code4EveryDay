package filesystem.files

class File(val parentPath: String, val name: String) {

  def getChildOption(name: String): Option[File] = throw new IllegalStateException("File not contains any children files")

  def getChild(name: String): File = throw new IllegalStateException("File not contains any children files")

  def path: String = {
    val prefix = if (filesystem.DIRECTORY_SEPARATOR == parentPath) "" else parentPath

    prefix + filesystem.DIRECTORY_SEPARATOR + name
  }

  def isDirectory: Boolean = false

  def isFile: Boolean = true

  def asDirectory: Directory = throw new IllegalStateException("File cannot be converted into Directory")

  override def toString: String = s"[f] $name"

}
