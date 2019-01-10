package filesystem

import filesystem.files.{Directory, File}

package object commands {

  def updateFileSystemStructure(currentDirectory: Directory,
                                path: List[Directory],
                                newFile: File) : Directory = {
    if (path.isEmpty) currentDirectory.addFile(newFile)
    else {
      val oldFile = currentDirectory.getChild(path.head.name)
      currentDirectory.replaceFile(oldFile,
        updateFileSystemStructure(oldFile.asInstanceOf[Directory], path.tail, newFile))
    }
  }
}
