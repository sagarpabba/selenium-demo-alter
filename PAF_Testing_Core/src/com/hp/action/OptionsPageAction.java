/**
 * Project Name:PAF_Testing_Core
 * File Name:Options_Page.java
 * Package Name:com.hp.pop
 * Date:Sep 27, 20139:35:29 AM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/
/**
 * 
 */

package com.hp.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.hp.po.OptionsPage;
import com.hp.utility.SeleniumCore;
import com.hp.utility.TimeUtils;

/**
 * ClassName:Options_Page 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 TODO ADD REASON. 
 * Date:     Sep 27, 2013 9:35:29 AM 
 * @author   huchan
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
/**
 * @author huchan
 *
 */
public class OptionsPageAction extends OptionsPage {
	
	/**
	 * @param driver
	 */
	public OptionsPageAction(WebDriver driver) {
		this.driver=driver;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void verifyPageElements(String pagename) throws IOException {
		// TODO Auto-generated method stub
		super.verifyPageElements(pagename);
		//Date:Sep 27, 201310:23:56 AM
	}
	public String getEmailRequstor(){
		String emailaddress=getElementText(mailname);
		logger.info("get the email address is:"+emailaddress);
		return emailaddress;
	}
	
	public List<String> getReportLanguageList() throws IOException{
		String checkedlanguage="";
		List<String> returnlanguage=new LinkedList<String>();
		List<WebElement> languagelist=findElementListByTagName(languages, "li");
		logger.info("Currently get the language total numbers are:"+languagelist.size());
		for(WebElement e:languagelist){
			    WebElement languagetext=findElementByXpath(e, "ul/span");
			    String languagenote=getElementText(languagetext);
			    WebElement checkedbox=findElementByXpath(e, "ul/input");
			    if(checkedbox.isSelected()){
			    	checkedlanguage=languagenote;
			    }
			   
			    returnlanguage.add(languagenote);
		}
		
		logger.info("All the supported language report are:"+returnlanguage);
		comments="Supported language list["+returnlanguage+"],Selected PAF Report language is:"+checkedlanguage;
		if(returnlanguage.size()>0){
			status="Passed";
			reporterNewStep("", "Get the Report supported language list",status,comments, driver);
			
		}
		else
		{
			status="Failed";
			comments="Language list is empty";
			reporterNewStep("", "Get the Report supported language list",status,comments, driver);
			
		}
		return returnlanguage;
		
	}
	public AssessmentReviewPageAction reviewAndRunNow() throws IOException{
		
		getEmailRequstor();
		getReportLanguageList();
		clickElement(nextbtn);
		sleepSeconds(4);
		return PageFactory.initElements(driver, AssessmentReviewPageAction.class);
	}
	
	public AssessmentReviewPageAction reviewAndScheduleLaterRun() throws IOException{
		Map<String, String> mapdata = SeleniumCore
				.importDataTable("device_detail");

		String isSchedule = mapdata.get("Scheduling").toLowerCase();
		logger.info("we will run the test on schedule or not: " + isSchedule);
		String schedulname = mapdata.get("Schedule_Title");
		String frequency = mapdata.get("Frequency");
		int aftertime = Integer.valueOf(mapdata.get("After_Minutes"));
		logger.info("Select options page-the schedule name is:" + schedulname
				+ ",the minutes we will run the schedule is:" + aftertime);

		int scheduleindex = TimeUtils.getRandomNumber();
		String inputname = schedulname + scheduleindex;
		savesubscription.click();

		String titlepath = ".//*[@id='analysisRequestName']";
		String selectcodepath = ".//*[@id='frequencyCode']";
		String noendboxpath = ".//*[@id='noEndFlag']";

		WebElement schedulename = driver.findElement(By.xpath(titlepath));
		WebElement selectcode = driver
					.findElement(By.xpath(selectcodepath));

		clearAndTypeString(schedulename, inputname);
		logger.info("Select options page-the schedule name we input is:"
					+ inputname);
           
		selectElementViaText(selectcode, frequency);

		WebElement noendbox = driver.findElement(By.xpath(noendboxpath));

		SimpleDateFormat timeset = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, aftertime);
		String inputtime = timeset.format(cal.getTime());

		setDateTimePicker("startTimestamp", inputtime);

	    clickElement(noendbox);
		
	    comments="The Schedule Name:"+inputname+",Start Run At:"+inputtime;
		reporterNewStep("", "Schedule the new Assessment Run",status,comments, driver);
	  
		clickElement(nextbtn);
		sleepSeconds(4);
		return PageFactory.initElements(driver, AssessmentReviewPageAction.class);
	}
}

