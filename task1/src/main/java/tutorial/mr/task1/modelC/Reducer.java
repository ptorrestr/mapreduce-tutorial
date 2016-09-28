package tutorial.mr.task1.modelC;

import java.util.function.BinaryOperator;

/**
 * A Reducer accumulates (or combines) two single datums and
 * produce a new one. 
 * 
 * In this case, two Result objects are combined and a new 
 * Result object is generated.
 *
 */
public class Reducer implements BinaryOperator<Result> {

	@Override
	public Result apply(Result t, Result u) {
		return new Sum(t.getResult(), u.getResult()).doOperation();
	}

}
