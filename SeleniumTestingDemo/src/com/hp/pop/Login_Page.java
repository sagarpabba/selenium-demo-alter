package com.hp.pop;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.hp.utility.Resulter;
import com.hp.utility.SeleniumCore;

public class Login_Page {

	private Logger logger = Logger.getLogger(Login_Page.class);

	private WebDriver driver;
	@FindBy(how = How.XPATH, using = "//input[@name='USER']")
	private WebElement emailinput;
	@FindBy(how = How.XPATH, using = "//input[@name='PASSWORD']")
	private WebElement password;
	@FindBy(how = How.XPATH, using = "//input[@title='Log on with Email ID']")
	private WebElement submitbtn;
	@FindAll({ @FindBy(className = "warning") })
	private List<WebElement> content;

	// constructor
	public Login_Page(WebDriver driver) {
		this.driver = driver;
	}

	// type the email for the log credential
	public void typeEmail(String email) {
		SeleniumCore.clearAndTypeString(emailinput, email);
		
		logger.info("Input the email address is:" + email);
	}

	// type the pasword for the log credential
	public void typePassword(String strpassword) {

		SeleniumCore.clearAndTypeString(password, strpassword);
		logger.info("Input the password is:" + strpassword);

	}

	// click the submit button
	public void clickSubmitBtn() {
		SeleniumCore.highLight(driver, submitbtn);
		SeleniumCore.clickElement(submitbtn);
		logger.info("Click the Submit button to log in the home page");
	}

	// log in the system using the valid credential
	
	public Home_Page logAsValidUser(String username, String inputpassword) {
		String pagename=this.getClass().getName();
		logger.info("\n***************************************"+pagename+"****************************************************");

		if (isLoginPage()) {
			logger.info("User now is logging the PAF,input username and password......");
			Resulter.log("COMMENT_LOG_USER", username);
			typeEmail(username);
			typePassword(inputpassword);
			clickSubmitBtn();
		}
		logger.info("Had logged in the home page successfully......");
		return PageFactory.initElements(driver, Home_Page.class);
	}

	// verify the user is logged before
	public boolean isLoginPage() {
		// driver.findElement(By.)
		boolean bemail = SeleniumCore.isDisplayed(emailinput);
		boolean bpass = SeleniumCore.isDisplayed(password);
		boolean bsubmit = SeleniumCore.isDisplayed(submitbtn);
		int size = content.size();
		logger.info("Find the content size are :" + size);

		if (bemail && bpass && bsubmit && (size > 0)) {
			logger.info("Now we are in the login page,So we will input the username and password to log in home page");
			return true;
		} else {
			logger.info("We cannot find some element matched the login page ,so we may in the home page already");
			return false;
		}
	}
}
