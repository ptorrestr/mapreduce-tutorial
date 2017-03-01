package tutorial.mr.task3;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Functions {
	
	public static Map<String, List<String>> invertedIndex(List<Document> docs) {
		Map<String, List<String>> m = Maps.newHashMap();
		return docs.stream()
			.map(Builder::newWordList)
			.map(Builder::newEntryList)
			.flatMap(Collection::stream)
		    .reduce( m, //Neutral
		    		 (p, q) -> { // Accumulator
			    			    Map<String, List<String>> k = Maps.newHashMap();
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
}
