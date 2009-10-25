# Scala XML Templates

This is an attempt to clean up 'view' methods in scala (functions of type T => NodeSeq, T => Elem, etc.) into separate files. Why? I'm not yet really sure, but it feels better to have this stuff out in separate files. Like Lift's templates, or Rails partials/templates.

# How to use it.

Assuming you're already using Simple-Built-Tool for a project, clone this repository.

From within that clone:
    $ sbt publish-local

In your sbt project, create 'Plugins.scala' in project/plugins.

    class Plugins(info: ProjectInfo) extends PluginDefinition(info) {
      val a = "enkp" % "sxt" % "1.0"
    }
    
Modify your project file so that it include the SxtPlugin trait.

    import sxt._
    class SampleProject(info : ProjectInfo) extends DefaultProject(info) with SxtPlugin {
       ....
    }


TODO: how/what to override.
* lazy val sxtFileHead = "" // Add additional imports that your templates need, here.

# SXT File/Folder Structure

The plugin expects templates to be found in the following location:

    ROOT/src/main/sxt/{application}/{action-group}/{action}.sxt
  
Each of these actions will be grouped up, and the result written out to:

    ROOT/src_managed/main/scala/{application}/{action-group}.scala
    
Each action will be a method on an object named 'action-group'.

# SXT Template

The first line of an SXT template specifies the names and types of inputs:

    <!-- (heading : String, values : List[Double])(implicit messages : Map[Symbol, String]) -->
    
This is exactly the same as a the parameter definition for a method, and may include an implicit block.

Any lines after the first one, are assumed to be part of the method body, and must be valid scala syntax. Typically, it's expected that this will be inline XML - but it could be anything.

    <h3>{ heading }</h3>
    <div>{ values map (v => v + "%") reduceLeft (_ + ", " + _) }</div>
    <div>{ messages('copyright) }</div>

Assuming this template resided in `src/main/sxt/science/model/index.sxt`, we would end up with:

    object model {
      def index(heading : String, values : List[Double])(implicit messages : Map[Symbol, String]) = (
        <h3>{ heading }</h3>
        <div>{ values map (v => v + "%") reduceLeft (_ + ", " + _) }</div>
        <div>{ messages('copyright) }</div>      
      )
    }

# License

(It's the MIT license.)

Copyright (c) 2009 Nick Partridge

Permission is hereby granted, free of charge, to any person
obtaining a copy of this software and associated documentation
files (the "Software"), to deal in the Software without
restriction, including without limitation the rights to use,
copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the
Software is furnished to do so, subject to the following
conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.

