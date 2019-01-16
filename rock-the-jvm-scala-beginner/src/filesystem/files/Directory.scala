package filesystem.files

class Directory(override val parentPath: String,
                override val name: String,
                val contents: Seq[File]) extends File(parentPath, name) {
  def isRoot: Boolean = parentPath.isEmpty

  override def isDirectory: Boolean = true

  override def isFile: Boolean = false

  override def asDirectory: Directory = this

  def getChildOption(name: String): Option[File] = {
    if (!containFile(name)) None
    else contents
      .find(n => name == n.name)
  }

  def getChild(name: String): File = {
    if (!containFile(name)) throw new IllegalArgumentException(s"Child with name $name not found")
    else contents.find(n => name == n.name).get
  }

  def getAllParents: List[String] = if (path.substring(1).isEmpty) List() else
      path.substring(1)
      .split(filesystem.DIRECTORY_SEPARATOR)
      .toList.filter(x => !x.isEmpty)

  def replaceFile(oldFile: File, newFile: File): Directory = {
    val newFiles = contents
      .filter(f => !(f.name == oldFile.name && f.path == oldFile.path)) :+ newFile

    new Directory(parentPath, name, newFiles)
  }

  def addFile(newFile: File) : Directory = {
    new Directory(parentPath, name, contents :+ newFile)
  }

  def addOrReplaceFile(newFile: File) : Directory = {
    if (containFile(newFile.name))
      replaceFile(getChild(newFile.name), newFile)
    else
      new Directory(parentPath, name, contents :+ newFile)
  }

  def removeFileIfExists(fileName: String) : Directory = {
    if (containFile(fileName)){
      val acceptedFiles = contents.filter(f => f.name != fileName)
      new Directory(parentPath, name, acceptedFiles)
    } else this
  }

  def findDescendantDirectory(path: List[String]): Directory = {
    if (path.isEmpty) this
    else getChild(path.head).asDirectory.findDescendantDirectory(path.tail)
  }

  def findDescendantDirectory(folder: Directory): Directory = {
    val path = folder.getAllParents

    findDescendantDirectory(path)
  }

  def containFile(name: String): Boolean = {
    contents.map(f => f.name).contains(name)
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[Directory]

  override def toString: String = s"[d] $name"

  override def equals(that: Any): Boolean =
    that match {
      case that: Directory => that.canEqual(this) && this.hashCode == that.hashCode
      case _ => false
    }

  override def hashCode: Int = {
    val prime = 31
    var result = 1
    result = prime * result + (if (parentPath == null) 0 else parentPath.hashCode)
    result = prime * result + (if (name == null) 0 else name.hashCode)
    result = prime * result + (if (contents == null) 0 else contents.hashCode)
    result
  }
}

object Directory {

  def ROOT: Directory = Directory.empty("", "")

  def apply(parentPath: String, name: String, contents: Seq[File] = Seq()): Directory = {
      new Directory(parentPath, name, contents)
  }

  def empty(parentPath: String, name: String): Directory = Directory(parentPath, name)

}
