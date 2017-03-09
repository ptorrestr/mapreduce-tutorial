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
      val diam = ShortestPaths.run(graph, ids)
        .vertices
        .mapValues(_.values.max)
        .collect()
        .reduce( (t,u) => if (t._2 > u._2) t else u)
      logger.info(s"Diameter: ${diam._2}")
      
      logger.info(s"")
      logger.info(s"==> Join <==")
      val x = sc.parallelize(Seq( (3, 3), (4, 4) ))
      val y = sc.parallelize(Seq( (3, 5), (4, 7) ))
      val z = x.join(y).collect()
      z.foreach(t => logger.info(s"${t._1}"))
    }
  } 
}