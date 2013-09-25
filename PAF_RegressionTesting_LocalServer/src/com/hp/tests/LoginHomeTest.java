package com.hp.tests;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.hp.pop.Login_Page;
import com.hp.utility.BaseDriver;
import com.hp.utility.RetryFail;
import com.hp.utility.SeleniumCore;

public class LoginHomeTest extends BaseDriver {

	private String username;
	private String password;
	
	@Test(description = "Login in the PAF home page ,using the username and password", testName = "Login in PAF", alwaysRun = true, successPercentage = 10, groups = "logininterface")
	public void test_loginInterface() throws Exception {

		Map<String, String> mapdata = SeleniumCore
				.importDataTable("login_page");
		String testedURL = mapdata.get("Base_URL");
		username = mapdata.get("LOG_Username");
	    password = mapdata.get("LOG_Password");
		// open the login page
		SeleniumCore.OpenURL(driver, testedURL);
		if(SeleniumCore.getBrowserType(driver).contains("internet explorer")){
		  driver.navigate().to("javascript:document.getElementById('overridelink').click()");
		}
		SeleniumCore.sleepSeconds(8);
		logger.info("We open the login page to input the username and password......");
        SeleniumCore.generateEmailStep("Login PAF interface", "Go to the PAF Login Interface", "Passed", "Login URL is:"+testedURL, driver);
		

	}
	@Test(retryAnalyzer=RetryFail.class,dependsOnMethods="test_loginInterface")
	public void loginHome() throws Exception{
		// init all the used pages
				Login_Page loginpage = PageFactory.initElements(driver,
						Login_Page.class);
				// log the home page as a valid username and password
				loginpage.verifyPageElements("PAF Login Interface Page");
				loginpage.logAsValidUser(username, password);
/******************************************************************************/
				SeleniumCore.generateEmailData("Login User Credential", username);
/*******************************************************************************/

	}

}
