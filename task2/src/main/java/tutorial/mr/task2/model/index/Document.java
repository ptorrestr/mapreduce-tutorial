package tutorial.mr.task2.model.index;

public class Document {
	private int id;
	private String content;
	
	public Document(int id, String content) {
		this.id = id;
		this.content = content;
	}
	
	public int getId() {
		return id;
	}
	
	public String getContent() {
		return content;
	}
}
