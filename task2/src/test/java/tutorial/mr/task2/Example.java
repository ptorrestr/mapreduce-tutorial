package tutorial.mr.task2;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tutorial.mr.task2.model.Mapper1;
import tutorial.mr.task2.model.Mapper2;
import tutorial.mr.task2.model.Mapper3;
import tutorial.mr.task2.model.ReducerAccumulator;
import tutorial.mr.task2.model.ReducerCombiner;
import tutorial.mr.task2.model.index.Builder;
import tutorial.mr.task2.model.index.Document;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Example {
	private static final Logger logger = LoggerFactory.getLogger(Example.class);
	private final Document doc1 = new Document(1, "This is my first document");
	private final Document doc2 = new Document(2, "This is my second document");
	private final Document doc3 = new Document(3, "This is my third document");
	
	@Test
	public void example1 () {
		List<Document> docs = Lists.newArrayList(doc1, doc2, doc3);
		
		Map<String, List<Integer>> invertedIndex = docs.stream()
			.map(new Mapper1()) // Document => WordList
			.map(new Mapper2()) // WordList => List<Entry>
			.flatMap(new Mapper3())  // List<Entry> => Entry
			.reduce(Maps.newHashMap(), new ReducerAccumulator(), new ReducerCombiner());
		logger.info("[Classic] Inverted Index has {} entries", invertedIndex.size());
		logger.info("{}", invertedIndex);
	}
	
	@Test
	public void example2() {
		// Lambda version 
		List<Document> docs = Lists.newArrayList(doc1, doc2, doc3);
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
		logger.info("[Lambda] Inverted Index has {} entries", invertedIndex.size());
		logger.info("{}", invertedIndex);
	}
	
	@Test
	public void example3() {
		// Parallel & Lambda version 
		List<Document> docs = Lists.newArrayList(doc1, doc2, doc3);
		Map<String, List<Integer>> m = Maps.newHashMap();
		Map<String, List<Integer>> invertedIndex = docs.parallelStream()
			.map(Builder::newWordList)
			.map(Builder::newEntryList)
			.flatMap(Collection::stream)
		    .reduce( m, //Neutral
		    		 (p, q) -> { // Accumulator
		    			    logger.info("Thread {} - p {}, q {} ", Thread.currentThread().getName(), p, q);
		    			    Map<String, List<Integer>> k = Maps.newHashMap();
		    			    k.putAll(p);
		    			    k.merge(q.getWord(), 
		    			    		Lists.newArrayList(q.getDocumentId()), 
		    			    		(t, u) -> Stream.concat(t.stream(), u.stream())
		    			    					.distinct()
		    			    					.collect(Collectors.toList()));
		    			    return k;
		    			 		
		    			 },
		    		 (p, q) -> {  // Combiner
		    			 	logger.info("Thread {} - p {}, q {} ", Thread.currentThread().getName(), p, q);
		    			 	return Stream.of(p, q)
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
		    			 		);
		    		 }
		    );
		logger.info("[Parallel] Inverted Index has {} entries", invertedIndex.size());
		logger.info("{}", invertedIndex);	
	}
}
