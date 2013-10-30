/**
 * Project Name:PAF_Testing_Core
 * File Name:Assessment_Review_Page.java
 * Package Name:com.hp.pop
 * Date:Sep 27, 20139:33:05 AM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.hp.action;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.hp.po.AssessmentReviewPage;

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
public class AssessmentReviewPageAction extends AssessmentReviewPage {

		
	public AssessmentReviewPageAction(WebDriver driver) {
		this.driver=driver;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void verifyPageElements(String pagename) throws IOException {
		// TODO Auto-generated method stub
		super.verifyPageElements(pagename);
		//Date:Sep 27, 201310:23:24 AM
	}
	
	public AssessmentSummaryPageAction runAssessment() throws InterruptedException{
		
		clickElement(driver.findElement(By.xpath("//*[@id='runBtn']")));
		sleepSeconds(3);
		return PageFactory.initElements(driver, AssessmentSummaryPageAction.class);
	}
	
	public AssessmentSummaryPageAction saveAssessment() throws InterruptedException{
		
		clickElement(driver.findElement(By.xpath(".//*[@id='saveBtn']")));
		sleepSeconds(3);
		return PageFactory.initElements(driver, AssessmentSummaryPageAction.class);
	}
}

