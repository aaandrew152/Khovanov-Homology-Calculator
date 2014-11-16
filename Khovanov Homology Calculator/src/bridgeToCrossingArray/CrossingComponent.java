package bridgeToCrossingArray;
import java.util.ArrayList;

public class CrossingComponent {
	int letterPosition;//Specific letter position in sigma, when connecting these will need to match
	int edge;//Number of the edge/vertex being considered
	int direction;//Direction that the strand is following
	boolean over;//Whether or not this section is over
	boolean matched;//Whether or not this component has been matched yet
	boolean fixFinalEdge;
	bridgePlacement bridge;//Original bridge	

	public CrossingComponent(bridgePlacement bridge, boolean over, int direction) {
		this.letterPosition = bridge.letterNumber;
		this.edge = bridge.edge;
		this.over = over;
		this.direction = direction;
		this.matched = false;
		this.fixFinalEdge = false;
		this.bridge = bridge;	
	}

	public void printCrossingComponent() {
		System.out.println(letterPosition + " "+edge+" "+over+" "+direction);
	}

	public UppedEdge getEdgeUpped(ArrayList<Integer>startingEdge, int startingEdgeIndex) {
		if(fixFinalEdge) {
			int newEdge;
			newEdge = startingEdge.get(startingEdgeIndex);
			UppedEdge upEdge = new UppedEdge(startingEdgeIndex+1, newEdge);
			
			return upEdge;
		}
		else
			return new UppedEdge(startingEdgeIndex, this.edge+1);
	}//Ensures that an edge value within the scope is given.(i.e. if an edge was the final edge the next shall be 1)

}
