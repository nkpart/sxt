import sbt._

class SxtPluginProject(info: ProjectInfo) extends PluginProject(info) {
  val specs = "org.scala-tools.testing" % "specs" % "1.6.0"
}
