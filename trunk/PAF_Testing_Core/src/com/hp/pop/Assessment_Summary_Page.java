/**
 * Project Name:PAF_Testing_Core
 * File Name:Assessment_Summary_Page.java
 * Package Name:com.hp.pop
 * Date:Sep 27, 20139:33:32 AM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.hp.pop;

import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

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
public class Assessment_Summary_Page extends PageObject {

	
	@FindBy(how = How.XPATH, using = "//*[@id='main_column']/div[1]/div/div[2]/ul/li[1]")
	private WebElement runid;
	@FindBy(how = How.XPATH, using = "//*[@id='main_column']/div[1]/div/div[2]/ul/li[2]")
	private WebElement requesttype;
	@FindBy(how = How.XPATH, using = "//*[@id='main_column']/div[1]/div/div[2]/ul/li[3]")
	private WebElement customername;
	@FindBy(how = How.XPATH, using = "//*[@id='main_column']/div[1]/div/div[2]/ul/li[4]")
	private WebElement address;
	@FindBy(how = How.XPATH, using = "//*[@id='main_column']/div[1]/div/div[2]/ul/li[5]")
	private WebElement languages;
	@FindBy(how = How.XPATH, using = "//*[@id='main_column']/div[1]/div/div[2]/ul/li[6]")
	private WebElement devicenumbers;

	@FindBy(how = How.XPATH, using = "//*[@id='main_column']/div[2]/div/div")
	private WebElement notes;
	
	public Assessment_Summary_Page(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void verifyPageElements(String pagename) throws IOException {
		// TODO Auto-generated method stub
		super.verifyPageElements(pagename);
		//Date:Sep 27, 201310:23:30 AM
	}
	
	public String getRunID() throws InterruptedException, IOException {
		String returnrunid = getElementText(runid);
		logger.info("Request summary view page .Currently the request run over ,and the Run ID is:"
				+ returnrunid);
		status="Passed";
		comments="Run ID is:"+returnrunid;
	    SeleniumCore.generateEmailStep("Request Summary detail", "Get the Request ID",status,comments, driver);
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
	
	public SearchRun_Page goSearchRunPage(){
		
		Map<String,String> mapdata=SeleniumCore.importDataTable("login_page");
		String baseurl=mapdata.get("Base_URL");
		String runurl=baseurl+"/run";
		driver.get(runurl);
		return PageFactory.initElements(driver, SearchRun_Page.class);
	}
}

