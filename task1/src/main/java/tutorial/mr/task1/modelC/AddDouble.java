package tutorial.mr.task1.modelC;

import java.util.function.BinaryOperator;

public class AddDouble implements BinaryOperator<Double> {
	@Override
	public Double apply(Double t, Double u) {
		return t+u;
	}
}