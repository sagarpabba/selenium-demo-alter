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

package com.hp.action;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.hp.po.LaunchAssessmentPage;

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
public class LaunchAssessmentPageAction extends LaunchAssessmentPage {

	
	/**
	 * @param driver
	 */
	public LaunchAssessmentPageAction(WebDriver driver) {
		this.driver=driver;
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
	
	public CustomerPageAction launchPSAssessment() throws IOException{
		SelectElementViaValue(options, "PRO_SCAN");
		logger.info("We had launched the proctive scan assessment");
		clickElement(createbtn);
		comments="Lauch Proctive Scan Assessment";
		reporterNewStep("Launch Assessment", "Launch a Specified Assessment", status, comments, driver);
		return PageFactory.initElements(driver, CustomerPageAction.class);
	}
}

