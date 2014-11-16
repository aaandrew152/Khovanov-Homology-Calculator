package original;

import java.util.ArrayList;

public class KhovanovRow{
    
    private KhovanovMapNode[] nodes;
    private int index;
    
    public KhovanovRow(int size){
        this.nodes = new KhovanovMapNode[size];
    }
    
    public void add(KhovanovMapNode kmn){
        this.nodes[this.index++]=kmn;
    }
    
    public int size(){
        return this.nodes.length;
        
    }
    
    public int howManyDots(){//goes through all the Nodes and returns how many total basis elements (tensors) are in this row
      int count = 0;
      
      for(KhovanovMapNode kmn: this.nodes) {
        count+=kmn.basisSize();
      }
      
      return count;
    }
    
    public KhovanovMapNode[] getNodes(){
        return this.nodes;
    }
    

    
    public KhovanovMapNode findNode(ArrayList<Integer> code){//returns a node based on its code
        for(KhovanovMapNode kmn:this.nodes){
            if(kmn.getBinCode().equals(code))
                return kmn;
        }
        throw new NullPointerException();
    }
    
    public KhovanovMapNode nthNode(int n){//finds nth node in the array
        return this.nodes[n];
        
            
    }
    
    public String toString(){
        String s="";
        for(KhovanovMapNode kmn: this.nodes){
            s+=(kmn.toString()+"\n");  
        }
        return s;
    }
    
    
}