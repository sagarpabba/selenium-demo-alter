package com.myPolymorphism;

/**
* @ClassName: MySingleton
* @Description: TODO
* @author alterhu2020@gmail.com
* @date Apr 21, 2014 11:06:09 AM
* 
*/

public class MySingleton {
	
	private static MySingleton ton=null;
	
	public static synchronized MySingleton getInstance(){
		
		if(ton==null){
			ton=new MySingleton();
		}
		return ton;
	}
	
	
	public int add(int x, int y){
		return x+y;
	}

}
