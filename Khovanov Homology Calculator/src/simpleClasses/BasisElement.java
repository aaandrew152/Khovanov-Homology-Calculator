package simpleClasses;
import java.util.ArrayList;

public interface BasisElement{
    
   public String toString();
   
   public ArrayList<Integer> getCode();
   public boolean getSign();
   public int getName();
   
   public boolean equals(BasisElement v);
    
    
}