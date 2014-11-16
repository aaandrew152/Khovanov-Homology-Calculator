package bridgeToCrossingArray;

public class bridgePlacement {
	int edge;//Vertex or edge where the program is currently on
	int letterNumber;//Currently accessed letter(i.e. the position of it, NOT the actual letter)
	int totalLetters;//Total number of letters
	int strand;//Currently accessed strand
	int[] bridgeForm;//The original input(i.e. knot in bridge form)

	public bridgePlacement(int[] bridgeForm) {
		this.edge = 1;
		this.letterNumber = 0;//Starts at 0 to access array
		this.strand = 1;
		this.totalLetters = bridgeForm.length;
		this.bridgeForm = bridgeForm;
	}//Holds the current placement of the tracker within the bridge knot

	public void nextLetter() {
		this.letterNumber++;
	}
	public void previousLetter() {
		this.letterNumber--;
	}//Go to next or previous letter

	public void upEdge() {
		this.edge++;
	}//Increase the edge

	public void strandSwap() {
		if(this.strand%2 == 0)
			this.strand--;
		else
			this.strand++;
	}
	public void upStrand() {
		this.strand++;
	}
	public void downStrand() {
		this.strand--;
	}
	public void setStrand(int i) {
		this.strand = i;
	}//Increase, decrease, or set the current strand

}
