package sxt

import sbt._
import scala.io._

trait TemplateFile {
  val app : String
  val group : String
  val name : String
  
  def readContents : Iterator[String]
  
  def buildMethod : Option[String] = Sxt.makeMethod(name, readContents.toList)
  
  override def toString = "Template(%s, %s, %s)" format (app,group,name)
}

object TemplateFile {
  def from(base : Path, path : String) = {
    parseAppGroupName(path) map { tr =>
      new TemplateFile {
        val app = tr._1
        val group = tr._2
        val name = tr._3.replace(".sxt", "")
        def readContents = {
          Source.fromFile(base.absolutePath + "/" + path).getLines
        }
      }
    }
  }
  
  private def parseAppGroupName(path : String) : Option[(String,String,String)] = {
    val parts = (path split "/")
    if (parts.length == 3) { Some((parts(0), parts(1), parts(2))) } else None
  }
}
