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
public class Datum {
	int op1;
	
	public Datum(int op1) {
		this.op1 = op1;
	}
	
	public int getValue() {
		return op1;
	}
}
