/**
 * Project Name:PAF_Testing_Core
 * File Name:Assessment_Summary_Page.java
 * Package Name:com.hp.pop
 * Date:Sep 27, 20139:33:32 AM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.hp.action;

import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.hp.po.AssessmentSummaryPage;
import com.hp.utility.SeleniumCore;
import com.hp.utility.TimeUtils;

/**
 * ClassName:Assessment_Summary_Page 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 TODO ADD REASON. 
 * Date:     Sep 27, 2013 9:33:32 AM 
 * @author   huchan
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class AssessmentSummaryPageAction extends AssessmentSummaryPage {

		
	public AssessmentSummaryPageAction(WebDriver driver) {
		this.driver=driver;
		// TODO Auto-generated constructor stub
	}


	public String getRunID() throws InterruptedException, IOException {
		String returnrunid = getElementText(runid);
		logger.info("Request summary view page .Currently the request run over ,and the Run ID is:"
				+ returnrunid);
		status="Passed";
		comments="Run ID is:"+returnrunid;
	    reporterNewStep("Request Summary detail", "Get the Request ID",status,comments,driver);
		return returnrunid;
	}
	
	public String getRunStartTime(){
		return TimeUtils.getCurrentTime(Calendar.getInstance().getTime());
	}
	public String getRequestType() {
		return getElementText(requesttype);
	}

	public String getCustomerName() {
		return getElementText(customername);
	}

	public String getAddress() {
		return getElementText(address);
	}

	public String getLanguage() {
		return getElementText(languages);
	}

	public String getDeviceNumber() {
		return getElementText(devicenumbers).split("[")[0];
	}
	
	public SearchRunPageAction goSearchRunPage(){
		
		Map<String,String> mapdata=SeleniumCore.importDataTable("login_page");
		String baseurl=mapdata.get("Base_URL");
		String runurl=baseurl+"/run";
		driver.get(runurl);
		return PageFactory.initElements(driver, SearchRunPageAction.class);
	}
}

