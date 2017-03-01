package tutorial.mr.task3;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import scala.Tuple2;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Builder {
	public static Document newDocument(Tuple2<String, String> t) {
		return new Document(t._1, t._2);
	}
	
	public static WordList newWordList(Document doc) {
		return new WordList(doc.getId(), Lists.newArrayList(doc.getContent().split("\\s")));
	}
	
	public static List<Entry> newEntryList(WordList wl) {
		return wl.getWords().stream().map( q -> { return new Entry(wl.getDocumentId(), q); })
        	.collect(Collectors.toList()) ;
	}
	
	public static Map<String, List<String>> newIndexMap(Entry e) {
		Map<String, List<String>> m = Maps.newHashMap();
		m.put(e.getWord(), Lists.newArrayList(e.getDocumentId()));
		return m;
	}
}
