/**
 * Project Name:PAF_Testing_Core
 * File Name:Login_Page.java
 * Package Name:com.hp.pop
 * Date:Sep 27, 20139:29:02 AM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.hp.pop;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.hp.utility.SeleniumCore;
import com.hp.utility.TimeUtils;


/**
 * ClassName:Login_Page 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 TODO ADD REASON. 
 * Date:     Sep 27, 2013 9:29:02 AM 
 * @author   huchan
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class Login_Page extends PageObject {
	
	
	@FindBy(how = How.XPATH, using = "//*[@name='USER']")
	private WebElement emailinput;
	@FindBy(how = How.XPATH, using = "//*[@name='PASSWORD']")
	private WebElement password;
	@FindBy(how = How.XPATH, using = "//*[@id='Login']/div[2]/input")
	private WebElement submitbtn;
	@FindAll({ @FindBy(className = "ft-text") })
	private List<WebElement> content;
	
	

	public Login_Page(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void verifyPageElements(String pagename) throws IOException {
		// TODO Auto-generated method stub
		super.verifyPageElements(pagename);
		//Date:Sep 27, 20139:29:37 AM
		comments="Verify Page Elements in Login Page";
		status="Passed";
	}
    
	@Override
	public  void open(String testedURL) throws Exception {
		// TODO Auto-generated method stub
		 super.open(testedURL);
		//Date:Sep 27, 20139:51:12 AM
		 status="Passed";
		 comments="Login URL is:"+testedURL;
		 comment_executionstart=TimeUtils.getCurrentTime(Calendar.getInstance().getTime());
		 comment_paf_url=testedURL;
		 
		 SeleniumCore.generateEmailStep("Login Page", "Verify URL can be opened", status, comments, driver);
		 
	}
	
	// log in the system using the valid credential
	
	public Home_Page logHomePage(String username, String inputpassword) throws IOException {
			if (isLoginPage()) {
				logger.info("User now is logging the PAF,input username and password......");
				clearAndTypeString(emailinput, username);
				clearAndTypeString(password, inputpassword);
				status="Passed";
				comments="Had Input Username:"+username+",Password:"+inputpassword;
				clickElement(submitbtn);		
		     }
			comment_login_credential=username;
			
			SeleniumCore.generateEmailStep("", "Verify Login User's Credential", status, comments, driver);
		    return PageFactory.initElements(driver, Home_Page.class);
	}

	// verify the user is logged before
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

