/**
 * Project Name:PAF_Testing_Core
 * File Name:Login_Page.java
 * Package Name:com.hp.pop
 * Date:Sep 27, 20139:29:02 AM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.hp.action;

import java.io.IOException;
import java.util.Calendar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.hp.po.LoginPage;
import com.hp.utility.TimeUtils;


/**
 * ClassName:Login_Page 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 TODO ADD REASON. 
 * Date:     Sep 27, 2013 9:29:02 AM 
 * @author   huchan

 * @since    JDK 1.6
 * @see 	 
 * @version $Revision: 1.0 $
 */
public class LoginPageAction extends LoginPage {
	
	
	
	/**
	 * Constructor for LoginPageAction.
	 * @param driver WebDriver
	 */
	public LoginPageAction(WebDriver driver) {
		this.driver=driver;
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Method verifyPageElements.
	 * @param pagename String
	 * @throws IOException
	 */
	@Override
	public void verifyPageElements(String pagename) throws IOException {
		// TODO Auto-generated method stub
		super.verifyPageElements(pagename);
		//Date:Sep 27, 20139:29:37 AM
		comments="Verify Page Elements in Login Page";
		status="Passed";
	}
    
	/**
	 * Method open.
	 * @param testedURL String
	 * @throws Exception
	 */
	@Override
	public  void open(String testedURL) throws Exception {
		// TODO Auto-generated method stub
		 super.open(testedURL);
		//Date:Sep 27, 20139:51:12 AM
		 status="Passed";
		 comments="Login URL is:"+testedURL;
		 comment_executionstart=TimeUtils.getCurrentTime(Calendar.getInstance().getTime());
		 comment_paf_url=testedURL;
		 
		 reporterNewStep("Login Page", "Verify URL can be opened", status, comments, driver);
		 
	}
	
	// log in the system using the valid credential
	
	/**
	 * Method logHomePage.
	 * @param username String
	 * @param inputpassword String
	 * @return HomePageAction
	 * @throws IOException
	 */
	public HomePageAction logHomePage(String username, String inputpassword) throws IOException {
			if (isLoginPage()) {
				logger.info("User now is logging the PAF,input username and password......");
				clearAndTypeString(emailinput, username);
				clearAndTypeString(password, inputpassword);
				status="Passed";
				comments="Had Input Username:"+username+",Password:"+inputpassword;
				clickElement(submitbtn);		
		     }
			comment_login_credential=username;
			
			reporterNewStep("", "Verify Login User's Credential", status, comments, driver);
		    return PageFactory.initElements(driver, HomePageAction.class);
	}

	// verify the user is logged before
	/**
	 * Method isLoginPage.
	 * @return boolean
	 */
	public boolean isLoginPage() {
		// driver.findElement(By.)
		boolean bemail = isElementPresentAndDisplay(emailinput);
		boolean bpass = isElementPresentAndDisplay(password);
		boolean bsubmit =isElementPresentAndDisplay(submitbtn);
		int size = content.size();
		logger.info("Find the content size are :" + size);
		if (bemail && bpass && bsubmit && (size > 0)) {
			logger.info("Now we are in the login page,So we will input the username and password to log in home page");
			return true;
		} else {
			status="Failed";
			comments="Expected these Elements("+emailinput+password+submitbtn+") Displayed in the page but not find them";
			logger.info("We cannot find some element matched the login page ,so we may in the home page already");
			return false;
		}
	}


}

