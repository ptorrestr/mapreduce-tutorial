package tutorial.mr.task2.model;

import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;

public class ReducerCombiner implements BinaryOperator<Map<String, List<Integer>>>{

	@Override
	public Map<String, List<Integer>> apply(Map<String, List<Integer>> t,
			Map<String, List<Integer>> u) {
		for (Map.Entry<String, List<Integer>> entry : u.entrySet()) {
			if (t.containsKey(entry.getKey())) {
				t.get(entry.getKey()).addAll(entry.getValue());
			}
			else {
				t.put(entry.getKey(), entry.getValue());
			}
		}
		return t;
	}

}
