package tutorial.mr.task1.model;

import java.util.function.BinaryOperator;

public class Reducer implements BinaryOperator<Integer> {

	@Override
	public Integer apply(Integer t, Integer u) {
		return t + u;
	}

}
