package filesystem.files

class Directory(override val parentPath: String,
                override val name: String,
                val contents: List[File]) extends File(parentPath, name) {

}

object Directory {

  val SEPARATOR: String = java.io.File.separator

  val ROOT_PATH: String = SEPARATOR

  val ROOT: Directory = apply("", "")

  def apply(parentPath: String, name: String, contents: List[File] = List()): Directory
      = new Directory(parentPath, name, contents)

  def empty(parentPath: String, name: String): Directory = Directory(parentPath, name)

}
