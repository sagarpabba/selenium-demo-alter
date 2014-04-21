package com.myorder;

public class MyBubbleSort {
	
	
	
	public int[] sortUp(int[] testarray){
		
		for(int x=0;x<testarray.length-1;x++){
			
			for(int y=0;y<testarray.length-x-1;y++){
				if(testarray[y]>testarray[y+1]){
					int temp=testarray[y];
					testarray[y]=testarray[y+1];
					testarray[y+1]=temp;
					
				}
			}
		}
		
		int[] b=new int[testarray.length];
		for(int k=0;k<testarray.length;k++){
			b[k]=testarray[k];
		}
		return b;
	}

}
