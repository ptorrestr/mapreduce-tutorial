package tutorial.mr.task4

import org.scalatest.junit.AssertionsForJUnit
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.apache.spark.graphx._
import org.apache.spark.graphx.util.GraphGenerators
import org.apache.spark.graphx.lib.ShortestPaths
import org.apache.spark.rdd.RDD
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

//import scalaz._, Scalaz._
import argonaut._, Argonaut._

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.io.FileUtils
import org.apache.commons.io.filefilter.WildcardFileFilter
import java.io._

import tutorial.mr.task4.embeddedspark._
import tutorial.mr.task4.Model._

class Example3 extends AssertionsForJUnit {
  var logger = LoggerFactory.getLogger(getClass)
  logger.info (s"${System.getProperty("hadoop.home.dir")}")
  var userDir = System.getProperty("user.dir");
  var bookPath = ""
  var outputDir = ""
	if (SystemUtils.IS_OS_WINDOWS) {
		logger.info("Windows");
		bookPath = userDir + "\\src\\test\\resources";
		outputDir = userDir + "\\output";
	}
	else {
		logger.info("Unix");
		bookPath = userDir + "/src/test/resources";
		outputDir = userDir + "/output";
	}
	logger.info("Using local resources: {}", bookPath);
	logger.info("Output: {}", outputDir);
	FileUtils.deleteDirectory(new File(outputDir))

  @Test def ex1() {
    using(new SparkContext(new SparkConf().setAppName("embedded").setMaster("local[2]"))) { sc =>
      logger.info("test")
      var docs = sc.wholeTextFiles(bookPath)
        .collect()
        .map(  t => t._2.decodeOption[Author].getOrElse(null) )
        .zipWithIndex
        .map( t => (t._2.toLong, t._1))
      //docs.foreach { x => logger.info(s"name = ${x.name}") }
      var docs2 = sc.parallelize(docs)
      var list = docs2
        .flatMap( t => t._2.keywords().map( w => (w, t._1)))
        .map( t => (t, 1))
        .reduceByKey( (p, q) => p + q )
        .map( t => (t._1._1, (t._1._2, t._2) ))
        .groupByKey()
        .mapValues(t => t.toArray)
      //list.map( p => (p._1, p._2.mkString(",")) ).saveAsTextFile(outputDir + "/invertedIndex")
      
      var vertices = docs.map{ case (t, v) => (t, v.name) }
      var edges = docs.map{ case (i, n) => Edge(i, 0L, 1) }
      val vertexRDD: RDD[(Long, String)] = sc.parallelize(vertices)
      val edgeRDD: RDD[Edge[Int]] = sc.parallelize(edges)
      val graph: Graph[String, Int] = Graph(vertexRDD, edgeRDD)
      val rank = graph.pageRank(0.001).vertices.map( t => (t._1.toLong, t._2))
      
      var query = "Einstein"
      var queryList = query.split("\\s+")
      // Find documents
      var hits = list
        .filter{ case (key, doc) => queryList.contains(key) }
        .flatMap( t => t._2)
        .join(rank)
        .map { case ( id, (freq, pageRank)) => (id, freq + pageRank) }
        .collect()
      hits.foreach( t => logger.info(s"id = ${t._1}, score = ${t._2}"))
    }
  }
}