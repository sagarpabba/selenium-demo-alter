/**
 * Project Name:PAF_Testing_Core
 * File Name:Assessment_Review_Page.java
 * Package Name:com.hp.pop
 * Date:Sep 27, 20139:33:05 AM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.hp.pop;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

/**
 * ClassName:Assessment_Review_Page 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 TODO ADD REASON. 
 * Date:     Sep 27, 2013 9:33:05 AM 
 * @author   huchan
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class Assessment_Review_Page extends PageObject {

	

	@FindBy(how = How.XPATH, using = "//*[@id='divREVIEW_REQUEST']/h2")
	private WebElement header;
	
	public Assessment_Review_Page(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void verifyPageElements(String pagename) throws IOException {
		// TODO Auto-generated method stub
		super.verifyPageElements(pagename);
		//Date:Sep 27, 201310:23:24 AM
	}
	
	public Assessment_Summary_Page runAssessment() throws InterruptedException{
		
		clickElement(driver.findElement(By.xpath("//*[@id='runBtn']")));
		sleepSeconds(3);
		return PageFactory.initElements(driver, Assessment_Summary_Page.class);
	}
	
	public Assessment_Summary_Page saveAssessment() throws InterruptedException{
		
		clickElement(driver.findElement(By.xpath(".//*[@id='saveBtn']")));
		sleepSeconds(3);
		return PageFactory.initElements(driver, Assessment_Summary_Page.class);
	}
}

