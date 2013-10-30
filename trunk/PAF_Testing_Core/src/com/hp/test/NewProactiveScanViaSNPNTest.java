package com.hp.test;

import java.io.File;
import java.io.IOException;

import org.testng.annotations.Test;
import org.openqa.selenium.support.PageFactory;

import com.hp.action.AssessmentReviewPageAction;
import com.hp.action.AssessmentSummaryPageAction;
import com.hp.action.CustomerPageAction;
import com.hp.action.DevicePageAction;
import com.hp.action.HomePageAction;
import com.hp.action.LaunchAssessmentPageAction;
import com.hp.action.OptionsPageAction;
import com.hp.action.SearchRunDetailPageAction;
import com.hp.action.SearchRunPageAction;
import com.hp.po.PageObject;
import com.hp.utility.BaseDriver;
import com.hp.utility.HostUtils;
import com.hp.utility.SeleniumCore;

public class NewProactiveScanViaSNPNTest extends BaseDriver {
  
	private static HomePageAction hp;
	private static LaunchAssessmentPageAction lnp;
	private static CustomerPageAction cp;
	private static DevicePageAction dp;
	private static OptionsPageAction op;
	private static AssessmentReviewPageAction arp;
	private static AssessmentSummaryPageAction asp;
	private static SearchRunPageAction srp;
	private static SearchRunDetailPageAction srdp;
	
	
	public static String runid="";
	public static String runstart="";
	
	
	@Test(testName="Home Page Operation")
	public void test_NavigateHomePage() throws Exception{
		hp=PageFactory.initElements(driver,HomePageAction.class);
		hp.verifyPageElements("Home Page");
			
	}
	
	@Test(testName="Launch a New Assessment",dependsOnMethods="test_NavigateHomePage")
	public void test_Launch_new_Assessment() throws Exception{
		lnp=hp.newAssessment();
		lnp.verifyPageElements("Launch Assessment Page");
		cp=lnp.launchPSAssessment();
	}
	
	@Test(testName="Select customer we specified",dependsOnMethods="test_Launch_new_Assessment")
	public void test_SelectCustomer() throws Exception{		
		cp.verifyPageElements("Customer Select Page");
		String snpnfile=SeleniumCore.getProjectWorkspace()+"PAFdevices"+File.separator+"testdevice.xlsx";	
		dp=cp.selectCustomerViaSNPN(snpnfile);
	}
	@Test(testName="select device list",dependsOnMethods="test_SelectCustomer")
	public void test_SelectDevice() throws IOException{
		dp.verifyPageElements("Device Page");
		op=dp.selectAllDevices();
	}
	
	@Test(description = "New schedule page", dependsOnMethods = "test_SelectDevice")
	public void test_Options() throws Exception {
		op.verifyPageElements("Assessment Option Page");	
		arp=op.reviewAndRunNow();
	}

	@Test(description = "New Request review page", dependsOnMethods = "test_Options")
	public void test_Review() throws Exception {
		
		arp.verifyPageElements("Assessment Review Page");
	    asp=arp.runAssessment();

		asp.verifyPageElements("Assessment Summary Page");
		runid =asp.getRunID();
		runstart=asp.getRunStartTime();
		// search the run result
		srp=asp.goSearchRunPage();
	}

	@Test(description = "list run result", dependsOnMethods = "test_Review")
	public void test_ListRun() throws Exception {

		srp.verifyPageElements("Assessment Run Search Page");
		srdp=srp.downloadRun("pc", runid,runstart);	

	}
	@Test(description = "list run detail reuslt", dependsOnMethods = "test_ListRun")
	public void test_ListRunDetail() throws Exception {
	
		srdp.verifyPageElements("Assessment Run Detail Page");
		srdp.downloadReport();			
	}
	
	@Test(description="list all the data we need to use",alwaysRun=true)
	public void test_listData(){
		String runstart=PageObject.comment_executionstart;
		String hostname=HostUtils.getFQDN();
		String systemname=HostUtils.getOperatingSystemName()+"-"+HostUtils.getOperatingSystemVersion()+" ("+HostUtils.getOSType()+")";

		String browsertype=SeleniumCore.getBrowserType(driver);
		String logincredential=PageObject.comment_login_credential;
		String paf_build=PageObject.comment_paf_buildnumber;
		String paf_url=PageObject.comment_paf_url;
		
		
		SeleniumCore.generateEmailData("Execution Start Time", runstart);
		SeleniumCore.generateEmailData("PAF URL", paf_url);
		SeleniumCore.generateEmailData("PAF Application Build Number", paf_build);
		SeleniumCore.generateEmailData("PAF Login User", logincredential);
		SeleniumCore.generateEmailData("Execution Host Name", hostname);
		SeleniumCore.generateEmailData("Execution Host's System Version", systemname);
		SeleniumCore.generateEmailData("Run Browser Name and Version", browsertype);
	}
}
