package hw

import sbt._

trait SxtPlugin extends Project {
  
  lazy val hello = task { log.info("Hello World!"); None }
  
}
