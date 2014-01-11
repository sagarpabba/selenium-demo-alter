package com.hp.test;

import java.util.Map;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.hp.action.LoginPageAction;
import com.hp.utility.BaseDriver;
import com.hp.utility.RetryFail;
import com.hp.utility.SeleniumCore;

/**
 */
public class LoginHomeTest extends BaseDriver {

	private String username;
	private String password;
	
	public static LoginPageAction loginpage;
	private String testedURL;
	
	/**
	 * Method test_loginInterface.
	 * @throws Exception
	 */
	@Test(description = "Login PAF Home Page", testName = "Login in PAF", alwaysRun = true, successPercentage = 10, groups = "logininterface")
	public void test_loginInterface() throws Exception {

		Map<String, String> mapdata = SeleniumCore
				.importDataTable("login_page");
		testedURL= mapdata.get("Base_URL");
		username = mapdata.get("LOG_Username");
	    password = mapdata.get("LOG_Password");
		// open the login page
	    loginpage = PageFactory.initElements(driver,
				LoginPageAction.class);
		loginpage.open(testedURL);
		if(SeleniumCore.getBrowserType(driver).contains("internet explorer")){
		  driver.navigate().to("javascript:document.getElementById('overridelink').click()");
		}
		logger.info("We open the login page to input the username and password......");	
		
	}
	/**
	 * Method loginHome.
	 * @throws Exception
	 */
	@Test(retryAnalyzer=RetryFail.class,dependsOnMethods="test_loginInterface")
	public void loginHome() throws Exception{

		// log the home page as a valid username and password
		loginpage.verifyPageElements("PAF Login Interface Page");
		loginpage.logHomePage(username, password);
	  }

}
