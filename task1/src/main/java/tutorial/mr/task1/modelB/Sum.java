package tutorial.mr.task1.modelB;

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
