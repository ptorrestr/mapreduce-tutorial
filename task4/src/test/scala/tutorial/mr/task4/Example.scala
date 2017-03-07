package tutorial.mr.task4

import org.scalatest.junit.AssertionsForJUnit
import scala.collection.mutable.ListBuffer
import org.junit.Test
import org.junit.Assert._

import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import resource._

import tutorial.mr.task4.embeddedspark._

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
  
  @Test def basicGraph() {
    val nodes = Array(
        (1L, ("Alice", 28)),
        (2L, ("Bob", 27)),
        (3L, ("Charlie", 65)),
        (4L, ("David", 42)),
        (5L, ("Ed", 55)),
        (6L, ("Fran", 50))
      )
    val edges = Array(
        Edge(2L, 1L, 7),
        Edge(2L, 4L, 2),
        Edge(3L, 2L, 4),
        Edge(3L, 6L, 3),
        Edge(4L, 1L, 1),
        Edge(5L, 2L, 2),
        Edge(5L, 3L, 8),
        Edge(5L, 6L, 3)
      )
      
    using(new SparkContext(new SparkConf().setAppName("embedded").setMaster("local[2]"))) { sc =>
        val vertexRDD: RDD[(Long, (String, Int))] = sc.parallelize(nodes)
        val edgeRDD: RDD[Edge[Int]] = sc.parallelize(edges)
    }
  }
}