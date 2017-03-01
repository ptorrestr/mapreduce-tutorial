package tutorial.mr.task2.model.index;

public class Entry {
	private int documentId;
	private String word;
	
	public Entry (int documentId, String word) {
		this.documentId = documentId;
		this.word = word;
	}
	
	public int getDocumentId() {
		return documentId;
	}
	
	public String getWord() {
		return word;
	}
	
	public String toString() {
		return "( " + documentId + " , " + word + " )";
	}
}
