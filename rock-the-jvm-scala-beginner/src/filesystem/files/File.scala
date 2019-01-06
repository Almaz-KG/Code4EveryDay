package filesystem.files

class File(val parent: Directory, val name: String) {

  def getChild(name: String): Option[File] = throw new IllegalStateException("File not contains any children files")

  def path: String = parent.path + filesystem.DIRECTORY_SEPARATOR + name

  def isDirectory: Boolean = false

  def isFile: Boolean = true

  override def toString: String = s"[f] $name"

}
