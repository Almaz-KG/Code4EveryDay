package filesystem

import filesystem.files.root.RootDirectory
import filesystem.files.{Directory, File}

import scala.annotation.tailrec

package object commands {

  @tailrec
  def findRoot(directory: Directory): Directory = {
    if (directory.isRoot)
      directory
    else
      findRoot(directory.parent)
  }

  def updateFileSystemStructure(currentDirectory: Directory,
                                path: List[String],
                                newFile: File) : Directory = {
    if (path.isEmpty) currentDirectory.addFile(newFile)
    else {
      val oldFile = currentDirectory.getChild(path.head)

      currentDirectory.replaceFile(oldFile,
        updateFileSystemStructure(oldFile.asInstanceOf[Directory], path.tail, newFile))
    }
  }


  def getAllFolders(directory: Directory): List[Directory] = {
//    directory.path.substring(1).split(filesystem.DIRECTORY_SEPARATOR).toList

    @tailrec
    def getAllFolders(directory: Directory, accumulator: List[Directory]) : List[Directory] = {
      if (directory.isRoot)
        accumulator
      else
        getAllFolders(directory.parent, accumulator :+ directory)
    }

    getAllFolders(directory, List())
  }

  @tailrec
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
