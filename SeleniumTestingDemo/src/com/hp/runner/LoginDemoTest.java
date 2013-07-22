package com.hp.runner;


import java.util.Map;

import org.testng.annotations.Test;
import org.apache.log4j.Logger;
import org.testng.Assert;
import com.hp.baserunner.BaseDriver;

public class LoginDemoTest extends BaseDriver{

	
	private static final Logger log=Logger.getLogger(LoginDemoTest.class);
	//@Parameters({ "username","password" })
	@Test
    public void testLogin()
    		//String username,String password) 
	{
	//	log.info("username:"+username+",password"+password);
		//log.info("testurl"+testUrl);
	//	LoginPage homePage = PageFactory.initElements(driver, LoginPage.class);
	//	homePage.search(query);
		
		//wait until the search results exist
	//	driver.findElement(By.id("ires"));

			//assertEquals(3,4);
	    Assert.assertTrue(2==3);
	    log.info("this is method 1:"+Thread.currentThread().getStackTrace()[2].getMethodName());
	}
	
//	@Test(dataProviderClass=com.hp.dataproviders.ExcelDataProivderLoginSheet.class,dataProvider="getPostiveLine")
//	public void testlog2(Map<String,String> loguser)
//	{
//		
//	}

}
