package com.hp.pop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.hp.utility.Resulter;
import com.hp.utility.SeleniumCore;

public class RunDetail_Page {


	private WebDriver driver;
	private Logger logger = Logger.getLogger(RunDetail_Page.class);
	
	@FindBy(how=How.XPATH,using=".//*[@id='content']/div/p") 
	@CacheLookup
	private WebElement headtitle;
	
	@FindBy(how=How.XPATH,using=".//*[@id='fackDownloadBtn']")
    @CacheLookup
    private WebElement downloadbtn;
	
	@FindBy(how=How.XPATH,using=".//*[@id='backBtn']")
	@CacheLookup
	private WebElement backbtn;
	
	@FindBy(how=How.XPATH,using=".//*[@id='cb_reportDownloadTable']")
	@CacheLookup
	private WebElement checkallbtn;
	
	@FindBy(how=How.XPATH,using=".//*[@id='reportDownloadTable']")
	@CacheLookup
	private WebElement reporttable;
	
	
	
	public RunDetail_Page(WebDriver driver) {
		this.driver = driver;
	}

	
	public void verifyPageElements(){
		String pagename=this.getClass().getName();
		logger.info("\n***************************************"+pagename+"****************************************************");
        SeleniumCore.assertDisplayed("Assert the download button is displayed in the page", downloadbtn);
        SeleniumCore.assertEnabled("Assert the back button is displayed in the page", backbtn);
		
	}
	
	
	public void downloadReport() throws Exception{
		//check the check all box 
		SeleniumCore.checkboxed(checkallbtn);
		// find the report name 
		List<WebElement> reportlist=SeleniumCore.findElementListByTagName(reporttable, "tbody/tr");
		Map<String,String> reportmap=new HashMap<String, String>();
		for(WebElement e:reportlist){
		    String reportname=SeleniumCore.findElementByXpath(e, "td[2]").getText().trim();
		    String language=SeleniumCore.findElementByXpath(e, "td[3]").getText().trim();
		    logger.info("Fond the report name is:"+reportname+",the langauge is:"+language);
		    if(!reportname.equals("") &&!language.endsWith("")){
		    	// get the report name and language we used now 
		    	reportmap.put(reportname, language);
		    }
		}
		
		logger.info("Get the report list is:"+reportmap);
		SeleniumCore.clickElement(downloadbtn);
		
		//wait the object displayed;
		SeleniumCore.waitForObjectDisplay(driver, ".//*[@id='downloadreason']");
		WebElement downloadreason=driver.findElement(By.xpath(".//*[@id='downloadreason']"));
		SeleniumCore.SelectElementViaValue(downloadreason, "DT");
		
	    SeleniumCore.clickElement(driver.findElement(By.xpath("html/body/div[3]/div[3]/div/button[1]")));
	    String imagepath=".//*[@id='hpit-busy']/img";
	    SeleniumCore.waitForObjectDisplay(driver.findElement(By
				.xpath(imagepath)));
	    
	    Resulter.log("STATUS_REPORT_DETAIL", "Passed");
	    Resulter.log("COMMENT_REPORT_DETAIL",reportmap.toString());
	    //save the zip file
	    
	}
}
