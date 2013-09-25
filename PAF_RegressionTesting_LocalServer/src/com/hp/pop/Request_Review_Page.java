package com.hp.pop;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.hp.utility.SeleniumCore;

public class Request_Review_Page extends PageObject{


	@FindBy(how = How.XPATH, using = "//*[@id='divREVIEW_REQUEST']/h2")
	private WebElement header;
	@FindBy(how = How.XPATH, using = "//*[@id='runBtn']")
	private WebElement runbtn;



	public Request_Review_Page(WebDriver driver) {
		super(driver);
	}

	public boolean verifyPageElements(String pagename) throws Exception {
		
	    String headtitle = SeleniumCore.getElementText(header);
		logger.info("New request assessment review page,the head title is:"+ headtitle);
		SeleniumCore.assertDisplayed("Assert the title is displayed in the page", header);
		return super.verifyPageElements(pagename);
	}

	public Request_Summary_Page RunRequest()
			throws Exception {
		boolean runlater=false;
		
		Map<String, String> mapdata = SeleniumCore
				.importDataTable("device_detail");
		String isSchedule = mapdata.get("Scheduling").toLowerCase();
		//Resulter.log("STATUS_SCAN_REVIEW", "Passed");
		if(isSchedule.equals("yes")){
			runlater=true;
		//	Resulter.log("COMMENT_SCAN_REVIEW", "Assessment run later");
		}
		
		if (runlater) {
			// run later the schedule
			logger.info("New request created now ,this is the review page ,and click the save button now ");
			driver.findElement(By.xpath(".//*[@id='saveBtn']")).click();
		} else {
			// run now the schedule
		//	Resulter.log("COMMENT_SCAN_REVIEW", "the assessment run at:"+TimeUtils.getCurrentTime(Calendar.getInstance().getTime()));
			logger.info("New request created now ,this is the review page ,and click the run button now ");
			SeleniumCore.clickElement(driver, runbtn);
		}
		
		SeleniumCore.sleepSeconds(3);
		
		return PageFactory.initElements(driver, Request_Summary_Page.class);
	}

}
