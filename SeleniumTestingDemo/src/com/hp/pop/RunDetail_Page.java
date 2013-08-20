package com.hp.pop;

import org.apache.log4j.Logger;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.hp.utility.SeleniumCore;

public class RunDetail_Page {


	private WebDriver driver;
	private Logger logger = Logger.getLogger(RunDetail_Page.class);
	
	@FindBy(how=How.XPATH,using=".//*[@id='content']/div/p") 
	@CacheLookup
	private WebElement headtitle;
	
	@FindBy(how=How.XPATH,using=".//*[@id='fackDownloadBtn']")
    @CacheLookup
    private WebElement downloadbtn;
	
	@FindBy(how=How.XPATH,using="")
	@CacheLookup
	private WebElement backbtn;
	
	
	public RunDetail_Page(WebDriver driver) {
		this.driver = driver;
	}

	
	public void verifyPageElements(){
		boolean isdownload=SeleniumCore.isDisplayed(downloadbtn);
		boolean isback=SeleniumCore.isEnabled(backbtn);
		
		String pagename=this.getClass().getName();
		logger.info("\n***************************************"+pagename+"****************************************************");

	}
}
