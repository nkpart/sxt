package sxt

import java.io.File

object Utils {
  def recurse(aStartingDir : File) : List[File] = {
    val filesAndDirs : List[File] = List(aStartingDir.listFiles() :_*);
    filesAndDirs ++ (filesAndDirs filter (!_.isFile) flatMap (recurse _))
  }
  
  def groupBy[T, K](list : Iterable[T], keyF : (T => K)) : Map[K, List[T]] = {
    list.foldLeft(Map[K, List[T]]()) { (m, t) => 
      val key = keyF(t)
      m + (key -> (t :: (m.get(key) getOrElse Nil)))
    }
  }
  
}