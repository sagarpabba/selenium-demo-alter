package com.hp.pop;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.hp.utility.SeleniumCore;
import com.hp.utility.TimeUtils;

public class Select_Options_Page extends PageObject{


	
	@FindBy(how = How.XPATH, using = "//*[@id='emailRequestor']")
	private WebElement mailname;
	
	@FindBy(how=How.XPATH,using="//*[@id='language']")
	@CacheLookup
	private WebElement languages;

	@FindBy(how = How.XPATH, using = "//*[@id='saveSubscription']")
	private WebElement savesubscription;

	@FindBy(how = How.XPATH, using = "//*[@id='back']")
	private WebElement backbtn;
	@FindBy(how = How.XPATH, using = "//*[@id='next']")
	private WebElement nextbtn;

	public Select_Options_Page(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public void verifyPageElements() {
		String pagename=this.getClass().getName();
		logger.info("\n***************************************"+pagename+"****************************************************");
        SeleniumCore.assertDisplayed("Assert the email request showed in the page", mailname);
        SeleniumCore.assertEnabled("Assert the back button is enabled in the page", backbtn);
        SeleniumCore.assertEnabled("Assert the next button is enabled in the page", nextbtn);	

	}

	
	public String getEmailAdress() throws IOException{
		String requestor= SeleniumCore.getElementText(mailname);
		//Resulter.log("STATUS_SCAN_EMAIL_RECIPIENTS", "Passed");
		//Resulter.log("COMMENT_SCAN_EMAIL_RECIPIENTS", "Email requestor address:"+requestor);
		SeleniumCore.generateEmailStep("Request Option Page", "Get the Eamil Recipients", "Passed", "Email Address is:"+requestor, driver);
		return requestor;
	}
	
	public List<String> getReportLanguageList() throws IOException{
		String checkedlanguage="";
		List<String> returnlanguage=new LinkedList<String>();
		List<WebElement> languagelist=SeleniumCore.findElementListByTagName(languages, "li");
		logger.info("Currently get the language total numbers are:"+languagelist.size());
		for(WebElement e:languagelist){
			    WebElement languagetext=SeleniumCore.findElementByXpath(e, "ul/span");
			    String languagenote=SeleniumCore.getElementText(languagetext);
			    WebElement checkedbox=SeleniumCore.findElementByXpath(e, "ul/input");
			    if(checkedbox.isSelected()){
			    	checkedlanguage=languagenote;
			    }
			   
			    returnlanguage.add(languagenote);
		}
		
		logger.info("All the supported language report are:"+returnlanguage);
		if(returnlanguage.size()>0){
		  //  Resulter.log("STATUS_SCAN_REPORT_LANGUAGE", "Passed");
		 //   Resulter.log("COMMENT_SCAN_LANGUAGE", "Supported report langauges are:"+returnlanguage);
			SeleniumCore.generateEmailStep("", "Get the Report supported language list", "Passed", "Supported language list["+returnlanguage+"],Selected PAF Report language is:"+checkedlanguage, driver);
			
		}
		else
		{
			// Resulter.log("STATUS_SCAN_REPORT_LANGUAGE", "Failed");
			// Resulter.log("COMMENT_SCAN_LANGUAGE", "Supported report langauges list is empty ,so it's failed");
			SeleniumCore.generateEmailStep("", "Get the Report supported language list", "Failed", "The Supported language list is:"+returnlanguage+",language list is empty", driver);
			
		}
		return returnlanguage;
		
	}
	public Request_Review_Page newSchedule() throws Exception {
		
		Map<String, String> mapdata = SeleniumCore
				.importDataTable("device_detail");

		String isSchedule = mapdata.get("Scheduling").toLowerCase();
		logger.info("we will run the test on schedule or not: " + isSchedule);
		String schedulname = mapdata.get("Schedule_Title");
		String frequency = mapdata.get("Frequency");
		int aftertime = Integer.valueOf(mapdata.get("After_Minutes"));
		logger.info("Select options page-the schedule name is:" + schedulname
				+ ",the minutes we will run the schedule is:" + aftertime);

		if (isSchedule.equals("yes")) {
		//	Resulter.log("STATUS_SCAN_REQUEST_SCHEDULING", "Passed");
		//	Resulter.log("COMMENT_SCAN_REQUEST_SCHEDULING", "Run the assessment "+frequency+" after "+aftertime+" minutes the schedule will be run");
			int scheduleindex = TimeUtils.getRandomNumber();
			String inputname = schedulname + scheduleindex;
			savesubscription.click();

			String titlepath = ".//*[@id='analysisRequestName']";
			String selectcodepath = ".//*[@id='frequencyCode']";
			String noendboxpath = ".//*[@id='noEndFlag']";

			WebElement schedulename = driver.findElement(By.xpath(titlepath));
			WebElement selectcode = driver
					.findElement(By.xpath(selectcodepath));

			SeleniumCore.clearAndTypeString(schedulename, inputname);
			logger.info("Select options page-the schedule name we input is:"
					+ inputname);
          //  Resulter.log("COMMENT_SCHEDULING", inputname);
            
            
			SeleniumCore.selectElementViaText(selectcode, frequency);

			WebElement noendbox = driver.findElement(By.xpath(noendboxpath));

			SimpleDateFormat timeset = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, aftertime);
			String inputtime = timeset.format(cal.getTime());

			SeleniumCore.setDateTimePicker(driver, "startTimestamp", inputtime);

			SeleniumCore.clickElement(driver, noendbox);
			
			
			SeleniumCore.generateEmailStep("", "Schedule the new Assessment Run", "Passed", "The Schedule Name:"+inputname+",Start Run At:"+inputtime, driver);
			
		}
		else{
			//Resulter.log("STATUS_SCAN_REQUEST_SCHEDULING", "Passed");
			//Resulter.log("COMMENT_SCAN_REQUEST_SCHEDULING", "Run the assessment now ,not scheduled");
			
		//	Resulter.log("COMMENT_SCHEDULING", "N/A");
			
			SeleniumCore.generateEmailStep("", "Schedule the new Assessment Run", "Passed", "Run the new assessment now ", driver);
			
		}
     
		SeleniumCore.highLight(driver, nextbtn);
		SeleniumCore.clickElement(driver, nextbtn);
		SeleniumCore.sleepSeconds(4);
		return PageFactory.initElements(driver, Request_Review_Page.class);
	}
}
