package com.myclass;

public abstract class MyAbstract {
	
	
	/*
	 * http://blog.sina.com.cn/s/blog_640738130100tshn.html
	 */
	public static int a=3;
	public static int fb(int n){
		if(n==1){
			return 1;
		}else{
			return n*(n-1);
		}
	}
	// abstract method must be inherited ,must be in abstract class or in interface;
	// the good way is to extend the method
	// abstract method is uncompleted method ,need to inherit in child method 
	public abstract void mustInherit();

}
