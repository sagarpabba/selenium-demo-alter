package com.hp.test;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.hp.utility.RetryFail;

public class RetryTester {
	
	
	private int a=0;
	@Test
	public void loginmethod(){
		
		
		 a=a+1;
		 if(a>=4){
			 //Reporter.getCurrentTestResult().setStatus(1);
			 Assert.assertTrue(true);
		 }else{
			// Reporter.getCurrentTestResult().setStatus(2);
			 System.out.println(Reporter.getCurrentTestResult().getStatus());
			 Assert.assertTrue(false); 
		 }
		System.out.println("loginmethod run count is:"+a);
	}

}
