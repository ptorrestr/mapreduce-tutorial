package tutorial.mr.task2.model;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class Mapper3 implements Function<List<Entry>, Stream<Entry>>{

	@Override
	public Stream<Entry> apply(List<Entry> t) {
		return t.stream();
	}

}
