package tutorial.mr.task2.model;

import java.util.function.Function;

import tutorial.mr.task2.model.index.Document;
import tutorial.mr.task2.model.index.WordList;

import com.google.common.collect.Lists;

public class Mapper1 implements Function<Document, WordList>{

	@Override
	public WordList apply(Document t) {
		return new WordList(t.getId(), Lists.newArrayList(t.getContent().split("\\s")));
	}

}
