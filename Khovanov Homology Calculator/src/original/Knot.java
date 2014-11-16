package original;
import java.util.*;

import simpleClasses.Vertex;

public class Knot{
    private Crossing[] crossings;
    private Vertex[] vertices;
    public ArrayList<Integer> binarycode = new ArrayList<Integer>();
    private Knot leftknot;
    private Knot rightknot;
    private ArrayList<SeifertCircle> seifertCircles;
    private Smoothing smoothing;
    
   
    public Knot(Crossing[] c){ //initial Knot
        this.crossings = c;   
        this.seifertCircles = new ArrayList<SeifertCircle>();
        
        //initialize vertices array
        int max=2*c.length;
        
        this.vertices = new Vertex[max];
        for(int j=0; j<max; j++){//make the vertices
            this.vertices[j] = new Vertex(j+1);
        }
        
        for(int j=0; j<max; j++){//fill their pointers
            this.vertices[j].setPointers(this.vertices[(j-1+max)%(max)], this.vertices[(j+1+max)%(max)]); //mod to wrap around   
        }
        
        makeCircles();

    }
    
    public Knot(Crossing[] c, ArrayList<Integer> code, Smoothing l, Vertex[] v){ //sc = seifertcircles
       
        this.seifertCircles = new ArrayList<SeifertCircle>();
        //this.seifertCircles.addAll(sc);
        
        this.crossings = new Crossing[c.length];
        for(int i=0; i<c.length; i++){ //PASS BY VALUE!!
            this.crossings[i] = new Crossing(c[i].get1st(), c[i].get2nd(), c[i].get3rd(), c[i].get4th(), c[i].getSign()); 
        }
        
        this.vertices = new Vertex[v.length];//PASS BY VALUE!!
        for(int i=0; i<v.length; i++){ //make the vertices
            this.vertices[i] = new Vertex(i+1);
        }
        
        for(int i=0; i<v.length; i++){ //fill their pointers
            this.vertices[i].setPointers(this.vertices[v[i].getFrom().getNum()-1],this.vertices[v[i].getTo().getNum()-1]);
        }
       
        
        this.binarycode = code;
        this.smoothing = l;
        //System.out.print(this.binarycode+": "+this.smoothing.toString());
        //reduce smoothing numbers
        /*for(int i=0; i<this.crossings.length; i++){  //go through all crossings and renumber
            this.crossings[i].reduce(smoothing);
            System.out.print(" Crossing #"+(i+1)+": "+this.crossings[i].toString());
        }*/
        //System.out.println("");
        //ArrayList<SeifertCircle> circlesToAdd=this.smoothing.detectCircles();
        //if(!circlesToAdd.isEmpty())
           // this.seifertCircles.addAll(circlesToAdd); //add circles from smoothing
        
        //Reposition pointers
         
            
        
        //I have to do all these minus ones because the array is from 0 to 5 and the vertices go from 1 to 6
        if(!this.smoothing.needOrientationReorder()){
            //System.out.println(this.smoothing.getOne() + " " + this.smoothing.getTwo() + " " + this.smoothing.getThree() + " " + this.smoothing.getFour());
            this.vertices[this.smoothing.getOne()-1].setTo(this.vertices[this.smoothing.getTwo()-1]);
            this.vertices[this.smoothing.getTwo()-1].setFrom(this.vertices[this.smoothing.getOne()-1]);
            
            this.vertices[this.smoothing.getThree()-1].setTo(this.vertices[this.smoothing.getFour()-1]);
            this.vertices[this.smoothing.getFour()-1].setFrom(this.vertices[this.smoothing.getThree()-1]);
        }
        else{
            Vertex current = this.vertices[this.smoothing.getTwo()-1];
            Vertex prev = this.vertices[this.smoothing.getOne()-1];
            prev.setTo(current);
            
            while(current.getNum()!=this.smoothing.getThree() && current.getNum()!=this.smoothing.getFour()){
                current.setTo(current.getFrom());
                current.setFrom(prev);
                prev = current;
                current = current.getTo();  
            }
            
            if(current.getNum()==this.smoothing.getThree()){
                current.setTo(this.vertices[this.smoothing.getFour()-1]);
                this.vertices[this.smoothing.getFour()-1].setFrom(current);
            }
            
            else{
                current.setTo(this.vertices[this.smoothing.getThree()-1]);
                this.vertices[this.smoothing.getThree()-1].setFrom(current);
            }
            
            current.setFrom(prev);
              
        }
        
        //Fix Crossings
        int flag;
        for(Crossing cross: this.crossings){
            flag=0;
            if(this.vertices[cross.get1st()-1].getTo().getNum()!=cross.get2nd()){
                cross.swapOverstrand();
                flag++;
            }
            if(this.vertices[cross.get3rd()-1].getTo().getNum()!=cross.get4th()){
                cross.swapUnderstrand();
                flag++;
            }
            if(flag==1){
                cross.swapSign();  
            }       
        }
        
        /*System.out.println(this.binarycode+"     "+this.smoothing.toString());
        for(int i=0; i<v.length; i++){
            System.out.println(this.vertices[i].toString());
        }*/
        
        makeCircles();
        
        
        /*for(Vertex vert:this.vertices){
            System.out.println(vert.toString());
            
        }*/
    }
    
    public int numCrossings(){ return this.crossings.length;}
    public ArrayList<Integer> getBinCode(){ return this.binarycode;}
    public Knot getLeftKnot(){ return this.leftknot;}
    public Knot getRightKnot(){ return this.rightknot;}
        
    
    public void resolveknot(){
        if(this.crossings.length==0){
            return;
        }
        else{
            //what crossing we are resolving
            Crossing current = this.crossings[0];
            //System.out.println("CURRENT CROSSING: "+current.toString());
            Crossing[] newcrossings = new Crossing[this.crossings.length-1];   //make next level's crossing array; one less because we resolved a crossing
            for(int i=1; i<crossings.length; i++){                                //fill up the new array
                newcrossings[i-1] = this.crossings[i];
            }            
            
            ArrayList<Integer> leftBinaryCode = new ArrayList<Integer>();
           	leftBinaryCode.addAll(this.binarycode);
            leftBinaryCode.add(0);
            
            ArrayList<Integer> rightBinaryCode = new ArrayList<Integer>();
            rightBinaryCode.addAll(this.binarycode);
            rightBinaryCode.add(1);
            
            this.leftknot=new Knot(newcrossings, leftBinaryCode, current.getZeroSmoothing(), this.vertices);
            this.rightknot=new Knot(newcrossings, rightBinaryCode, current.getOneSmoothing(), this.vertices);
            this.leftknot.resolveknot();
            this.rightknot.resolveknot();
        }
    }
    
    public void makeCircles(){
        ArrayList<Integer> code;
        Vertex current;
        for(Vertex vert: this.vertices){
            code = new ArrayList<Integer>();
            current = vert;
            while(!current.wasVisited()){
                code.add(current.getNum());
                current.visit();
                current=current.getTo();
                
            }

            if(!code.isEmpty()){
                this.seifertCircles.add(new SeifertCircle(code));
            }
        }    
    }
    
    public ArrayList<SeifertCircle> getCircles(){ return this.seifertCircles;}
    
    public void output(){
        if(this.leftknot==null){
            String s = "";
            for(SeifertCircle c: this.seifertCircles){
                s+=c.getCode()+"    ";                
            }
        
            System.out.println(this.binarycode+": "+this.seifertCircles.size() + "    " + s);
        }
        else{
            this.leftknot.output();
            this.rightknot.output();
        }
    }

}

