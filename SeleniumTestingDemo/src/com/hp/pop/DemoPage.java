package com.hp.pop;

import org.testng.Assert;

public class DemoPage {

	
	
	public String demoTest()
	{
        int count=0;
        System.out.println("this is the test :"+String.valueOf(count++));
		Assert.assertEquals("1", "2");
		
	
		return "string";
	}
	
	public void demoTest2()
	{
		int count=0;
		System.out.println("the time is for test case is :"+(count++));
		Assert.assertEquals("2", "2");
		
	}
	
	public void demoTest3()
	{
		
	}
}
