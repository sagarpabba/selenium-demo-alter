package com.hp.runner;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.hp.baserunner.BaseSeleniumDriver;
import com.hp.baserunner.RetryFail;
import com.hp.pop.DemoPage;

public class DemoRun extends BaseSeleniumDriver {

	private static Logger log=Logger.getLogger(DemoRun.class);
	
	@Test(retryAnalyzer=RetryFail.class)
	public void demoTest()
	{
		driver.get("http://www.google.com");
		DemoPage dp=new DemoPage();
		dp.demoTest();
	}
	@Test
	public void demoTest2()
	{
		driver.get("www.baidu.com");
		DemoPage dp2=new DemoPage();
		dp2.demoTest2();
	}
}
