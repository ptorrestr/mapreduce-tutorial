package tutorial.mr.task1.modelB;

import java.util.function.BinaryOperator;

public class Reducer implements BinaryOperator<Result> {

	@Override
	public Result apply(Result t, Result u) {
		return new Sum(t.getResult(), u.getResult()).doOperation();
	}

}
