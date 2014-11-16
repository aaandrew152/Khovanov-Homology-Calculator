package original;
import java.util.*;

import simpleClasses.Arrow;
import simpleClasses.BasisElement;

public class Tensor{

	private ArrayList<Integer> khovanovNodeCode;
	private BasisElement[] tensorProduct;
	private ArrayList<Arrow> outArrows;
	private ArrayList<Arrow> inArrows;
	private boolean isDeleted;

	public Tensor(ArrayList<BasisElement> b, ArrayList<Integer>  code){
		this.tensorProduct = new BasisElement[b.size()];
		this.tensorProduct = b.toArray(this.tensorProduct);
		this.outArrows = new ArrayList<Arrow>();
		this.inArrows = new ArrayList<Arrow>();

		this.khovanovNodeCode = code;
	}

	public int getNumArrows(){
		return this.outArrows.size()+this.inArrows.size();

	}

	public boolean isDeleted(){
		return this.isDeleted;
	}

	public boolean addInArrowModTwo(Arrow a){
		for(Arrow arr: this.inArrows){
			if(arr.getFrom().equals(a.getFrom())){ //if an arrow already exists from ____, delete the existing arrow
				arr.delete();
				return true;
			}
		}
		this.inArrows.add(a);  

		return false;
	}//Returns true if something was deleted, otherwise returns false

	public boolean addOutArrowModTwo(Arrow a){
		for(Arrow arr: this.outArrows){
			if(arr.getTo().equals(a.getTo())){ //if an arrow already exists from ____, delete the existing arrow
				arr.delete(); 
				return true;
			}
		}
		this.outArrows.add(a);  

		return false;
	}//Returns true if something was deleted, otherwise returns false

	public void delete(){
		this.isDeleted = true;
	}

	public ArrayList<Integer> getCode(){
		return this.khovanovNodeCode;
	}

	public void addOutArrow(Arrow a){
		this.outArrows.add(a);
	}

	public void addOutArrows(ArrayList<Arrow> a){
		this.outArrows.addAll(a);
	}

	public void addInArrow(Arrow a){
		this.inArrows.add(a);
	}

	public void addInArrows(ArrayList<Arrow> a){
		this.inArrows.addAll(a);
	}

	public void removeDeletedArrows(){
		for(Arrow a: this.outArrows){
			if(a.isDeleted())
				this.outArrows.remove(a);
		}

		for(Arrow a: this.inArrows){
			if(a.isDeleted())
				this.inArrows.remove(a);
		}
	}

	public ArrayList<Arrow> getOutArrows(){
		return this.outArrows;
	}

	public ArrayList<Arrow> getInArrows(){
		return this.inArrows;
	}


	public boolean contains(BasisElement b){
		boolean flag=false;
		for(BasisElement be:this.tensorProduct){
			if(be.equals(b)){
				flag = true;
				break;
			}   
		}
		return flag;
	}

	public boolean isIdentityMinusOne(Tensor other, BasisElement one){ //does "this" contain everything in "other" besides the "one" element?
		BasisElement[] toTest = other.getElements();
		for(BasisElement b:toTest){
			if(b.equals(one)){}
			else if(!contains(b))
				return false;
		}
		return true;
	}

	public BasisElement[] getElements(){
		return this.tensorProduct;
	}

	public ArrayList<Arrow> getArrowsNotFrom(Tensor from) {
		ArrayList<Arrow> temp = new ArrayList<Arrow>();
		for(Arrow a: this.inArrows) {
			if(!a.isDeleted() && a.getFrom()!=from)
				temp.add(a);
		}
		return temp;
	}

	public ArrayList<Arrow> getArrowsNotTo(Tensor to) {
		ArrayList<Arrow> temp = new ArrayList<Arrow>();
		for(Arrow a: this.outArrows) {
			if(!a.isDeleted() && a.getTo()!=to)
				temp.add(a);
		}
		return temp;
	}

	public void deleteAllOutArrows() {
		for (Arrow arrow : this.outArrows) {
			arrow.delete();
		}
	}//Deletes all arrows going out of this point.

	public void deleteAllInArrows() {
		for (Arrow arrow : this.inArrows) {
			arrow.delete();
		}
	}//Deletes all arrows going into this point.


	public boolean equals(Tensor other){
		BasisElement[] otherElements=other.getElements();
		if(this.tensorProduct.length==otherElements.length){
			for(int i=0; i<this.tensorProduct.length; i++){
				if(!this.tensorProduct[i].equals(otherElements[i])){
					return false;
				}
			}
			if(this.khovanovNodeCode.toString().equals(other.khovanovNodeCode.toString()))
				return true;         
		}
		return false;    
	}

	public String basisToString(){
		String s="";

		for(BasisElement b:this.tensorProduct){
			s+=(b.toString()+" ");
		}       
		return s;
	}

	public String toString(){
		String s=basisToString();
		for(Arrow a:this.outArrows)
			s+=("   "+a.toString());
		s+=("   ")+this.outArrows.size();
		return s;
	}

	public int getTotalPosNeg() {
		int total = 0;//+1 for each positive crossing, -1 for each negative crossing

		for (BasisElement b : this.tensorProduct) {
			if(b.getSign())
				total++;
			else
				total--;
		}

		return total;
	}




}