package filesystem.commands

import filesystem.files.{Directory, File}

class Touch(names: Array[String]) extends CreateFile(names) {

  override protected def isValidFileName(name: String): Boolean = !name.contains(filesystem.DIRECTORY_SEPARATOR)

  override protected def createEntry(name: String, parent: Directory): File = new File(parent.path, name)

  override protected def commandName: String = "touch"
}
