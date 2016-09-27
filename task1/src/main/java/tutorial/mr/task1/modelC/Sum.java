package tutorial.mr.task1.modelC;

public class Sum {
	private int op1;
	private int op2;
	
	public Sum(int op1, int op2) {
		this.op1 = op1;
		this.op2 = op2;
	}
	
	public Result doOperation() {
		return new Result( this.op1 + this.op2);
	}
}
