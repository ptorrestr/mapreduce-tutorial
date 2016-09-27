package tutorial.mr.task1.modelA;

import java.util.function.Function;

public class Mapper implements Function<Sum, Integer> {

	@Override
	public Integer apply(Sum t) {
		return t.doOperation();
	}

}
