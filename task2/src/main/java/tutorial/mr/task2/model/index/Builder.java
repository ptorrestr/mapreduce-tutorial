package tutorial.mr.task2.model.index;

import java.util.List;
import java.util.stream.Collectors;

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
}
