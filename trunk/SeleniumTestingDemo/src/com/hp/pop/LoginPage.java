package com.hp.pop;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.How;

public class LoginPage {

	
	private WebDriver driver;
	@FindBy(how=How.XPATH,using="//*/input[@name='USER']") private WebElement emailinput;
	@FindBy(how=How.XPATH,using="//*/input[@name='PASSWORD']") private WebElement password;
	@FindBy(how=How.XPATH,using="//*/input[@title='Log on with Email ID']") private WebElement submitbtn;
	@FindAll({@FindBy(className="logcontent")}) private List<WebElement> content;
	
	
	private Logger logger=Logger.getLogger(LoginPage.class);
	
	//constructor
	public LoginPage(WebDriver driver)
	{
		this.driver=driver;
	}
	//type the email for the log credential
	public void typeEmail(String email)
	{
		emailinput.clear();
		emailinput.sendKeys(email);
	}
	//type the pasword for the log credential
	public void typePassword(String strpassword)
	{
		password.clear();
		password.sendKeys(strpassword);
		
	}
	
	//log in the system using the valid credential 
	public HomePage logAsValidUser(String username,String inputpassword)
	{
		
		if(isLoginPage())
		{
			typeEmail(username);
			typePassword(inputpassword);
			submitbtn.click();
		}
		return new HomePage(driver);
	}
	
	//verify the user is logged before 
	public boolean isLoginPage()
	{
		//driver.findElement(By.)
		boolean bemail=emailinput.isDisplayed();
		boolean bpass=password.isDisplayed();
		boolean bsubmit=submitbtn.isDisplayed();
		int size=content.size();
		logger.debug("Find the content size is :"+size);
		
		if(bemail && bpass &&bsubmit &&(size>0))
		{
			return true;
		}
		else
		{
		    return false;
		}
	}
}
