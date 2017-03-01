package tutorial.mr.task1.modelC;

import java.util.function.BinaryOperator;

public class AddInteger implements BinaryOperator<Integer> {
	@Override
	public Integer apply(Integer t, Integer u) {
		return t+u;
	}
}
