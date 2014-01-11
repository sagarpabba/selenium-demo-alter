/**
 * Project Name:PAF_Testing_Core
 * File Name:Assessment_Review_Page.java
 * Package Name:com.hp.pop
 * Date:Sep 27, 20139:33:05 AM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.hp.action;



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

 * @since    JDK 1.6
 * @see 	 
 * @version $Revision: 1.0 $
 */
public class AssessmentReviewPageAction extends AssessmentReviewPage {

		
	/**
	 * Constructor for AssessmentReviewPageAction.
	 * @param driver WebDriver
	 */
	public AssessmentReviewPageAction(WebDriver driver) {
		this.driver=driver;
		// TODO Auto-generated constructor stub
	}

	
	
	/**
	 * Method runAssessment.
	 * @return AssessmentSummaryPageAction
	 * @throws InterruptedException
	 */
	public AssessmentSummaryPageAction runAssessment() throws InterruptedException{
		
		clickElement(driver.findElement(By.xpath("//*[@id='runBtn']")));
		sleepSeconds(3);
		return PageFactory.initElements(driver, AssessmentSummaryPageAction.class);
	}
	
	/**
	 * Method saveAssessment.
	 * @return AssessmentSummaryPageAction
	 * @throws InterruptedException
	 */
	public AssessmentSummaryPageAction saveAssessment() throws InterruptedException{
		
		clickElement(driver.findElement(By.xpath(".//*[@id='saveBtn']")));
		sleepSeconds(3);
		return PageFactory.initElements(driver, AssessmentSummaryPageAction.class);
	}
}

