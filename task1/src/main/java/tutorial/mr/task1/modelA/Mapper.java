package tutorial.mr.task1.modelA;

import java.util.function.Function;

/**
 * A Mapper function takes a single datum and creates a new version
 * of it. The output is used to "map" such datum into a processor.
 * 
 * In this case, the input data is an integer and the mapping generates
 * another integer.
 *
 */
public class Mapper implements Function<Integer, Integer> {

	@Override
	public Integer apply(Integer t) {
		// This case is very simple, but we can apply any kind of function
		// as long as it returns an integer
		return t;
	}

}
