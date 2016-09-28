package tutorial.mr.task2.model;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import com.google.common.collect.Lists;

public class ReducerAccumulator implements BiFunction<Map<String, List<Integer>>, Entry, Map<String, List<Integer>>> {

	@Override
	public Map<String, List<Integer>> apply(Map<String, List<Integer>> t, Entry u) {
		if ( t.containsKey(u.getWord()) ) {
			t.get(u.getWord()).add(u.getDocumentId());
		}
		else {
			t.put(u.getWord(), Lists.newArrayList(u.getDocumentId()));
		}
		return t;
	}

}
