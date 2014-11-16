package original;
import java.util.*;

import simpleClasses.Arrow;
import simpleClasses.DotRow;

public class DotsAndArrows{

	private KhovanovMap khmap;
	public DotRow[] rows;
	private ArrayList<Arrow> arrows;

	public DotsAndArrows(KhovanovMap k){
		this.khmap = k;
		this.rows = new DotRow[this.khmap.getNumRows()];
		this.arrows = new ArrayList<Arrow>();
		fillRows();
		fillArrows();
		resolve();
		output();
	}

	public void fillRows(){
		//fill the rows with all the dots (tensors)
		KhovanovRow[] krows = this.khmap.getKhovanovRows(); 
		KhovanovMapNode[] nodes;
		Tensor[] basis;

		for(int i=0; i<krows.length; i++){
			this.rows[i] = new DotRow(krows[i].howManyDots());
			nodes = krows[i].getNodes();
			for(KhovanovMapNode k: nodes){
				basis = k.getBasis();
				for(Tensor t: basis){
					this.rows[i].add(t);                    
				}
			}
		}  
	}

	public void fillArrows(){
		Tensor[] basis;

		for(DotRow d: this.rows){
			basis = d.getDots();
			for(Tensor t: basis){
				this.arrows.addAll(t.getOutArrows());
			}
		}
	}

	public void debug() {
		for (int i = 0; i < this.rows.length; i++) {
			System.out.println("Row " +i);
			System.out.println(rows[i].toString());
		}
	}

	public boolean listNotEmpty() {
		for (Arrow a: this.arrows) {
			if(!a.isDeleted())
				return true;
		}
		return false;
	}

	public int numInList() {
		int count = 0;
		for(Arrow a: this.arrows) {
			if(!a.isDeleted())
				count++;
		}
		return count;
	}
	public void resolve() {
		Arrow current;
		Tensor to;
		Tensor from;
		ArrayList<Arrow> toArrows;
		ArrayList<Arrow> fromArrows;
		
		
		while (listNotEmpty()) {
			int counter = 0;
			while(this.arrows.get(counter).isDeleted())
				counter++;
			current = this.arrows.get(counter);
			to = current.getTo();
			from = current.getFrom();
			
			
			toArrows = to.getArrowsNotFrom(from);
			fromArrows = from.getArrowsNotTo(to);
			
			Arrow temp;
			boolean fromDeleted;
			boolean toDeleted;
			
			for(Arrow a: toArrows) {
				for(Arrow b: fromArrows){
					temp = new Arrow(a.getFrom(), b.getTo());
					fromDeleted = a.getFrom().addOutArrowModTwo(temp);
					toDeleted = a.getTo().addInArrowModTwo(temp);
					assert ((fromDeleted && toDeleted) || (!fromDeleted && !toDeleted));
					//Either the arrow should be deletable at both ends, or it should happen at neither end
					if(fromDeleted && toDeleted)
						removeArrow(temp);
					else {
						this.arrows.add(temp);
						temp.getFrom().addOutArrow(temp);
						temp.getTo().addInArrow(temp);
					}
				}
			}
			
			to.deleteAllOutArrows();
			from.deleteAllInArrows();
			
			for(Arrow a: toArrows) {
				a.delete();
			}
			
			for(Arrow a: fromArrows) {
				a.delete();
			}
			
			to.delete();
			from.delete();
			
			removeArrow(current);
		}
	}
	public boolean addArrowModTwo(Arrow addingThis) {
		for (Arrow arrow : arrows) {
			if(arrow.equals(addingThis)&&!arrow.isDeleted()) {
				arrow.delete();
				return true;
			}
		}
		return false;			
	}//Returns false if no such arrow was deleted, otherwise returns true and deletes the arrow

	public void removeArrow(Arrow a) {

		for(Arrow arrow: this.arrows)

			if(a.getFrom() == arrow.getFrom() && a.getTo() == arrow.getTo()) {
				arrow.delete();
			}
	}

	public void output(){
		for(int i=0; i<this.rows.length; i++){
			System.out.println("Row "+i+" has "+this.rows[i].numDotsRemaining()+" dots remaining");
		}
	}

}