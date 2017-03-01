package tutorial.mr.task2;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tutorial.mr.task2.model.index.Builder;
import tutorial.mr.task2.model.index.Document;

import com.google.common.base.Charsets;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;

public class Example2 {
	private static final Logger logger = LoggerFactory.getLogger(Example.class);
	private final String file1 = "pg11.txt";
	//private final String file2 = "pg1342.txt";
	private final String file3 = "pg16328.txt";

	public List<Document> readFiles() throws IOException {
		// Read files
		Document doc1 = new Document(1, Resources.toString(Resources.getResource(file1), Charsets.UTF_8));
		//Document doc2 = new Document(2, Resources.toString(Resources.getResource(file2), Charsets.UTF_8));
		Document doc3 = new Document(3, Resources.toString(Resources.getResource(file3), Charsets.UTF_8));
		return Lists.newArrayList(doc1, doc3);
	}
	
	@Test
	public void singleThread() throws IOException {
		final List<Document> docs = readFiles();
		
		// Measuring time
		final Stopwatch stopwatch = Stopwatch.createStarted();
		
		// Lambda version 
		Map<String, List<Integer>> m = Maps.newHashMap();
		Map<String, List<Integer>> invertedIndex = docs.stream()
			.map(Builder::newWordList)
			.map(Builder::newEntryList)
			.flatMap(Collection::stream)
		    .reduce( m, //Neutral
		    		 (p, q) -> { // Accumulator
			    			    Map<String, List<Integer>> k = Maps.newHashMap();
			    			    k.putAll(p);
			    			    k.merge(q.getWord(), 
			    			    		Lists.newArrayList(q.getDocumentId()), 
			    			    		(t, u) -> Stream.concat(t.stream(), u.stream())
			    			    					.distinct()
			    			    					.collect(Collectors.toList()));
			    			    return k;
		    			 		},
		    		 (p, q) -> Stream.of(p, q)  // Combiner
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
		    	                 )
		    );
		
		// Stop watch
		stopwatch.stop();
		
		// Print
		logger.info("Single thread");
		logger.info("Elapsed time in Milliseconds ==> {} ", stopwatch.elapsed(TimeUnit.MILLISECONDS));
		logger.info("[Lambda] Inverted Index has {} entries", invertedIndex.size());		
	}
	
	@Test
	public void fourThreads() throws IOException, InterruptedException, ExecutionException {
		final List<Document> docs = readFiles();
		ForkJoinPool forkJoinPool = new ForkJoinPool(4);  
		
		// Measuring time
		final Stopwatch stopwatch = Stopwatch.createStarted();
		
		// Lambda version 
		Map<String, List<Integer>> invertedIndex = 
			forkJoinPool.submit( () -> {
				Map<String, List<Integer>> m = Maps.newHashMap();
				return docs.parallelStream()
					.map(Builder::newWordList)
					.map(Builder::newEntryList)
					.flatMap(Collection::stream)
				    .reduce( m, //Neutral
				    		 (p, q) -> { // Accumulator
					    			    Map<String, List<Integer>> k = Maps.newHashMap();
					    			    k.putAll(p);
					    			    k.merge(q.getWord(), 
					    			    		Lists.newArrayList(q.getDocumentId()), 
					    			    		(t, u) -> Stream.concat(t.stream(), u.stream())
					    			    					.distinct()
					    			    					.collect(Collectors.toList()));
					    			    return k;
				    			 		},
				    		 (p, q) -> Stream.of(p, q)  // Combiner
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
				    	                 )
				    );
				}
			).get();
		
		// Stop watch
		stopwatch.stop();
		
		// Print
		logger.info("4 - threads");
		logger.info("Elapsed time in Milliseconds ==> {} ", stopwatch.elapsed(TimeUnit.MILLISECONDS));
		logger.info("[Lambda] Inverted Index has {} entries", invertedIndex.size());		
	}
}
