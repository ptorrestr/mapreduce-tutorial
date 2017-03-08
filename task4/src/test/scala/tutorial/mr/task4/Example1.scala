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

import tutorial.mr.task4.embeddedspark._

class Example1 extends AssertionsForJUnit {
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
  
  @Test def basicGraph() {
    using(new SparkContext(new SparkConf().setAppName("embedded").setMaster("local[2]"))) { sc =>
      val vertexRDD: RDD[(Long, (String, Int))] = sc.parallelize(nodes)
      val edgeRDD: RDD[Edge[Int]] = sc.parallelize(edges)
      val graph: Graph[(String, Int), Int] = Graph(vertexRDD, edgeRDD)
      
      // Iterate nodes with filter
      logger.info ("")
      logger.info ("==> Iterates nodes with filter <==")
      graph.vertices.collect.foreach {
        case (id, (name, age)) => logger.info(s"${name} is ${age} years old")
      }
      
      // Iterate nodes with filter
      logger.info ("")
      logger.info ("==> Iterates nodes with filter. People with more than 30 years <==")
      graph.vertices.filter { case (id, (name, age)) => age > 30 }.collect.foreach {
        case (id, (name, age)) => logger.info(s"${name} is ${age} years old")
      }
      
      // Iterate edges
      logger.info ("")
      logger.info ("==> Iterates edges <==")
      graph.edges.collect.foreach { 
        case e => logger.info(s"${e.srcId} likes ${e.dstId} with score ${e.attr}") 
      }
      
      // Iterate edges with filter
      logger.info ("")
      logger.info ("==> Iterates edges with filter. Relationships with more than 5 <==")
      graph.edges.filter{ case e => e.attr > 5  }.collect.foreach { 
        case e => logger.info(s"${e.srcId} likes ${e.dstId} with score ${e.attr}") 
      }
      
      // Iterate triplets
      logger.info ("")
      logger.info ("==> Iterates triplets <==")
      graph.triplets.collect.foreach{
        case t => logger.info(s"${t.srcAttr._1} likes ${t.dstAttr._1}")
      }
      
      // Iterate triplets with filter
      logger.info ("")
      logger.info ( "==> Iterates triplets with filter. Relationships with more than 5 <==")
      graph.triplets.filter{ case t => t.attr > 5}.collect.foreach{
        case t => logger.info(s"${t.srcAttr._1} likes ${t.dstAttr._1}")
      }
    } 
  }
  
  @Test def statistics() {  
    using(new SparkContext(new SparkConf().setAppName("embedded").setMaster("local[2]"))) { sc =>
      val vertexRDD: RDD[(Long, (String, Int))] = sc.parallelize(nodes)
      val edgeRDD: RDD[Edge[Int]] = sc.parallelize(edges)
      val graph: Graph[(String, Int), Int] = Graph(vertexRDD, edgeRDD)
      
      /* Graph statistics */
      logger.info ("")
      logger.info ("==> In-degree average <==")
      val in = graph.inDegrees.map{case (_, value) => (value, 1)}
        .reduce{case ( c1, c2) => (c1._1 + c2._1, c1._2 + c2._2) }
      logger.info(s"In-degree average ${in._1/in._2}")
      
      logger.info ("")
      logger.info ("==> Maximum indegree <==")
      val max = graph.inDegrees.reduce( {case (a, b) => if (a._2 > b._2) a else b } )._1
      val name = graph.vertices.lookup(max).collectFirst({ case a => a._1})
      logger.info (s"Maximum in-degree ${name}")
      
      logger.info ("")
      logger.info ("==> Shortest path v1, v5 <==")
      val v1 = graph.vertices.collect {case t if t._2._1 == "Alice" => t._1}.first()
      val v5 = graph.vertices.collect {case t if t._2._1 == "Ed" => t._1}.first()
      val sh = ShortestPaths.run(graph, Seq(v1))
      val path = sh.vertices
        .filter( { case(vId, _) => vId == v5 } )
        .first
        //._2
        //.get(v1)
      logger.info (s"Shortest Path ${path}")
      
      /* Domain statistics */
      logger.info ("")
      logger.info ("==> Average age <==")
      graph.collectNeighbors(EdgeDirection.Either)
        .mapValues(t => t.map(_._2._2).sum / t.length.toDouble)
        .collect()
        .foreach {
          case (id, ave) => logger.info (s"For node ${id} the neighbour average age is ${ave}")
      }
    }
  }
}