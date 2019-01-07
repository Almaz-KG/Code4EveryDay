package filesystem.files


class Directory(override val parent: Directory,
                override val name: String,
                var contents: Seq[File]) extends File(parent, name) {

  def isRoot: Boolean = false

  override def isDirectory: Boolean = true

  override def isFile: Boolean = false

  override def getChildOption(name: String): Option[File] = {
    if (!containFile(name)) None
    else contents
      .find(n => name == n.name)
  }

  override def getChild(name: String): File = {
    if (!containFile(name)) throw new IllegalArgumentException(s"Child with name $name not found")
    else contents.find(n => name == n.name).get
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
    result = prime * result + (if (parent == null) 0 else parent.hashCode)
    result = prime * result + (if (name == null) 0 else name.hashCode)
    result = prime * result + (if (contents == null) 0 else contents.hashCode)
    result
  }
}

object Directory {

  def apply(parent: Directory, name: String, contents: Seq[File] = Seq()): Directory = {
      new Directory(parent, name, contents)
  }

  def empty(directory: Directory, name: String): Directory = Directory(directory, name)

}
