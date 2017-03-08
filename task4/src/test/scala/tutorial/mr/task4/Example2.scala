package tutorial.mr.task4

import org.scalatest.junit.AssertionsForJUnit
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.apache.spark.graphx._
import org.apache.spark.graphx.util.GraphGenerators
import org.apache.spark.graphx.lib.ShortestPaths
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

import tutorial.mr.task4.embeddedspark._

class Example2 extends AssertionsForJUnit {
  var logger = LoggerFactory.getLogger(getClass)
  
  @Test def randomGraph() {
    using(new SparkContext(new SparkConf().setAppName("embedded").setMaster("local[2]"))) { sc =>
      var graph = GraphGenerators.logNormalGraph(sc, numVertices = 100, mu = 0.5, sigma = 0.5)
      
      logger.info("")
      logger.info("==> LogNormal graph <==")
      var vertices = graph.vertices.count()
      logger.info(s"Number of vertices: ${vertices}")
      var edges = graph.edges.count()
      logger.info(s"Number of edges: ${edges}")
      val ids = graph.vertices.map( _._1).collect().toSeq
      val sh = ShortestPaths.run(graph, ids).vertices.first
      logger.info(s"${sh}")
    }
  } 
}