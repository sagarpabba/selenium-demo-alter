package com.hp.tests;

import java.util.Calendar;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.hp.pop.ListSearch_Assessment_Run_Page;
import com.hp.pop.RunDetail_Page;
import com.hp.utility.BaseDriver;
import com.hp.utility.SeleniumCore;


public class TestRunnerDemo extends BaseDriver {

	@Test
	public void test1() throws Exception {
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
		
		driver.get("https://proactive-assessments-fut1.corp.hp.com/web/run");
		ListSearch_Assessment_Run_Page ls=PageFactory.initElements(driver, ListSearch_Assessment_Run_Page.class);
		ls.verifyPageElements();
		RunDetail_Page rp=ls.searchRun("pc", "3219", SeleniumCore.getCurrentTime(Calendar.getInstance().getTime()));
		rp.verifyPageElements();
		rp.downloadReport();
		
//		WebDriver driver=new InternetExplorerDriver();
//		driver.get("https://proactive-assessments-fut1.corp.hp.com/web");
//		Thread.sleep(20000);
//		JavascriptExecutor je = (JavascriptExecutor) driver;
//	    je.executeScript("window.document.getElementById('overridelink').click();");
		//System.out.println(et.getTagName());
	}

	@Test
	public void test2() {
		//System.out.println("Demo2 :run2");
	}

}
