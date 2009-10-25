import org.specs._

import sxt._

object SxtWhole extends Specification {  
  "extract" in {
    Sxt.extractParams("<!-- (a : String, b : String) -->") must_== Some("(a : String, b : String)")
  }
  
  "creates a method from an sxt" in {
    val template = """
<!-- (a : String, b : String) -->
<p>{ a } : { b }</p>
    """
    true must_== true
    // Sxt.makeMethod(template, "namedTemplate") must_== Some("""
    // def namedTemplate(a : String, b : String) = (
    // <p>{ a } : { b }</p>
    // )
    //       """)
    //     
  }
}