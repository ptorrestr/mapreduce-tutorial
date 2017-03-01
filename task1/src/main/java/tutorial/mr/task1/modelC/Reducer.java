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
public class Reducer<T, K extends BinaryOperator<T>> implements BinaryOperator<OutDatum<T>> {
	K fun;
	
	public Reducer(K fun) {
		this.fun = fun;
	}
	
	@Override
	public OutDatum<T> apply(OutDatum<T> t, OutDatum<T> u) {
		return new OutDatum<T>(fun.apply(t.getValue(),u.getValue()) );
	}
}