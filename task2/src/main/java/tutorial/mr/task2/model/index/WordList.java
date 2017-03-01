package tutorial.mr.task2.model.index;

import java.util.List;

public class WordList {
	private int documentId;
	private List<String> words;
	
	public WordList(int documentId, List<String> words) {
		this.documentId = documentId;
		this.words = words;
	}
	
	public int getDocumentId() {
		return documentId;
	}
	
	public List<String> getWords() {
		return words;
	}
}
