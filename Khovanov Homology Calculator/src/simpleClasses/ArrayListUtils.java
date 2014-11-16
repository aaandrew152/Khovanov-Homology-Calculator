package simpleClasses;

import java.util.ArrayList;

public class ArrayListUtils {


	
	public static int countMatches(ArrayList<Integer> binaryCode, Integer k) {
		int numMatches = 0;
		
		for (Integer integer : binaryCode) {
			if(integer == k)
				numMatches++;
		}
		
		return numMatches;
	}
}
