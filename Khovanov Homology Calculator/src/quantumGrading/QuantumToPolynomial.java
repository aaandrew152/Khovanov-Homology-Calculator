package quantumGrading;

public class QuantumToPolynomial {
	DotsToQuantum DTQ;
	String polynomial ="";
	
	public QuantumToPolynomial(DotsToQuantum DTQ) {
		this.DTQ = DTQ;
		
		calculatePolynomial();
		output();
	}
	
	public void calculatePolynomial() {
		int previousHomologicalGrading = 100;
		for (GradedDot gd : DTQ.gradedDotList) {
			if(previousHomologicalGrading != gd.homologicalGrading)
				polynomial+="\n";
			polynomial+= "q^{"+gd.quantumGrading+"}*t^{"+gd.homologicalGrading+"} + ";
			previousHomologicalGrading = gd.homologicalGrading;
		}
		polynomial = polynomial.substring(0,polynomial.length()-2);
	}
	
	public void output(){
		System.out.println(polynomial);
	}

}
