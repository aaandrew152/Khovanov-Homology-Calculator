package quantumGrading;

import java.util.ArrayList;

import original.DotsAndArrows;
import original.Tensor;

public class DotsToQuantum {
	DotsAndArrows allDots;//Dots which have no quantum grading
	ArrayList<GradedDot> gradedDotList = new ArrayList<GradedDot>();//Dots with a quantum grading
	int numPositiveCrossings;
	int numNegativeCrossings;//Number of negative crossings
	ArrayList<TensorWithRowNum> ungradedDotsList = new ArrayList<TensorWithRowNum>();//Existing dots which have not yet been graded

	public DotsToQuantum(DotsAndArrows ungradedDots, int numPos, int numNeg) {
		this.allDots = ungradedDots;
		this.numNegativeCrossings = numNeg;
		this.numPositiveCrossings = numPos;
		this.ungradedDotsList = getUngradedDots();
		this.gradedDotList = quantumizeUngradedDots();
	}

	public void output() {
		//System.out.println("The first number is the homological grading, and the second number is the quantum grading");
		for (GradedDot gradedDot : gradedDotList) {
			gradedDot.printInfo();
		}
	}

	public ArrayList<TensorWithRowNum> getUngradedDots() {
		ArrayList<TensorWithRowNum> allExistingUngradedDots = new ArrayList<TensorWithRowNum>();

		for(int i = 0; i < allDots.rows.length; i++) {
			for (Tensor ungradedDot : allDots.rows[i].dots) {
				if(!ungradedDot.isDeleted())
					allExistingUngradedDots.add(new TensorWithRowNum(ungradedDot, i));
			}
		}//Goes through each dotrow then access each undeleted dot within it and adds it to the list with its rowNum

		return allExistingUngradedDots;
	}

	public ArrayList<GradedDot> quantumizeUngradedDots () {
		ArrayList<GradedDot> nowGradedDots = new ArrayList<GradedDot>();
		
		for (TensorWithRowNum ungradedDot : this.ungradedDotsList) {			
			int homologicalGrading = -ungradedDot.rowNum+this.numNegativeCrossings;
			int quantumGrading = homologicalGrading+ungradedDot.tensor.getTotalPosNeg()-this.numNegativeCrossings+this.numPositiveCrossings;
			
			GradedDot toBeAdded = new GradedDot(homologicalGrading, quantumGrading);
			nowGradedDots.add(toBeAdded);
		}		
				
		return nowGradedDots;
	}

}
