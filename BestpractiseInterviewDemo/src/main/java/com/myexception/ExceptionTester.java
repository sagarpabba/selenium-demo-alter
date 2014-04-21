package com.myexception;

public class ExceptionTester {
	
	
	
	public int tryException(){
			int x=1;
			int y=2;
			try{
			    System.out.println("this is a try statement");
				return x+y;
			}catch(Exception e){
				
			}finally{
				System.out.println("finall run now ");
			}
			 return x;
	}
	
   
}
