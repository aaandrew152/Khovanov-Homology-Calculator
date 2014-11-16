package original;
import java.util.*;

import simpleClasses.ArrayListUtils;
import simpleClasses.BasisElement;

public class KhovanovMapNode{
    
    private ArrayList<Integer> binarycode = new ArrayList<Integer>();
    private ArrayList<SeifertCircle> circles;
    
    private DMap[] outMaps;
    private DMap[] inMaps;
     //keeps track of last positions so we know where to add new maps 
    private int outcount;
    private int incount;        
    
    private Tensor[] basis;
    //private int tensorcount; //num of tensors inputted
    
    
    
    public KhovanovMapNode(ArrayList<Integer> bcode, ArrayList<SeifertCircle> circ){
        this.circles = circ;
        this.binarycode = bcode; 
        
        this.basis = new Tensor[(int) Math.pow(2,this.circles.size())];
        
        this.outMaps = new DMap[ArrayListUtils.countMatches(this.binarycode, 0)];
        this.inMaps = new DMap[ArrayListUtils.countMatches(this.binarycode, 1)];
        makeBasis();
    }
    
    public void makeBasis(){
        String instructions;
        String formatPattern = "%" + this.circles.size() + "s";
        ArrayList<BasisElement> theTensor;

        for(int i=0; i<this.basis.length; i++){ //all binary combinations from 0 to basis.length (example 000,001,...,111)
            instructions = String.format(formatPattern, Integer.toBinaryString(i)).replace(' ', '0');
            theTensor = new ArrayList<BasisElement>();
            for(int j=0; j<instructions.length(); j++){
                
                if(instructions.charAt(j)=='0')
                    theTensor.add(this.circles.get(j).getVplus());
                else
                    theTensor.add(this.circles.get(j).getVminus());  
            }
            this.basis[i] = new Tensor(theTensor, this.binarycode);
        }
  
    }
    
    public void addOutMap(DMap out){
        this.outMaps[outcount++]=out;
    }
    
    
    public void addInMap(DMap in){
        this.inMaps[incount++]=in;
    }
    
    public ArrayList<Integer> getBinCode(){
        return this.binarycode;   
    }
    
    public ArrayList<SeifertCircle> getCircles(){
        return this.circles;
    }
   
    
    
    public Tensor[] getBasis(){
        return this.basis;
    }
    
    public int basisSize(){
        return this.basis.length;
    }
    
    public int numCircles(){
        return this.circles.size();
    }
    
    public boolean containsCircle(SeifertCircle other){
        for(SeifertCircle sc:this.circles){
            if(sc.compareTo(other)==0)
                return true;
        }
        return false;
        
    }
    
    public ArrayList<SeifertCircle> findCircleMatch(SeifertCircle sc){
        ArrayList<SeifertCircle> matches = new ArrayList<SeifertCircle>();
        for(SeifertCircle s:this.circles){
            if(s.compareTo(sc)==0)
                matches.add(s);
        }
        
        return matches; 
    }
    
    public ArrayList<SeifertCircle> findDifferentCircles(KhovanovMapNode other){ //find the circles in THIS which DONT HAVE A MATCH in OTHER
        ArrayList<SeifertCircle> different = new ArrayList<SeifertCircle>();
        for(SeifertCircle s:this.circles){
            if(!other.containsCircle(s)){
                different.add(s);
            }
        }
        
        return different; 
    }
    
    
    public ArrayList<ArrayList<Integer>> outMaps(){
        ArrayList<ArrayList<Integer>> out = new ArrayList<ArrayList<Integer>>();
        
        for(int j=1; j<=this.outMaps.length; j++){
            //find the "Jth" 0 and change it to a 1
            int n = nthOccurrence(this.binarycode,0,j);

            ArrayList<Integer> toCode = new ArrayList<Integer>();
            toCode.addAll(this.binarycode.subList(0,n));
            toCode.add(1);
            if(this.binarycode.size() > n+1)
            	toCode.addAll(this.binarycode.subList(n+1,binarycode.size()));
            
            out.add(toCode); //can combine this with two lines above after debugging 
        } 
        return out;
    }
    
    public static int nthOccurrence(ArrayList<Integer> code, Integer soughtNumber, int j) { //helper function for the binary code out maps
        int instances = 0;//Number of times that the soughtNumber has been found
        int position = 0;
        
    	for (Integer integer : code) {
			if(integer == soughtNumber)
				instances++;
			if(instances == j)
				break;
			position++;
		}
    	
    	/*int position = code.indexOf(c, 0);
        while (--n > 0 && position != -1)
            position = code.indexOf(c, position+1);*/
        return position;
    }
    
    public String toString(){
        String s="Binary Code:   "+this.binarycode+"\n";
        //for(int i=0; i<this.outcount; i++)
          //  s+=(this.outMaps[i].toString()+"\n");
        for(int i=0; i<this.basis.length; i++)
            s+=(this.basis[i].toString()+"\n");
        return s;
        
    }
    
    
    
    
    
}