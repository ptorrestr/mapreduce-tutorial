package tutorial.mr.task1.modelB;

import java.util.function.Function;

public class Mapper implements Function<Sum, Result> {

	@Override
	public Result apply(Sum t) {
		return t.doOperation();
	}

}
