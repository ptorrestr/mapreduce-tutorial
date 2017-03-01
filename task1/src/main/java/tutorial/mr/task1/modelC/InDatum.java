package tutorial.mr.task1.modelC;

/**
 * This is an example of a more complex data structure.
 * MapReduce can use any native types as well as user-defined
 * data structures.
 * 
 * Some tasks may just work fine with primitive types, but others
 * may require more advance structures in order to improve the 
 * performance.
 * 
 * Have in mind that the mapper and reducer are stateless functions. 
 *
 */
public class InDatum {
	private int op1;
	private double op2;
	private String op3;
	
	public InDatum(int op1, double op2, String op3) {
		this.op1 = op1;
		this.op2 = op2;
		this.op3 = op3;
	}

	public int getOp1() {
		return op1;
	}

	public double getOp2() {
		return op2;
	}

	public String getOp3() {
		return op3;
	}
}
