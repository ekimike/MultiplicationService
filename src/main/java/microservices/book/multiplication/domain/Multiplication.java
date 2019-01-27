package microservices.book.multiplication.domain;

public class Multiplication {
	private int factorA;
	private int factorB;
	
	private int result;

	public Multiplication(int factorA, int factorB) {
		super();
		this.factorA = factorA;
		this.factorB = factorB;
		this.result = this.factorA * this.factorB;
	}

	public int getFactorA() {
		return factorA;
	}

	public int getFactorB() {
		return factorB;
	}

	public int getResult() {
		return result;
	}

	@Override
	public String toString() {
		return "Multiplication [factorA=" + factorA + ", factorB=" + factorB + ", result=" + result + "]";
	}
	
	
	
}
