package tutorial.mr.task4

import org.scalatest.junit.AssertionsForJUnit
import scala.collection.mutable.ListBuffer
import org.junit.Test
import org.junit.Assert._

class Example extends AssertionsForJUnit {
  
  @Test def verifyEasy() { // Uses JUnit-style assertions
    var sb = new StringBuilder("ScalaTest is ")
    var lb = new ListBuffer[String]
    sb.append("easy!")
    assertEquals("ScalaTest is easy!", sb.toString)
    assertTrue(lb.isEmpty)
    lb += "sweet"
    try {
      "verbose".charAt(-1)
      fail()
    }
    catch {
      case e: StringIndexOutOfBoundsException => // Expected
    }
  }
}