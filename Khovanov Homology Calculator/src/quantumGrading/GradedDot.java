package quantumGrading;

public class GradedDot {
	int homologicalGrading;
	int quantumGrading;
	
	public GradedDot(int homologicalGrading, int quantumGrading) {
		this.homologicalGrading = homologicalGrading;
		this.quantumGrading = quantumGrading;
	}
	
	public void printInfo() {
		System.out.println(homologicalGrading+" "+quantumGrading);
	}

}
