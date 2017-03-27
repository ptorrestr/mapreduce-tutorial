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

import org.apache.commons.lang.SystemUtils;
import tutorial.mr.task4.embeddedspark._

class Example5 extends AssertionsForJUnit {
  var logger = LoggerFactory.getLogger(getClass)
  var userDir: String = ""
	var bookPath: String = ""
	var outputDir: String = ""
	
	logger.info("{}", System.getProperty("hadoop.home.dir") );
	userDir = System.getProperty("user.dir");
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
  
	/* CSV */
  @Test def readData1() {
    using(new SparkContext(new SparkConf().setAppName("embedded").setMaster("local[2]"))) { sc =>
      // 0
      // read files
      var verticesText = sc.textFile(bookPath + "/graph1/vertices.txt")
      var edgesText = sc.textFile(bookPath + "/graph1/edges.txt")
      
      // 1
      // we need to create a RDD for the vertices with the
      // following signature: RDD[(Long, (String, Int))]
      var vertices:RDD[(Long, (String, Int))] = verticesText
        .map( line => line.replace(" ", "").split(","))
        .map( array => (array(0).toLong, (array(1), array(2).toInt)))
        
      // 2
      // and for the edges: JavaRDD[Edge[Integer]]
      var edges:RDD[Edge[Int]] = edgesText
	      .map( line => line.replace(" ", "").split(","))
	.      map( list => new Edge(list(0).toLong, list(1).toLong, list(2).toInt))
	
	    // 3
	    // Create graph
	    val graph: Graph[(String, Int), Int] = Graph(vertices, edges)
	    
	    graph.triplets.collect().foreach {
        case c => logger.info(s"${c.srcAttr} -> ${c.dstAttr}") 
      }
    }
  }
  
  /* No IDs*/
  @Test def readData2() {
    using(new SparkContext(new SparkConf().setAppName("embedded").setMaster("local[2]"))) { sc =>
      // 0
      // read files
      var edgesText = sc.textFile(bookPath + "/graph2/edges.txt")
      
      // 1
      // We define a map so we can transform one string into a integer when creating the edges
      var verticesText = edgesText
        .map( line => line.replace(" ", "").split(","))
        .flatMap( list => Array(list(0), list(1)))
        .distinct()
        .collect()
      var dict = verticesText.zipWithIndex.toMap
      // Now we create the RDD for the vertices
      var vertices:RDD[(Long, String)] = sc.parallelize(verticesText.zipWithIndex.map(t => (t._2.toLong, t._1 )).toSeq)
      
      // 2
      // We create a RDD for the edges: JavaRDD[Edge[Integer]]
      var edges:RDD[Edge[Int]] = edgesText
	      .map( line => line.replace(" ", "").split(","))
	.      map( list => new Edge( dict(list(0)), dict(list(1)), list(2).toInt))
	
	    // 3
	    // Create graph
	    val graph: Graph[String, Int] = Graph(vertices, edges)
	    
	    graph.triplets.collect().foreach {
        case c => logger.info(s"${c.srcAttr} -> ${c.dstAttr}") 
      }
    }
  }
  
  /* JSON */
  @Test def readData3() {
    using(new SparkContext(new SparkConf().setAppName("embedded").setMaster("local[2]"))) { sc =>
      import argonaut._, Argonaut._
      // 0
      // Read all the files in this folder
      var jsonFiles = sc.wholeTextFiles(bookPath + "/graph3")
      // This last function creates a map where the key is the file name
      // and the value is the content of the file

      // Take the content of the file (t._2) an use Argonout to parse the
      // text. (decodeOption[Model2.Node]). If we can extract an object
      // Node, then get it otherwise, create a null.
      var jsonObjects = jsonFiles
        .map( t => t._2.decodeOption[Model2.Node].getOrElse(null))
      // Now we can create the node and edges as usually
      
      // 1
      // We define a map so we can transform one string into a integer when creating the edges
      var vertices:RDD[(Long, (String, Int))] = jsonObjects
        .map( t => (t.id.toLong, (t.name, t.age)))
      
      // 2
      // We create a RDD for the edges: JavaRDD[Edge[Integer]]
      var edges:RDD[Edge[Int]] = jsonObjects
        .flatMap( t => t.links.map { 
          case (link) => Edge(t.id, link(0), link(1)) 
        })
	
	    // 3
	    // Create graph
	    val graph: Graph[(String, Int), Int] = Graph(vertices, edges)
	    
	    graph.triplets.collect().foreach {
        case c => logger.info(s"${c.srcAttr} -> ${c.dstAttr}") 
      }
    }
  }
}