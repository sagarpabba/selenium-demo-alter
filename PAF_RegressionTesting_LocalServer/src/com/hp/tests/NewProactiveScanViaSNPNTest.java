package com.hp.tests;

import java.io.File;
import java.util.List;





import org.testng.annotations.Test;
import org.openqa.selenium.support.PageFactory;

import com.hp.pop.Home_Page;
import com.hp.pop.Launch_Assessment_Page;
import com.hp.pop.ListSearch_Assessment_Run_Page;
import com.hp.pop.Request_Review_Page;
import com.hp.pop.Request_Summary_Page;
import com.hp.pop.RunDetail_Page;
import com.hp.pop.Select_Customer_Page;
import com.hp.pop.Select_Device_Page;
import com.hp.pop.Select_Options_Page;
import com.hp.utility.BaseDriver;
import com.hp.utility.SeleniumCore;

public class NewProactiveScanViaSNPNTest extends BaseDriver {


	private String runid;
	private String runstart;

	private Home_Page homepage ;
	private Launch_Assessment_Page lap;
	private Select_Customer_Page scp;
	private Select_Device_Page sdp;
	private Select_Options_Page sop;
	private Request_Review_Page rrp;
	private Request_Summary_Page rsp;
	private ListSearch_Assessment_Run_Page larp;
    private RunDetail_Page rdp;
	
	private String emailaddress;
	private List<String> languages;

	@Test(description = "check elements in the home page", testName = "Verify the elements in home page")
	public void test_verifyHomePage() throws Exception {

		// search device
		//server.newHar("logpage");
		homepage = PageFactory.initElements(driver, Home_Page.class);
		//Har har=server.getHar();
		//logger.info("Har is:"+har.toString());
		homepage.verifyPageElements("PAF Home Page");
		//Resulter.log("COMMENT_LOG_USER", username);
	}

	@Test(description = "New Proactive Scan assessment", testName = "click the new assessment link", dependsOnMethods = "test_verifyHomePage")
	public void test_newAssessment() throws Exception {
		lap=homepage.newAssessment();
		lap.verifyPageElements("Assessment Launch Page");
		
		scp=lap.newPS();
	}

	@Test(description = "Select Customer page", dependsOnMethods = "test_newAssessment")
	public void test_SelectCustomer() throws Exception {
		scp.verifyPageElements("Select Customer Page");
		String devicefilepath=SeleniumCore.getProjectWorkspace()+"devicedata"+File.separator+"testdevice.xlsx";
		sdp=scp.selectAllCustomersByFile(devicefilepath);
	}

	@Test(description = "Select device page", dependsOnMethods = "test_SelectCustomer")
	public void test_SelectDevice() throws Exception {
		
		sdp.verifyPageElements("Select Device Page");
		sop=sdp.selectAllDevice();
	}

	@Test(description = "New schedule page", dependsOnMethods = "test_SelectDevice")
	public void test_Options() throws Exception {
		sop.verifyPageElements("Assessment Option Page");
		
		emailaddress=sop.getEmailAdress();
		languages=sop.getReportLanguageList();
		logger.info("Email requestor is:"+emailaddress+",the supported languages are:"+languages);
		rrp= sop.newSchedule();
	}

	@Test(description = "New Request review page", dependsOnMethods = "test_Options")
	public void test_Review() throws Exception {
		
		rrp.verifyPageElements("Assessment Review Page");
		rsp=rrp.RunRequest();

		rsp.verifyPageElements("Assessment Summary Page");
		runid = rsp.getRunID();
		runstart=rsp.getRunStartTime();
		// search the run result
		larp=rsp.goSearchRunPage();
		SeleniumCore.sleepSeconds(3);
		
	}

	@Test(description = "list run result", dependsOnMethods = "test_Review")
	public void test_ListRun() throws Exception {

		larp.verifyPageElements("Assessment Run Search Page");
		rdp=larp.searchRun("pc", runid,runstart);	
		logger.info("exit testing now......");
		
	}
	@Test(description = "list run detail reuslt", dependsOnMethods = "test_ListRun")
	public void test_ListRunDetail() throws Exception {

		
		rdp.verifyPageElements("Assessment Run Detail Page");
		rdp.downloadReport();	
		logger.info("had downloaed the report successfully ...exit testing now......");
		
	}
}
