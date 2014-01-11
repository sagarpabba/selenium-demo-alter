/**
 * Project Name:PAF_Testing_Core
 * File Name:Home_Page.java
 * Package Name:com.hp.pop
 * Date:Sep 27, 20139:31:37 AM
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

import com.hp.po.HomePage;

/**
 * ClassName:Home_Page 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 TODO ADD REASON. 
 * Date:     Sep 27, 2013 9:31:37 AM 
 * @author   huchan

 * @since    JDK 1.6
 * @see 	 
 * @version $Revision: 1.0 $
 */
/**
 * @author huchan
 *
 */
public class HomePageAction extends HomePage {
	
	
	

	/**
	 * @param driver
	 */
	public HomePageAction(WebDriver driver) {
		this.driver=driver;
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * Method verifyPageElements.
	 * @param pagename String
	 * @throws IOException
	 */
	@Override
	public void verifyPageElements(String pagename) throws IOException {
		// TODO Auto-generated method stub
		super.verifyPageElements(pagename);
		String buildnumber = "";
		String pafmessage="";
		if((isElementPresentAndDisplay(buildnumbertag))&&(isElementPresentAndDisplay(pafwarningnote))){
			buildnumber=getElementText(buildnumbertag);
			pafmessage=getElementText(pafwarningnote);
			status="Passed";
			comments="Build Number:"+buildnumber+",PAF Notification:"+pafmessage;
		}
		else{
			status="Failed";
			comments="Applcation Build Number element and PAF Notification not displayed in the home page";
		}
		
		logger.info("get the paf warning message is:"+pafmessage);
		comment_paf_buildnumber=buildnumber;

		
	}
	/**
	 * Method searchRun.
	 * @return SearchRunPageAction
	 */
	public SearchRunPageAction searchRun(){
		clickElement(listrun);
		return PageFactory.initElements(driver, SearchRunPageAction.class);
	}
	/**
	 * Method newAssessment.
	 * @return LaunchAssessmentPageAction
	 * @throws IOException
	 */
	public LaunchAssessmentPageAction newAssessment() throws IOException{
		//click the new assessment link
		clickElement(newassessmentrequest);
		logger.info("Now clicked the new assessment request link to the lauch new assessment page");
		reporterNewStep("Home Page", "Verify Home Page's Elements", status, comments, driver);
		return PageFactory.initElements(driver, LaunchAssessmentPageAction.class);
	}
	
}

