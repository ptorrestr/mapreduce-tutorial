package tutorial.mr.task2.model;

import java.util.List;
import java.util.function.Function;

import tutorial.mr.task2.model.index.Entry;
import tutorial.mr.task2.model.index.WordList;

import com.google.common.collect.Lists;

public class Mapper2 implements Function<WordList, List<Entry>> {

	@Override
	public List<Entry> apply(WordList t) {
		List<Entry> entries = Lists.newArrayList();
		for(String word: t.getWords()) {
			entries.add(new Entry(t.getDocumentId(), word));
		}
		return entries;
	}

}
