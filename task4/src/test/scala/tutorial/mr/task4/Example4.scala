package tutorial.mr.task4

import org.scalatest.junit.AssertionsForJUnit
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.apache.spark.graphx._
import org.apache.spark.graphx.lib.ShortestPaths
import org.apache.spark.rdd.RDD
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

import com.centrality.kBC.KBetweenness
import tutorial.mr.task4.embeddedspark._

class Example4 extends AssertionsForJUnit {
  var logger = LoggerFactory.getLogger(getClass)
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
      
  @Test def betweenness() {
    using(new SparkContext(new SparkConf().setAppName("embedded").setMaster("local[2]"))) { sc =>
      val vertexRDD: RDD[(Long, (String, Int))] = sc.parallelize(nodes)
      val edgeRDD: RDD[Edge[Int]] = sc.parallelize(edges)
      val graph: Graph[(String, Int), Int] = Graph(vertexRDD, edgeRDD)
      
      logger.info ("")
      logger.info ("==> Betweenness per node <==")
      val kBCGraph = KBetweenness.run(graph, 10)
      kBCGraph.vertices.collect.foreach {
        case (id, betweenness) => logger.info(s"${id} has ${betweenness}")
      }
      
      logger.info ("")
      logger.info ("==> Betweenness per edge (Approximation) <==")
      kBCGraph.triplets.collect.foreach {
        case e => logger.info(s"Edge ${e.srcId}->${e.dstId} has ${e.dstAttr + e.srcAttr} ")
      }
    }
  }
  
  @Test def connected() {
    using(new SparkContext(new SparkConf().setAppName("embedded").setMaster("local[2]"))) { sc =>
      val vertexRDD: RDD[(Long, (String, Int))] = sc.parallelize(nodes)
      val edgeRDD: RDD[Edge[Int]] = sc.parallelize(edges)
      val graph: Graph[(String, Int), Int] = Graph(vertexRDD, edgeRDD)
      
      // Since we know that edges 3->2 and 5->2 are the highest one, we remove them
      val removeEdges: List[(VertexId, VertexId)] = List((3,2),(5,2))
      val fGraph = graph.subgraph(e => ! removeEdges.contains(e.srcId,e.dstId) , (_,_) => true)
      
      // Once remove, call connected components
      val out = fGraph.connectedComponents()
      logger.info ("")
      logger.info ("==> Connected components <==")
      out.vertices.collect.foreach { 
        case (id, componentId) => logger.info(s"${id} is memeber of component ${componentId}")
      }
      
      // Count the number of components
      logger.info ("==> Total components <==")
      val total = out.vertices.map(_._2).distinct().count()
      logger.info(s"${total}")
    }
  }
}