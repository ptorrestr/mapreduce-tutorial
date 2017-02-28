package tutorial.mr.task1.modelB;

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
public class Sum {
	private int op1;
	private int op2;
	
	public Sum(int op1, int op2) {
		this.op1 = op1;
		this.op2 = op2;
	}
	
	public int doOperation() {
		return this.op1 + this.op2;
	}
}
