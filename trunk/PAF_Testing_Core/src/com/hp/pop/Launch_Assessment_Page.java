/**
 * Project Name:PAF_Testing_Core
 * File Name:Launch_NewAssessment_Page.java
 * Package Name:com.hp.pop
 * Date:Sep 27, 20139:32:20 AM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/
/**
 * 
 */

package com.hp.pop;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.hp.utility.SeleniumCore;

/**
 * ClassName:Launch_NewAssessment_Page 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 TODO ADD REASON. 
 * Date:     Sep 27, 2013 9:32:20 AM 
 * @author   huchan
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
/**
 * @author huchan
 *
 */
public class Launch_Assessment_Page extends PageObject {

	
	@FindBy(how = How.XPATH, using = "//*[@id='analysisRequestTypeCode']")
	private WebElement options;

	@FindBy(how = How.XPATH, using = "//*[@id='create']")
	private WebElement createbtn;
	
	
	
	/**
	 * @param driver
	 */
	public Launch_Assessment_Page(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void verifyPageElements(String pagename) throws IOException {
		// TODO Auto-generated method stub
		super.verifyPageElements(pagename);
		//Date:Sep 27, 201310:23:50 AM
		status="Passed";
		comments="Page Object showed correctly";
		
	}
	
	public Customer_Page launchPSAssessment() throws IOException{
		SelectElementViaValue(options, "PRO_SCAN");
		logger.info("We had launched the proctive scan assessment");
		clickElement(createbtn);
		comments="Lauch Proctive Scan Assessment";
		SeleniumCore.generateEmailStep("Launch Assessment", "Launch a Specified Assessment", status, comments, driver);
		return PageFactory.initElements(driver, Customer_Page.class);
	}
}

