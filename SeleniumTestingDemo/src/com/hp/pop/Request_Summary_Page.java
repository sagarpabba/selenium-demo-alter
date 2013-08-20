package com.hp.pop;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;

import com.hp.utility.Resulter;
import com.hp.utility.SeleniumCore;
import com.hp.utility.TimerUtils;

public class Request_Summary_Page {

	private static Logger logger = Logger.getLogger(Request_Summary_Page.class);
	@SuppressWarnings("unused")
	private WebDriver driver;

	@FindBy(how = How.XPATH, using = ".//*[@id='main_column']/div[1]/div/div[2]/ul/li[1]")
	private WebElement runid;
	@FindBy(how = How.XPATH, using = ".//*[@id='main_column']/div[1]/div/div[2]/ul/li[2]")
	private WebElement requesttype;
	@FindBy(how = How.XPATH, using = ".//*[@id='main_column']/div[1]/div/div[2]/ul/li[3]")
	private WebElement customername;
	@FindBy(how = How.XPATH, using = ".//*[@id='main_column']/div[1]/div/div[2]/ul/li[4]")
	private WebElement address;
	@FindBy(how = How.XPATH, using = ".//*[@id='main_column']/div[1]/div/div[2]/ul/li[5]")
	private WebElement languages;
	@FindBy(how = How.XPATH, using = ".//*[@id='main_column']/div[1]/div/div[2]/ul/li[6]")
	private WebElement devicenumbers;

	@FindBy(how = How.XPATH, using = ".//*[@id='main_column']/div[2]/div/div")
	private WebElement notes;

	public Request_Summary_Page(WebDriver driver) {
		this.driver = driver;
	}

	public void verifyPageElements() {
		boolean isrunid = SeleniumCore.isDisplayed(runid);
		boolean isrequesttype = SeleniumCore.isDisplayed(requesttype);
		boolean iscustomer = SeleniumCore.isDisplayed(customername);
		boolean isaddress = SeleniumCore.isDisplayed(address);
		boolean islanguage = SeleniumCore.isDisplayed(languages);

		String pagename=this.getClass().getName();
		logger.info("\n***************************************"+pagename+"****************************************************");

		if (isrunid && isrequesttype && iscustomer && isaddress && islanguage) {
			logger.info("Request summary view page .we found all the webelments displayed in the page"
					+ isrunid
					+ ""
					+ ",request type:"
					+ isrequesttype
					+ ",customer name:"
					+ iscustomer
					+ ",address :"
					+ isaddress
					+ ",language:" + islanguage);
		} else {
			Assert.fail();
			logger.error("Request summary view page ..Sorry the webelment cannot be found in the request summary page ");
		}
	}

	public String getRunID() throws InterruptedException {
		String returnrunid = SeleniumCore.getElementText(runid);
		logger.info("Request summary view page .Currently the request run over ,and the Run ID is:"
				+ returnrunid);
		Resulter.log("STATUS_SCAN_EMAIL_RESULT", "Passed");
		Resulter.log("COMMENT_SCAN_EMAIL_RESULT", "RUN ID:"+returnrunid+",Requst Type:"+getRequestType()+",Customer Name:"+getCustomerName());
		return returnrunid;
	}

	public String getRunStartTime(){
		return TimerUtils.getCurrentTime(Calendar.getInstance().getTime());
	}
	public String getRequestType() {
		return requesttype.getText().trim();
	}

	public String getCustomerName() {
		return customername.getText().trim();
	}

	public String getAddress() {
		return address.getText().trim();
	}

	public String getLanguage() {
		return languages.getText().trim();
	}

	public String getDeviceNumber() {
		return devicenumbers.getText().trim().split("[")[0];
	}

}
