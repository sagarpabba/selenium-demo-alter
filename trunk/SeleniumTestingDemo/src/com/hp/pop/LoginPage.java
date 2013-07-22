package com.hp.pop;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {

	WebDriver driver;
	
	@FindBy(name = "q")
	WebElement txtSearch;
	
	@FindBy(name = "btnK")
	WebElement btnSearch;

	

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}
	
	
    //this page's busniess flow,log in the system
	public void login(String username,String password) {
		txtSearch.clear();
		txtSearch.sendKeys(username);
		txtSearch.sendKeys(Keys.RETURN);
		//wait until the search results exist
		driver.findElement(By.id("ires"));

	}

}
