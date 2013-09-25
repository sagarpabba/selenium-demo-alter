/**
 * Project Name:PAF_RegressionTesting
 * File Name:PageBase.java
 * Package Name:com.hp.pop
 * Date:Sep 13, 20131:06:16 PM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.hp.pop;


import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.hp.utility.SeleniumCore;

/**
 * ClassName:PageBase 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 TODO ADD REASON. 
 * Date:     Sep 13, 2013 1:06:16 PM 
 * @author   huchan
 * @version  
 * @since    JDK 1.6
 * @see 	  http://chon.techliminal.com/page_object/#/slide4
 */
public abstract class PageObject {

	
	public static String URL;
    public WebDriver driver;
    public static Logger logger=Logger.getLogger(PageObject.class);
 
	
	public PageObject(WebDriver driver){
		this.driver=driver;
	}
	
	public boolean verifyPageElements(String pagename) throws Exception{
		boolean pageLoading=false;
		logger.info("\n***************************************"+pagename+"****************************************************");
		//get the page loading time 
		long pageloadingtime=SeleniumCore.getPageLoadTime(driver);
		SeleniumCore.generatePageLoadTime(pagename, pageloadingtime, driver);
		//verify page elements
				
		return pageLoading;
		
	}
	public void open(){
		driver.get(URL);
	}
	
	public String getTitle(){
		String title=driver.getTitle();
		
		return title;
	}
	
	public WebElement getWebElement(By by){
		
		WebElement e=driver.findElement(by);
		return e;
		
	}
	public boolean isElementPresent(By by){
		boolean isdisplay=driver.findElement(by).isDisplayed();
		
		return isdisplay;
	}
	
	public boolean isTextPresent(String text){
		return false;
	}
}

