package simpleClasses;
import java.util.ArrayList;

public class Vminus implements BasisElement{

	private ArrayList<Integer>  circleNumber;
	private int name;
	private boolean sign;

	public Vminus(ArrayList<Integer>  cn){
		this.circleNumber = cn;
		this.name = findName();
		this.sign = false; //-

	}

	public int findName(){//find min number in the circle number

		int min;

		if(circleNumber.size()==0) return 0;

		else{
			min = circleNumber.get(0);
			for (Integer integer : circleNumber) {
				if(min > (int) integer)
					min = (int) integer;
			}
		}
		return min;

	}
	public boolean getSign(){
		return this.sign;
	}

	public ArrayList<Integer>  getCode(){
		return this.circleNumber;
	}

	public int getName(){
		return this.name;
	}

	@Override
	public boolean equals(BasisElement v){

		return this.name==v.getName() && this.sign==v.getSign();
	}

	public String toString(){
		return "V"+this.name+"-";


	}


}