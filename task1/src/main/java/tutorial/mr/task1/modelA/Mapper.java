package tutorial.mr.task1.modelA;

import java.util.function.Function;

public class Mapper implements Function<Integer, Integer> {

	@Override
	public Integer apply(Integer t) {
		return t;
	}

}
