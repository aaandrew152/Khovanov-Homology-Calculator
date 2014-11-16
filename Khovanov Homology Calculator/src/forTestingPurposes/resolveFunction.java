package forTestingPurposes;

import java.util.ArrayList;

import simpleClasses.Arrow;

public @interface resolveFunction {
	/*
	 * public void resolve() {
		for (Arrow arrow : arrows) {
			System.out.println(arrow.toString());
		}

		while (listNotEmpty()) {
			int counter = 0;
			while(this.arrows.get(counter).isDeleted())//Goes through arrows until one arrow isn't deleted
				counter++;
			Arrow current = this.arrows.get(counter);//Current arrow under consideration
			Tensor to = current.getTo();//To of the current arrow
			Tensor from = current.getFrom();//From of the current arrow


			ArrayList<Arrow> toArrows = to.getArrowsNotFrom(from);//Arrows going to target, which are not the current arrow
			ArrayList<Arrow> fromArrows = from.getArrowsNotTo(to);//Arrows from originator, which are not the current arrow

			System.out.println("Next Iteration");
			for (Arrow arrow : toArrows) {
				if(!arrow.isDeleted())
					System.out.println("TO ARROW " + arrow.toString());
			}
			for (Arrow arrow : fromArrows) {
				if(!arrow.isDeleted())
					System.out.println("FROM ARROW " + arrow.toString());
			}
			for (Arrow arrow : this.arrows) {
				if(!arrow.isDeleted())
					System.out.println(arrow.toString());
			}
			output();
			
			
			for(Arrow a: toArrows) {
				for(Arrow b: fromArrows){
					Arrow temp = new Arrow(a.getFrom(), b.getTo());//Temporary arrow, which will be added to the list mod2
					boolean fromDeleted = a.getFrom().addOutArrowModTwo(temp);//Is the from arrow deleted?
					a.getTo().addInArrowModTwo(temp);//Is the to arrow deleted?
					if(fromDeleted)
						removeArrow(temp);//If from and to are deleted this arrow can be deleted.
					else
						this.arrows.add(temp);//Otherwise this arrow still exists
				}
			}

			to.deleteAllOutArrows();//Delete all arrows going out from the target
			from.deleteAllInArrows();//Delete all arrows going into the originator

			for(Arrow a: toArrows) {
				a.delete();
			}//Delete all toArrows

			for(Arrow a: fromArrows) {
				a.delete();
			}//Delete all fromArrows

			to.delete();//Delete the target
			from.delete();//Delete the originator
			
			for (Arrow arrow : fromArrows) {
				if(arrow.getTo().isDeleted()||arrow.getFrom().isDeleted())
					arrow.delete();
			}

			removeArrow(current);		
		}//Continues through every arrow until they have all been deleted
	}
	
	public void resolve() {
		int runThrough = 0;

		while (listNotEmpty()) {
			System.out.println(runThrough);
			runThrough++;
			for (Arrow arrow : arrows) {
				if(!arrow.isDeleted())
					System.out.println(arrow.toString());
			}

			int counter = 0;
			while(this.arrows.get(counter).isDeleted())//Goes through arrows until one arrow isn't deleted
				counter++;
			Arrow current = this.arrows.get(counter);//Current arrow under consideration

			ArrayList<Arrow> arrowsToTarget = current.getTo().getArrowsNotFrom(current.getFrom());
			ArrayList<Arrow> arrowsFromOriginator = current.getFrom().getArrowsNotTo(current.getTo());

			for (Arrow origArrow : arrowsFromOriginator) {
				for (Arrow targetArrow : arrowsToTarget) {
					if(origArrow.isDeleted()||targetArrow.isDeleted())
						throw new NullPointerException();//Neither of these arrows should be deleted
					Arrow addingThisModTwo = new Arrow(origArrow.getTo(),targetArrow.getFrom());
					boolean removed = addArrowModTwo(addingThisModTwo);
					if(!removed)
						arrows.add(addingThisModTwo);
				}
			}//Goes through and creates maps between all instances of originators and targets		

			current.getFrom().deleteAllInArrows();
			current.getTo().deleteAllOutArrows();//Deletes all arrows which went into from, or out of to

			current.getFrom().delete();
			current.getTo().delete();//Deletes the now isolated tensors

			for (Arrow arrow : arrows) {
				if(arrow.getFrom().isDeleted()||arrow.getTo().isDeleted())
					arrow.delete();
			}

			current.delete();//Deletes the arrow current

			correctForDuplicates();

		}//Continues through every arrow until they have all been deleted
	}
	 */
}
