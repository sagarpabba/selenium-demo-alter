package com.myrecursion;

public class Recursion {

	
	//1£¬1£¬2£¬3£¬5£¬8......
	public static int fb(int n){
		if(n==1){
			return 1;
		}else{
			return n*(n-1);
		}
	}
	//Fibonacci 1,1,2,3,5,8
	public static int getvalue(int n){
		if(n==1||n==2){
			return 1;
		}else{
			return getvalue(n-1)*getvalue(n-2);
		}
	}
	
	//also used for creating the directory iteractor
}
