package tutorial.mr.task3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import scala.Tuple2;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;


public class Example1 {
	private static final Logger logger = LoggerFactory.getLogger(Example1.class);
	private String userDir;
	private String bookPath;
	private String resourceDir = "/src/test/resources";
	private String outputDir;
	
	public Example1() {
		userDir = System.getProperty("user.dir");
		bookPath = System.getProperty("books.dir");
		if ( bookPath == null) {
			bookPath = userDir + resourceDir;
			logger.info("Using local resources: {}", bookPath);
		}
		outputDir = userDir + "/output";
	}
	
	@Test
	public void invertedIndex1() throws IOException {
		int executors = 4;
		SparkConf conf = new SparkConf();
		conf.set("spark.executor.instances", Integer.toString(executors));
		try ( JavaSparkContext jsc = new JavaSparkContext("local", getClass().getSimpleName(), conf) ) {
			// Measuring time
			final Stopwatch stopwatch = Stopwatch.createStarted();
			
			JavaRDD<Document> docs = jsc.wholeTextFiles(bookPath, executors).map(Builder::newDocument);
			logger.info("Files were read");
			JavaPairRDD<String, List<Tuple2<String, Integer>>> list = docs
				.map(Builder::newWordList)
				.map(Builder::newEntryList)
				.flatMap(Collection::iterator)
				.mapToPair( p-> new Tuple2<String, String>(p.getWord(), p.getDocumentId()) )
				.mapToPair(t -> new Tuple2<Tuple2<String,String>, Integer>(t, 1))
				.reduceByKey( (p, q) -> p + q )
				.mapToPair(p -> new Tuple2<String, Tuple2<String,Integer>>(p._1._1, new Tuple2<String, Integer>(p._1._2, p._2) ) )
				.groupByKey()
				.mapValues(p -> Lists.newArrayList(p.iterator()));
			
			list.saveAsTextFile(outputDir +"/invertedIndex1");
			
			// Stop watch
			stopwatch.stop();
			
			// Print
			logger.info("1 - worker Spark");
			logger.info("Elapsed time in Milliseconds ==> {} ", stopwatch.elapsed(TimeUnit.MILLISECONDS));
		}
	}
	
	@Test
	public void invertedIndex2() throws IOException {
		try ( JavaSparkContext jsc = new JavaSparkContext("local", getClass().getSimpleName()) ) {
			
			// Measuring time
			final Stopwatch stopwatch = Stopwatch.createStarted();

			JavaPairRDD<String, String> docs = jsc.wholeTextFiles(bookPath, 4);
	
			JavaPairRDD<String, List<Tuple2<String, Integer>>> list = docs
					.flatMap(t -> Lists.newArrayList(t._2.toLowerCase().split("\\W"))
							.stream().map(w -> new Tuple2<String, String>(w, t._1))
							.collect(Collectors.toList())
							.iterator())
					.mapToPair(t -> new Tuple2<Tuple2<String,String>, Integer>(t, 1))
					.reduceByKey( (p, q) -> p + q )
					.mapToPair(p -> new Tuple2<String, Tuple2<String,Integer>>(p._1._1, new Tuple2<String, Integer>(p._1._2, p._2) ) )
					.groupByKey()
					.mapValues(p -> Lists.newArrayList(p.iterator()) )
					;
			list.saveAsTextFile(outputDir +"/invertedIndex2");
			stopwatch.stop();
			
			// Print
			logger.info("1 - worker Spark");
			logger.info("Elapsed time in Milliseconds ==> {} ", stopwatch.elapsed(TimeUnit.MILLISECONDS));
		}
	}

}
