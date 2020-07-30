package hr.fer.zemris.java.hw06.observer1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {
	
	public static void main(String[] args) {
		
		List<Integer> l = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			l.add(i);
		}
		
		Iterator<Integer> i1 = l.iterator();
		while(i1.hasNext()) {
			i1.next();
			Iterator<Integer> i2 = l.iterator();
			while(i2.hasNext()) {
				Integer i = i2.next();
				if(i == 2) {
					i2.remove();
				}
			}
			
		}
		
	}

}
