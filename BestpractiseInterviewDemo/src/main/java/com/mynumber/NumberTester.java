package com.mynumber;

public class NumberTester {
	
	
	
	public int getRandomNumber(int min,int max){
		
		int k=min+(int)(Math.random()*max);
		return k;
	}
	
	public int maxNumber(){
		return Integer.MAX_VALUE;
	}
	
	public int minNumber(){
		return Integer.MIN_VALUE;
	}
	/*
	 * return the most nearly number for  the input number
	 */
	public long myRound(double a){
		return Math.round(a);
	}

}
