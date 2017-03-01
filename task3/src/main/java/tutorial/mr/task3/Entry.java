package tutorial.mr.task3;

import java.io.Serializable;

public class Entry implements Serializable{
	private static final long serialVersionUID = 1L;
	private String documentId;
	private String word;
	
	public Entry (String documentId, String word) {
		this.documentId = documentId;
		this.word = word;
	}
	
	public String getDocumentId() {
		return documentId;
	}
	
	public String getWord() {
		return word;
	}
	
	public String toString() {
		return "( " + documentId + " , " + word + " )";
	}
}
