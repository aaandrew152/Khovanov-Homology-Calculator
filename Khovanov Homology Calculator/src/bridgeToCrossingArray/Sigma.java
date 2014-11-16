package bridgeToCrossingArray;
import java.util.ArrayList;
import original.*;

public class Sigma {

	int[] bridgeForm;
	bridgePlacement currentBridge;
	ArrayList<CrossingComponent> componentList = new ArrayList<CrossingComponent>();
	boolean goingDown = true;
	ArrayList<Integer> topsTouched = new ArrayList<Integer>();//Lists all the bridge tops which have been reached
	int numberOfStrands;//The total number of strands which will need to be reached.
	ArrayList<Integer> startingEdge = new ArrayList<Integer>();//Stores all the starting edges, return link edges later
	int startingEdgeIndex = 0;//Tracks how many startingEdge's have been corrected for

	public Sigma(int[] bridgeForm) {
		this.bridgeForm = bridgeForm;
		this.numberOfStrands = findMaxStrand();
	}

	public Crossing[] resolveBridge(){
		boolean knotTopsCovered = false;//To test whether or not every top of the knot has been covered.
		currentBridge = new bridgePlacement(bridgeForm);

		while(!knotTopsCovered) {//Once every top of the bridge form knot has been covered this will break
			boolean returned = false;//Whether or not the knot has return to its starting point
			int startingTop = currentBridge.strand;//Determines which exact strand the bridge is starting at
			startingEdge.add(currentBridge.edge);//Stores the current edge value(in case of link)
			while(!returned){//If the knot has returned to the start, it will break this while loop
				goDown();//Goes all the way down to the bottom
				goUp();//Goes all the way up to the top
				returned = checkReturn(startingTop);//(determines whether or not its returning to the same strand)
				if(returned)
					fixFinalEdge();
			}
			knotTopsCovered = setCurrentTop();
			//Moves the tracked to an uncover top, in order from left to right.
			//Also notes whether all tops have been covered already
		}//Goes through all the strands and generates Crossing components for all 1/2 crossings

		return matchCrossingComponents();
	}

	public void goDown() {
		int crossed;
		boolean bottomReached = false;

		goingDown = true;

		while(!bottomReached) {
			crossed = checkCurrentLetter();
			if (crossed != 0){
				if (crossed == 1) 
				{leftCross(); currentBridge.downStrand();}
				else if (crossed == -1)
				{rightCross(); currentBridge.upStrand();}
				currentBridge.upEdge();//increase the number of the edge
			}
			if(currentBridge.letterNumber+1 == bridgeForm.length)
			{bottomReached = true; currentBridge.strandSwap();}
			else
				currentBridge.nextLetter();//consider the crossing below the current one.
		}

	}

	public void goUp() {
		int crossed;
		boolean topReached = false;

		goingDown = false;

		while(!topReached) {
			crossed = checkCurrentLetter();
			if (crossed != 0){
				if (crossed == 1) 
				{leftCross(); currentBridge.downStrand();}
				else if (crossed == -1)
				{rightCross(); currentBridge.upStrand();}
				currentBridge.upEdge();//increase the number of the edge
			}//Sends knot left or right through a crossing

			if(currentBridge.letterNumber == 0) {
				topReached = true;
				topsTouched.add(currentBridge.strand);
				currentBridge.strandSwap();
				topsTouched.add(currentBridge.strand);
			}//The top has been reached, and notes which of the bridge tops have been hit.

			else
				currentBridge.previousLetter();//consider the crossing above the current one.
		}
	}//At the end of this values should be added to knottopscovered to show which ones have been reached

	public int checkCurrentLetter() {
		if(Math.abs(bridgeForm[currentBridge.letterNumber]) == currentBridge.strand-1)//i.e. the strand is to the left of the crossing
			return 1;
		else if(Math.abs(bridgeForm[currentBridge.letterNumber]) == currentBridge.strand)//i.e. the strand is to the right of the crossing
			return -1;
		else//The strand does not touch the current crossing
			return 0;
	}

	public void leftCross() {
		boolean over;
		int direction;

		if(goingDown) {
			if(bridgeForm[currentBridge.letterNumber] > 0)
				over = true;
			else
				over = false;
			direction = 1;//South west
		}
		else {
			if(bridgeForm[currentBridge.letterNumber] > 0)
				over = false;
			else
				over = true;
			direction = 2;//North west
		}//determines whether or not the strand in question is over or under

		componentList.add(new CrossingComponent(this.currentBridge,over,direction));
	}

	public void rightCross() {
		boolean over;
		int direction;

		if(goingDown) {
			if(bridgeForm[currentBridge.letterNumber] > 0) 
				over = false;
			else
				over = true;
			direction = 0;//South east
		}
		else {
			if(bridgeForm[currentBridge.letterNumber] > 0)
				over = true;
			else
				over = false;
			direction = 3;//North east
		}//determines whether or not the strand in question is over or under

		componentList.add(new CrossingComponent(this.currentBridge,over,direction));
	}


	public boolean setCurrentTop() {
		boolean knotTopsCovered = true;

		for (int i = 1; i <= numberOfStrands; i++) {
			boolean iTouched = false;
			for (int j : topsTouched) {
				if(i == j) {
					iTouched = true;
					break;
				}
			}
			if(!iTouched) {
				knotTopsCovered = false;
				currentBridge.setStrand(i);
				break;
			}
		}

		return knotTopsCovered;
	}//Searches topsTouched and finds which tops have not yet been reached.

	public int findMaxStrand() {
		int maxStrand = 0;
		for (int i = 0; i < bridgeForm.length; i++) {
			if(maxStrand < bridgeForm[i])
				maxStrand = bridgeForm[i];
		}//Finds largest crossing value

		if(maxStrand%2 == 0)
			maxStrand++;//corrects if the highest crossing is even(if odd, it is the highest valued strand)
		maxStrand++;//Even if the strand is even or odd it must increase once more

		return maxStrand;
	}

	public boolean checkReturn(int startingTop) {
		if(currentBridge.strand == startingTop)
			return true;
		else 
			return false;
	}//To check whether or not the tracker is at the same strand it started at

	public Crossing[] matchCrossingComponents() {
		ArrayList<Crossing> tempKnotCrossings = new ArrayList<Crossing>();

		for (CrossingComponent cc: componentList) {
			if(!cc.matched) {//Not matched yet...
				for (CrossingComponent dd : componentList) {
					if(cc.letterPosition == dd.letterPosition && cc != dd) {
						tempKnotCrossings.add(combineComponents(cc,dd));
						cc.matched = true;
						dd.matched = true;
					}
				}
			}
		}
		Crossing[] knotCrossings = new Crossing[tempKnotCrossings.size()];//Converting to an array

		for (int i = 0; i < tempKnotCrossings.size(); i++) {
			knotCrossings[i] = tempKnotCrossings.get(i);
		}

		return knotCrossings;
	}

	public Crossing combineComponents(CrossingComponent a, CrossingComponent b) {
		boolean positiveCrossing;//Positive or negative crossing
		UppedEdge uppedA;
		UppedEdge uppedB;

		if(a.over) {
			if(a.direction-b.direction == 1 ||a.direction-b.direction == -3)
				positiveCrossing = true;
			else
				positiveCrossing = false;
		}
		else {
			if(b.direction-a.direction == 1|| b.direction-a.direction == -3)
				positiveCrossing = true;
			else 
				positiveCrossing = false;
		}
		
		uppedA = a.getEdgeUpped(startingEdge,startingEdgeIndex);
		startingEdgeIndex = uppedA.startingEdgeIndex;

		uppedB =  b.getEdgeUpped(startingEdge,startingEdgeIndex);
		startingEdgeIndex = uppedB.startingEdgeIndex;

		Crossing toBeAdded = new Crossing(a.edge, uppedA.edgeNumber, b.edge, uppedB.edgeNumber, positiveCrossing);

		return toBeAdded;
	}

	public void fixFinalEdge() {
		CrossingComponent lastComponent = componentList.get(componentList.size()-1);
		lastComponent.fixFinalEdge = true;
		componentList.set(componentList.size()-1, lastComponent);
	}


}