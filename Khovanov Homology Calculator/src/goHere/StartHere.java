package goHere;

import bridgeToCrossingArray.*;
import original.*;
import quantumGrading.DotsToQuantum;
import quantumGrading.QuantumToPolynomial;

public class StartHere {
	public static void main(String[] args){
		int[] input = {1,3};//INPUT THE KNOT HERE IN BRIDGE FORM, everything else is self computing
		/*The output lists the remaining elements in the homology,
		*listing first their homological grading, and then their quantum grading
		*/

		Sigma knotSigma = new Sigma(input);
        
		Crossing[] crossarray = knotSigma.resolveBridge();		
		
		int numPositiveCrossings = getNumPositiveCrossings(crossarray);//Finds the number of positive crossings
		int numNegativeCrossings = getNumNegativeCrossings(crossarray);//Finds the number of negative crossings

		DotsAndArrows DAA = goThroughCrossingsToDots(crossarray);
		
		DotsToQuantum DTQ = new DotsToQuantum(DAA,numPositiveCrossings,numNegativeCrossings);
		
		new QuantumToPolynomial(DTQ);

	}
	
	public static int getNumNegativeCrossings(Crossing[] crossingArray) {
		int numNeg = 0;
		
		for (Crossing crossing : crossingArray) {
			if(!crossing.getSign())
				numNeg++;
		}
		
		return numNeg;
	}
	
	public static int getNumPositiveCrossings(Crossing[] crossingArray) {
		int numPos = 0;
		
		for (Crossing crossing : crossingArray) {
			if(crossing.getSign())
				numPos++;
		}
		
		return numPos;
	}
	
	public static DotsAndArrows goThroughCrossingsToDots (Crossing[] crossarray) {
		Knot theKnot = new Knot(crossarray);

		theKnot.resolveknot();

		KhovanovMap theKhovanovMap = new KhovanovMap(theKnot);

		DotsAndArrows theDotsAndArrows = new DotsAndArrows(theKhovanovMap);
						
		return theDotsAndArrows;
	}
	
}
