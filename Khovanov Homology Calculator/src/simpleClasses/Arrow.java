package simpleClasses;

import original.Tensor;

public class Arrow{
    
    private Tensor from;
    private Tensor to;
    private boolean deleted; //for the dots and arrows
    
    public Arrow(Tensor f, Tensor t){
        this.from = f;
        this.to = t;
        //addToInOutArrows();
    }
    
    public Tensor getFrom(){return this.from;}
    public Tensor getTo(){return this.to;}
    
    public void delete(){
        this.deleted = true;
    }
    
    //we'll see about this method.....
    public boolean equals(Arrow other){
        return this.from.equals(other.getFrom()) && this.to.equals(other.getTo());
    }
    
    public boolean isDeleted(){
        return this.deleted;
    }
    
    public String toString(){
        return "From (" + from.basisToString() + ") To (" + to.basisToString() + ")."+"in "+to.getCode();
    }
    
    public void addToInOutArrows() {
    	from.addInArrow(this);
    	to.addOutArrow(this);
    }
 
    
    
    
    
    
}