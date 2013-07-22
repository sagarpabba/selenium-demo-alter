package com.hp.pop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	@FindBy(name="")
	WebElement username;
	@FindBy(xpath="")
	WebElement password;
	@FindBy(xpath="")
	WebElement submit;
	
    WebDriver driver;
	public HomePage(WebDriver driver) {
	//	super();
		// TODO Auto-generated constructor stub
		this.driver=driver;
	}
	
	public MainPage validLogin(String user,String pass)
	{
		username.clear();
		username.sendKeys(pass);
		
		return new MainPage(driver);
	}
	
	public HomePage invalidLogin(String user,String pass)
	{
//		PageFactory.initElements(driver, LoginPage.class);
		return new HomePage(driver);
//		driver.findElement(By.id("13")).GET
	}
	

}
