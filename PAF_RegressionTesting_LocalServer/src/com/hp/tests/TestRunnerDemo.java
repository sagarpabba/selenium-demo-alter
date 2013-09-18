package com.hp.tests;

import java.util.Calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.hp.pop.Home_Page;
import com.hp.pop.Launch_Assessment_Page;
import com.hp.pop.ListSearch_Assessment_Run_Page;
import com.hp.pop.RunDetail_Page;
import com.hp.pop.Select_Customer_Page;
import com.hp.pop.Select_Device_Page;
import com.hp.utility.BaseDriver_Remote;
import com.hp.utility.FilesUtils;
import com.hp.utility.JsoupUtils;
import com.hp.utility.SeleniumCore;
import com.hp.utility.TimeUtils;

public class TestRunnerDemo extends BaseDriver_Remote{

	@Test
	public void test1() throws Exception  {
		//Assert.fail();
		//System.out.println("Demo2 :run1");
//		List<String> files=FileUtils.getSubFiles("C:\\Python27\\workspace\\PAF_HC\\log");
//		for(String a:files){
//			System.out.println("file is:"+a+"\n");
//		}
	
      // SeleniumCore.autoit_clickButton("AutoIt Help", "", "[TEXT:&Display]");
       //SeleniumCore.autoit_typeText("","","[ID:1001]","abc");
		//System.out.println(SeleniumCore.getProjectWorkspace());
	//driver.get("http://dl.ez-download.com/ez/firefox_downloader.exe");
//		
		driver.get("https://proactive-assessments-fut1.corp.hp.com/web/run");
		ListSearch_Assessment_Run_Page ls=PageFactory.initElements(driver, ListSearch_Assessment_Run_Page.class);
		ls.verifyPageElements();
		RunDetail_Page rp=ls.searchRun("pc", "3386", TimeUtils.getCurrentTime(Calendar.getInstance().getTime()));
		rp.verifyPageElements();
		rp.downloadReport();
		
//		WebDriver driver=new InternetExplorerDriver();
//		driver.get("https://proactive-assessments-fut1.corp.hp.com/web");
//		Thread.sleep(20000);
//		JavascriptExecutor je = (JavascriptExecutor) driver;
//	    je.executeScript("window.document.getElementById('overridelink').click();");
		//System.out.println(et.getTagName());
		//SeleniumCore.switchtoWindow(driver, "Opening");
		//JsoupUtils.getReportSteps();
		//Resulter.logUpdate(2, "Custmer name:", "abc", "7888888888");
//		Home_Page hp=PageFactory.initElements(driver, Home_Page.class);
//		hp.verifyPageElements();
//		Launch_Assessment_Page lap=hp.newAssessment();
//		lap.verifyPageElements();
//		Select_Customer_Page scp=lap.newPS();
//		scp.verifyPageElements();
//		Select_Device_Page sdp=scp.newCustomer();
//		sdp.verifyPageElements();
//		sdp.selectDevice();
		//scp.selectAllCustomersByFile();
	 String zipFile="C:\\temp\\DCOM.zip";
	//	String outputFolder="C:\\Python27\\workspace\\PAF_HC\\PAFReports";
		//SeleniumCore.unZipIt(zipFile, outputFolder);
		//SeleniumCore.generateData("PAF url", "www.kbsste.com");
//		SeleniumCore.generateData("bei", "changyuan");
//		SeleniumCore.generateStep("new assessment", "Verify the assessment success", "blocked", "tested");
		//JsoupUtils.generateHtmlReport();
	//	System.out.print("test is run 1");
		//Assert.fail("test is failed ,manually");
	 //   driver.findElement(By.id("dsdsdsdsd"));
		
	//	FileUtils.unZipIt(zipFile, ".");
	}

	@Test
	public void test2() {
		//System.out.println("Demo2 :run2");
		//Connection con=DatabaseUtils.getConnection();
		//int deletenum=DatabaseUtils.updateRecord(con, "delete from qtp_status where date(RUN_TIME)=CURDATE()");
		//int insertednum=DatabaseUtils.updateRecord(con, "insert into qtp_status(PASS_TOTAL,FAIL_TOTAL,NORUN_TOTAL) Values(2,3,4)");
        
		//con.close();
	    //JsoupUtils.updateOverviewReport();
		//System.out.println("test run 2");
	}

}
