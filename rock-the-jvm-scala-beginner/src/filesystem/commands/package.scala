package filesystem

import filesystem.files.root.RootDirectory
import filesystem.files.{Directory, File}

package object commands {

  def findRoot(directory: Directory): Directory = {
    if (directory.isRoot)
      directory
    else
      findRoot(directory.parent)
  }

  /**
    * TODO: Implement this method with functional style
    * TODO: The main idea of this project is practicing development with immutable objects
    *
    * Updating & rebuilding file system structure
    *
    * @param workingDirectory - directory where we start update process
    * @return updated root directory
    */
  def updateFileSystemStructure(workingDirectory: Directory) : Directory = {

    def updateParent(oldParent: Directory, children: Seq[File]): Directory = {
       ???
    }

    def _updateFileSystemStructure(workingDirectory: Directory): Directory = {
      val oldParent: Directory = workingDirectory.parent
      val oldFolder: Directory = oldParent.getChild(workingDirectory.name).asInstanceOf[Directory]

      val newFiles: Seq[File] = oldParent.contents
          .filter(f => f.name != oldFolder.name) :+ workingDirectory

      val directory = updateParent(oldParent, newFiles)
      ???
    }


//    _updateFileSystemStructure(workingDirectory)
    ???
  }


  def findChildFolder(root: Directory, directoryPath: String): Option[Directory] = {
    val sp = filesystem.DIRECTORY_SEPARATOR

    if (directoryPath == RootDirectory.path)
      Some(findRoot(root))
    else if(!directoryPath.contains(sp)) {
      if (root.containFile(directoryPath)) Some(root) else None
    } else {
      val paths = directoryPath.split(sp)
      val str = paths(0)

      if (root.containFile(str)){
        val child = root.getChild(str)
        if (child.isDirectory){
          val path = directoryPath.substring(directoryPath.indexOf(sp) + 1)
          findChildFolder(child.asInstanceOf[Directory], path)
        } else
          None
      } else
        None
    }
  }
}
