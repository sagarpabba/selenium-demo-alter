package com.hp.pop;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import com.hp.utility.SeleniumCore;

public class Home_Page {

	private WebDriver driver;
	private static Logger logger = Logger.getLogger(Home_Page.class);
	
	@FindBy(how = How.XPATH, using = "//*[@id='hpit-topMenu']/li[2]/ul/li[1]/a")
	WebElement newassessment;
	@FindBy(how = How.XPATH, using = "//*[@id='hpit-topMenu']/li[2]/ul/li[2]/a")
	WebElement searchrun;

	@FindBy(how = How.XPATH, using = "//*[@id='hpit-topMessage']/b")
	WebElement notifitymessage;
	@FindBy(how = How.XPATH, using = "//*[@id='content']/div/div[1]")
	WebElement pafnote;
	@FindBy(how = How.XPATH, using = "//*[@id='createRequest']")
	WebElement newassessmentlink;
	@FindBy(how = How.XPATH, using = "//*[@id='listRuns']/b")
	WebElement listrun;
	@FindBy(how = How.XPATH, using = "//*[@id='listSubscriptions']")
	WebElement listsub;
	@FindBy(how = How.XPATH, using = "//*[@id='feedback']")
	WebElement feekback;

	@FindBy(how = How.XPATH, using = "//*[@id='hpit-topUser']")
	WebElement loginuser;

	@FindBy(how = How.XPATH, using = "//*[@id='hpit-btmList1'][2]")
	WebElement buildnumber;

	public Home_Page(WebDriver driver) {
		this.driver = driver;
	}

	public Launch_Assessment_Page clickNewAssessment() {

		SeleniumCore.clickElement(driver, newassessment);
		return new Launch_Assessment_Page(driver);
	}

	// list the assessment had been run inthe PAF
	public ListSearch_Assessment_Run_Page clickListRun() {

		SeleniumCore.clickElement(driver, searchrun);
		return new ListSearch_Assessment_Run_Page(driver);
	}

	// get the PAF build number
	public String getBuildNumber() {
		String buildnum = "";
		boolean isdisplayed = SeleniumCore.isDisplayed(buildnumber);
		if (isdisplayed) {
			buildnum = SeleniumCore.getElementText(buildnumber);
		}
		logger.info("The build number showed in the home page is:" + buildnum);
	
		return buildnum;
	}

	public boolean verifyPageElements() {
		boolean pageCorrect = false;
		String pagename=this.getClass().getName();
		logger.info("\n***************************************"+pagename+"****************************************************");
		try {
			String buildnumber = getBuildNumber();
			String pafnotes = SeleniumCore.getElementText(pafnote); // content
		//	String notification = SeleniumCore.getElementText(notifitymessage); // the
																				// notification
																				// message
			//driver.getCurrentUrl()
			String testurl=SeleniumCore.getCurrentPageURL(driver);
			logger.info("Current loaded page url is:"+testurl);
			String browser=SeleniumCore.getBrowserType(driver);
			logger.info("Get the browser field is:"+browser);
/*********************************************************************/
			
			SeleniumCore.generateEmailData("PAF Application Build Version", buildnumber);
			SeleniumCore.generateEmailData("PAF Base URL", testurl);
/**********************************************************************/
			
			boolean isnew = SeleniumCore.isDisplayed(newassessmentlink);
			boolean islistrun = SeleniumCore.isDisplayed(listrun);
			boolean islistsub = SeleniumCore.isDisplayed(listsub);
			boolean isfeedback = SeleniumCore.isDisplayed(feekback);
			Map<String, String> mapdata = SeleniumCore
					.importDataTable("home_page");

			// String expecteduser="Welcome "+user;
			String expectednote = mapdata.get("Post_Content");
			String expectednotis = mapdata.get("Notification_Content");
			String expectedbuildNumber = mapdata.get("Build_Number");
			// boolean isuser=expecteduser.equals(loguser);
			boolean isnotes = expectednote.equals(pafnotes);
		//	boolean isnotification = expectednotis.equals(notification);
		
			if (isnew && islistrun && islistsub & isfeedback && isnotes
					) {
				pageCorrect = true;
				logger.info("Checking the content text in page is:" + pafnotes
						+ ",but actually from test data is:" + expectednote);
				logger.info("Checking the notification text in page is:"
						 + ",but actually from test data is:"
						+ expectednotis);
				logger.info("Checking the build number in home page is:"
						+ buildnumber + ",but actually from test data is:"
						+ expectedbuildNumber);
			}
		} catch (NoSuchElementException e) {
			logger.warn("the web element cannot be found in the home page:"
					+ e.getMessage());
			Reporter.log("Web elment cannot be found in the home page:"
					+ e.getMessage());
		}
		return pageCorrect;
	}

	public Launch_Assessment_Page newAssessment() throws Exception {

		SeleniumCore.clickElement(driver, newassessmentlink);
		SeleniumCore.sleepSeconds(3);
		return PageFactory.initElements(driver, Launch_Assessment_Page.class);
	}

}
