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
public class Reducer implements BinaryOperator<Datum> {

	@Override
	public Datum apply(Datum t, Datum u) {
		return new Datum(t.op1 + u.op1);
	}

}
