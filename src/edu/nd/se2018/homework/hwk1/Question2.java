package edu.nd.se2018.homework.hwk1;
import java.util.HashMap;

public class Question2 {

	public Question2(){}
	
	public String getMostFrequentWord(String input, String stopwords){
		//remove stopwords from input
		String[] inputList = input.split(" ");
		for(int i=0; i<inputList.length; i++) {
			if(stopwords.contains(inputList[i])) {
				inputList[i] = null;
			}
		}
		
		//create HashMap of words in input and their frequency of occurrence 
		HashMap<String,Integer> countWords = new HashMap<String,Integer>();
		for(String word: inputList) {
			if(word==null) continue; 
			if(countWords.containsKey(word)){
				countWords.put(word, countWords.get(word)+1);
			} else {
				countWords.put(word, 1);
			}
		}
		
		//find word with highest frequency value
		int maxCount = 0;
		String returnWord = null;
		boolean lengthMatch = false; //return true if multiple words have highest frequency
		for(String word: countWords.keySet()) {
			if(countWords.get(word) > maxCount) {
				maxCount = countWords.get(word);
				returnWord = word;
				lengthMatch = false;
			}
			else if(countWords.get(word) == maxCount) {
				lengthMatch = true;
			}
		}
		
		if(lengthMatch) return null;
		
		return returnWord;
	}
}


