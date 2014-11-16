package original;
import java.util.ArrayList;

import simpleClasses.BasisElement;
import simpleClasses.Vminus;
import simpleClasses.Vplus;

public class SeifertCircle implements Comparable<SeifertCircle>{
    
    private int minEdge;
    private ArrayList<Integer> code;
    private BasisElement vplus;
    private BasisElement vminus;
    
    public SeifertCircle(ArrayList<Integer> c){
        this.code = c;
        this.vplus = new Vplus(c);
        this.vminus = new Vminus(c);
        
        this.minEdge=findMinEdge();
    }
    
    public int compareTo(SeifertCircle other){
        if(this.code.equals(other.getCode()))
            return 0;
        else
            return 1;
        
    }
    
     public int compareName(SeifertCircle other){
        if(this.minEdge==other.getMinEdge())
            return 0;
        else
            return 1;
        
    }
     
     public int findMinEdge(){//find min number in the circle number
         
         if(this.code.size()==0) return 0;
         
         else{
             int min = this.code.get(0);
             
             for (Integer i : code) {
				if (i < min)
					min = i;
			}
             
             return min;
         }
         
     }
    
    public BasisElement getVplus(){ return this.vplus;}
    public BasisElement getVminus(){ return this.vminus;}
    
    public ArrayList<Integer>  getCode(){ return this.code;}
    public int getMinEdge(){ return this.minEdge;}
        
    
    
    
    
}