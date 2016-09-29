package tutorial.mr.task3;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jersey.repackaged.com.google.common.collect.Maps;

import com.google.common.collect.Lists;

public class Builder {
	public static Document newDocument(int id, String content) {
		return new Document(id, content);
	}
	
	public static WordList newWordList(Document doc) {
		return new WordList(doc.getId(), Lists.newArrayList(doc.getContent().split("\\s")));
	}
	
	public static List<Entry> newEntryList(WordList wl) {
		return wl.getWords().stream().map( q -> { return new Entry(wl.getDocumentId(), q); })
        	.collect(Collectors.toList()) ;
	}
	
	public static Map<String, List<Integer>> newIndexMap(Entry e) {
		Map<String, List<Integer>> m = Maps.newHashMap();
		m.put(e.getWord(), Lists.newArrayList(e.getDocumentId()));
		return m;
	}
}
