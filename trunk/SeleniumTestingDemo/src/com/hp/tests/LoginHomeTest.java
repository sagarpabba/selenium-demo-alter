package com.hp.tests;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.hp.pop.Login_Page;
import com.hp.utility.NiceBaseDriver;
import com.hp.utility.Resulter;
import com.hp.utility.RetryFail;
import com.hp.utility.SeleniumCore;

public class LoginHomeTest extends NiceBaseDriver {

	private String username;
	private String password;
	@Test(description = "Login in the PAF home page ,using the username and password", testName = "Login in PAF", alwaysRun = true, successPercentage = 10, groups = "logininterface")
	public void test_loginInterface() {

		Map<String, String> mapdata = SeleniumCore
				.importDataTable("login_page");
		String testedURL = mapdata.get("Base_URL");
		username = mapdata.get("LOG_Username");
	    password = mapdata.get("LOG_Password");
		// open the login page
		SeleniumCore.OpenURL(driver, testedURL);
		logger.info("We open the login page to input the username and password......");

		

	}
	@Test(retryAnalyzer=RetryFail.class,dependsOnMethods="test_loginInterface")
	public void loginHome(){
		// init all the used pages
				Login_Page loginpage = PageFactory.initElements(driver,
						Login_Page.class);

				Resulter.log("START_TIME", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(Calendar.getInstance().getTime()));

				// log the home page as a valid username and password
				loginpage.logAsValidUser(username, password);
	}

}
