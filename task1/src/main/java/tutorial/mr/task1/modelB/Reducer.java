package tutorial.mr.task1.modelB;

import java.util.function.BinaryOperator;

/**
 * A Reducer accumulates (or combines) two single datums and
 * produce a new one. 
 * 
 * In this case, two integers are combined and a new 
 * integer is generated.
 *
 */
public class Reducer implements BinaryOperator<Integer> {

	@Override
	public Integer apply(Integer t, Integer u) {
		return t + u;
	}

}
