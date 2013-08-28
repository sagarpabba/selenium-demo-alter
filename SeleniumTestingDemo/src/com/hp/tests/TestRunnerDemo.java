package com.hp.tests;

import java.util.Calendar;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.hp.pop.ListSearch_Assessment_Run_Page;
import com.hp.pop.RunDetail_Page;
import com.hp.utility.NiceBaseDriver;
import com.hp.utility.SeleniumCore;


public class TestRunnerDemo extends NiceBaseDriver {

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
		RunDetail_Page rp=ls.searchRun("pc", "3149", SeleniumCore.getCurrentTime(Calendar.getInstance().getTime()));
		rp.verifyPageElements();
		rp.downloadReport();
	}

	@Test
	public void test2() {
		//System.out.println("Demo2 :run2");
	}

}
