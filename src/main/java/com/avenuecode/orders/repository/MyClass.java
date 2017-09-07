package com.avenuecode.orders.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MyClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//creating sample Collection
				List<Integer> myList = new ArrayList<Integer>();
				for(int i=0; i<10; i++) myList.add(i);
				
				myList.forEach(new Consumer<Integer>() {

					public void accept(Integer t) {
						System.out.println("forEach anonymous class Value::"+t);
					}

				});

	}

}
