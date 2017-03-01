package tutorial.mr.task3;

import java.io.Serializable;
import java.util.List;

public class WordList implements Serializable {
	private static final long serialVersionUID = 1L;
	private String documentId;
	private List<String> words;
	
	public WordList(String documentId, List<String> words) {
		this.documentId = documentId;
		this.words = words;
	}
	
	public String getDocumentId() {
		return documentId;
	}
	
	public List<String> getWords() {
		return words;
	}
}
