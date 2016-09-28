package tutorial.mr.task1.modelC;

import java.util.function.Function;

/**
 * A Mapper function takes a single datum and creates a new version
 * of it. The output is used to "map" such datum into a processor.
 * 
 * In this case, the input data is an object Sum and the mapping generates
 * a Result object.
 *
 */
public class Mapper implements Function<Sum, Result> {

	@Override
	public Result apply(Sum t) {
		return t.doOperation();
	}

}
