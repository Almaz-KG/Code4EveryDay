package filesystem.commands
import filesystem.files.{Directory, File}

class Mkdir(args: Array[String]) extends CreateFile(args) {

  override protected def isValidFileName(name: String): Boolean = !name.contains(".")

  override protected def createEntry(name: String, parent: Directory): File = Directory.empty(parent.path, name)

  override protected def commandName: String = "mkdir"

}