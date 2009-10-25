package sxt

import Function._
import sbt._
import scala.io._

trait SxtPlugin extends Project {
  self : MavenStyleScalaPaths =>
  lazy val mainTemplatePath = mainSourcePath / "sxt"
  lazy val templatePath = mainTemplatePath ** "*.sxt"
  lazy val outputSxtScalaPath = path("src_managed") / "main" / "scala"
  // Included above the object definition, used for import definitions
  lazy val sxtFileHead = ""  
  
  lazy val compileSxt = task { 
    val templatePaths = templatePath.get // foreach println
    val prefix = mainTemplatePath.projectRelativePath + "/"
    
    val templates : Iterable[TemplateFile] = templatePaths map { path => 
      stripPrefix(path.projectRelativePath, prefix) 
      } flatMap (TemplateFile.from(mainTemplatePath, _))
    
    val byAppAndGroup = Utils.groupBy(templates, ((x : TemplateFile) => x.app)) mapElements (forApp => {
      Utils.groupBy(forApp, ((x : TemplateFile) => x.group))
    })
    
    byAppAndGroup foreach tupled(commitApp)
    
    None
  }
  
  def buildFile(packageBase : String, objectName : String, content : String) = """
package %s.sxt

import scala.xml._
%s

object %s {
%s
}
  """ format (packageBase, sxtFileHead, objectName, content)
   	   
  def commitGroup(appDir : Path, app : String, group : String, files : List[TemplateFile]) {
    (appDir / "sxt").asFile.mkdirs
    val groupFile = ((appDir / "sxt") / (group + ".scala")).asFile
    val groupMethods = files flatMap (_.buildMethod) reduceLeft (_ + _)
    
    val groupFileContents = buildFile(app, group, groupMethods)
    
    val fw = new java.io.FileWriter(groupFile)
    fw.write(groupFileContents)
    fw.close()
  }
  
  def commitApp(app : String, groups : Map[String, List[TemplateFile]]) {
    log.info("Generating app: %s" format app)
    val appDir = outputSxtScalaPath / app
    appDir.asFile.mkdirs
    groups foreach tupled(commitGroup(appDir,app, _, _))
  }
  
  
  def stripPrefix(s : String, prefix: String) =
  	    if (s.startsWith(prefix)) s.substring(prefix.length)
  	    else s
  
  	/** Returns this string with the given <code>suffix</code> stripped. */
  def stripSuffix(s : String, suffix: String) =
  	    if (s.endsWith(suffix)) s.substring(0, s.length() - suffix.length)
  	    else s
}
