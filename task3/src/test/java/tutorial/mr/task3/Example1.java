package tutorial.mr.task3;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;


public class Example1 {
	private static final Logger logger = LoggerFactory.getLogger(Example1.class);
	private final String file1 = "pg11.txt";
	private final String file2 = "pg1342.txt";
	private final String file3 = "pg16328.txt";
	
	public List<Document> readFiles() throws IOException {
		// Read files
		Document doc1 = new Document(1, Resources.toString(Resources.getResource(file1), Charsets.UTF_8));
		Document doc2 = new Document(2, Resources.toString(Resources.getResource(file2), Charsets.UTF_8));
		Document doc3 = new Document(3, Resources.toString(Resources.getResource(file3), Charsets.UTF_8));
		/*Document doc1 = new Document(1, "this is the first document");
		Document doc2 = new Document(2, "this is the second document");
		Document doc3 = new Document(3, "this is the third document");*/
		return Lists.newArrayList(doc1, doc2, doc3);
	}
	
	@Test
	public void invertedIndex() throws IOException {
		try ( JavaSparkContext jsc = new JavaSparkContext("local", getClass().getSimpleName()) ) {
			List<Document> docs = readFiles();
			
			// Measuring time
			final Stopwatch stopwatch = Stopwatch.createStarted();
			
			JavaRDD<Document> a = jsc.parallelize(docs, 1);
			Map<String, List<Integer>> invertedIndex = a.map(Builder::newWordList)
				.map(Builder::newEntryList)
				.flatMap(p -> p.iterator())
				.map(Builder::newIndexMap)
				.reduce( (p, q) -> Stream.of(p, q)  // Combiner
		    			 		.map(Map::entrySet)
		    			 		.flatMap(Collection::stream)
		    			 		.collect(
		    			 				Collectors.toMap(
		    			 						Map.Entry::getKey,
		    			 						Map.Entry::getValue,
		    			 						(t, u) -> Stream.concat(t.stream(), u.stream())
		    			 								.distinct()
		    			 								.collect(Collectors.toList())
		    			 				)
		    	                 ));
			
			// Stop watch
			stopwatch.stop();
			
			// Print
			logger.info("1 - worker Spark");
			logger.info("Elapsed time in Milliseconds ==> {} ", stopwatch.elapsed(TimeUnit.MILLISECONDS));
			logger.info("[Lambda] Inverted Index has {} entries", invertedIndex.size());	
		}
	}
	
	@Test
	public void invertedIndex2() throws IOException {
		try ( JavaSparkContext jsc = new JavaSparkContext("local", getClass().getSimpleName()) ) {
			List<Document> docs = readFiles();
			
			// Measuring time
			final Stopwatch stopwatch = Stopwatch.createStarted();
			
			JavaRDD<Document> a = jsc.parallelize(docs, 4);
			Map<String, List<Integer>> invertedIndex = a.map(Builder::newWordList)
				.map(Builder::newEntryList)
				.flatMap(p -> p.iterator())
				.map(Builder::newIndexMap)
				.reduce( (p, q) -> Stream.of(p, q)  // Combiner
		    			 		.map(Map::entrySet)
		    			 		.flatMap(Collection::stream)
		    			 		.collect(
		    			 				Collectors.toMap(
		    			 						Map.Entry::getKey,
		    			 						Map.Entry::getValue,
		    			 						(t, u) -> Stream.concat(t.stream(), u.stream())
		    			 								.distinct()
		    			 								.collect(Collectors.toList())
		    			 				)
		    	                 ));
			
			// Stop watch
			stopwatch.stop();
			
			// Print
			logger.info("1 - worker Spark");
			logger.info("Elapsed time in Milliseconds ==> {} ", stopwatch.elapsed(TimeUnit.MILLISECONDS));
			logger.info("[Lambda] Inverted Index has {} entries", invertedIndex.size());	
		}
	}

}
