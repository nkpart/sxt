import sbt._

class SxtPluginProject(info: ProjectInfo) extends PluginProject(info) {
  val specs = "org.specs" % "specs" % "1.6"
}
