package com.avenuecode.orders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] A = {3,5,6,3,3,5};
		System.out.println(A[A.length - 1]);
		String S = null;
		int count=0;
		Map<Integer, List<Integer>> pairMap = new HashMap<Integer, List<Integer>>();
		List<Integer> indexList;
		for(int i=0; i<A.length; i++) {
			if(pairMap.containsKey(A[i])) {
				List<Integer> existingIndList = pairMap.get(A[i]);
				existingIndList.add(i);
				pairMap.put(A[i], existingIndList);
			}else {
				indexList = new ArrayList<Integer>();
				indexList.add(i);
				pairMap.put(A[i], indexList);
			}
		}
		
			
		for (Map.Entry<Integer, List<Integer>> entry : pairMap.entrySet())
		{
		    System.out.println(entry.getKey() + "/" + entry.getValue().size());
		    if(entry.getValue().size() > 1) {
		    	count += (entry.getValue().size() * (entry.getValue().size() - 1))/2; 
		    }
		    
		}
		
		
		System.out.println(count);
	}

}
