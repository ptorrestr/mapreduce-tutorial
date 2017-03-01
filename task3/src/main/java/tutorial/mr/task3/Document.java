package tutorial.mr.task3;

import java.io.Serializable;

public class Document implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String content;
	
	public Document(String id, String content) {
		this.id = id;
		this.content = content;
	}
	
	public String getId() {
		return id;
	}
	
	public String getContent() {
		return content;
	}
}
