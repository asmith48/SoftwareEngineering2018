package edu.nd.se2018.homework.hwk1;
import java.util.HashSet;

public class Question1 {

	public Question1(){}
	
	public int getSumWithoutDuplicates(int[] numbers){
		//add numbers to HashSet to remove duplicates
		HashSet<Integer> set = new HashSet<Integer>();
		for(int i: numbers) {
			set.add(i);
		}
		//sum numbers in HashSet
		int sum = 0;
		for(int i: set) {
			sum = sum + i;
		}
		
		return sum;	
	}

}
