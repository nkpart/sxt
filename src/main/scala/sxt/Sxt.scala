package sxt

object Sxt {  
  import java.io.File
  
  def extractParams(s : String) : Option[String] = {
    ("""<\!-- (.*) -->""".r findFirstMatchIn s) map (_.group(1))
  }
    
  def makeMethod(name : String, templateContent : List[String]) : Option[String] = {
    templateContent match {
      case Nil => None
      case (definition :: content) => {
        extractParams(definition) map (ps => 
          """
    def %s%s = (
%s)
          """ format (name, ps, content.reduceLeft(_ + _)))
      }
    }
  }
}