package edu.nd.se2018.homework.hwk1;
import java.util.ArrayList;

public class Question3 {
	
	public Question3(){}	
	
    public int getMirrorCount(int[] numbers){
    	//check for empty list
    	if(numbers.length == 0) return 0;
    	
    	ArrayList<Integer> seq = new ArrayList<Integer>(); 
    	int start = 0;
    	int end = numbers.length-1;
    	int startIndex = 0; //index of last element in first subset
    	int endIndex = 0; //index of first element in second subset
    	int len = 0; //length of mirrored sequence
    	while(start<numbers.length/2) {
    		if(numbers[start]==numbers[end]) {
    			seq.add(numbers[start]);
    			if (seq.size() > len) {
    				len = seq.size();
    				startIndex = start;
    				endIndex = end;
    			}
    			start++;
    			end--;
    		}
    		else {
    			if (seq.size() > len) {
    				len = seq.size();
    				startIndex = start;
    				endIndex = end;
    			}
    			seq.clear();
    			if (end>=numbers.length/2) end--;
    			else {
    				start++;
    				end = numbers.length-1;
    			}
    		}
    	}

    	//no multi-value mirrored sequence 
    	if (len==0) return 1;
    	//contiguous mirrored sequence
    	else if (startIndex+1==endIndex) return 2*len;
    	//mirrored sequence with common value in middle
    	else if (startIndex+2==endIndex) return (2*len)+1;
    	//non-contiguous mirrored sequence
    	else return len;    	
	}
}
