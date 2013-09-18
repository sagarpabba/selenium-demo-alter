package com.hp.pop;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.hp.utility.FilesUtils;
import com.hp.utility.SeleniumCore;

public class RunDetail_Page extends PageObject {
	
	@FindBy(how=How.XPATH,using="//*[@id='content']/div/p") 
	@CacheLookup
	private WebElement headtitle;
	
	@FindBy(how=How.XPATH,using="//*[@id='fackDownloadBtn']")
    @CacheLookup
    private WebElement downloadbtn;
	
	@FindBy(how=How.XPATH,using="//*[@id='backBtn']")
	@CacheLookup
	private WebElement backbtn;
	
	@FindBy(how=How.XPATH,using="//*[@id='cb_reportDownloadTable']")
	@CacheLookup
	private WebElement checkallbtn;
	
	@FindBy(how=How.XPATH,using="//*[@id='reportDownloadTable']")
	@CacheLookup
	private WebElement reporttable;
	
	
	
	public RunDetail_Page(WebDriver driver) {
		super(driver);
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
		List<WebElement> reportlist=SeleniumCore.findElementListByTagName(reporttable, "tr");
		SeleniumCore.highLight(driver, reporttable);
		Map<String,String> reportmap=new HashMap<String, String>();
		for(WebElement e:reportlist){
		    String reportname=SeleniumCore.findElementByXpath(e, "td[2]").getText().trim();
		    String language=SeleniumCore.findElementByXpath(e, "td[3]").getText().trim();
		    logger.info("Fond the report name is:"+reportname+",the langauge is:"+language);
		    if((!reportname.equals("")) &&(!language.equals(""))){
		    	// get the report name and language we used now 
		    	reportmap.put(reportname, language);
		    }
		}
		
		logger.info("Get the report list is:"+reportmap);
		SeleniumCore.clickElement(driver, downloadbtn);
		
		//wait the object displayed;
		SeleniumCore.waitForObjectDisplay(driver, "//*[@id='downloadreason']");
		WebElement downloadreason=driver.findElement(By.xpath("//*[@id='downloadreason']"));
		SeleniumCore.SelectElementViaValue(downloadreason, "DT");
		SeleniumCore.sleepSeconds(3);
	    SeleniumCore.clickElement(driver,driver.findElement(By.xpath("//body/div[4]/div[3]/*/button[1]")));
	    String imagepath=".//*[@id='hpit-busy']/img";
	    SeleniumCore.waitProcessBarNotAppear(driver.findElement(By
				.xpath(imagepath)));
	    
	   // Resulter.log("STATUS_REPORT_DETAIL", "Passed");
	    //Resulter.log("COMMENT_REPORT_DETAIL",reportmap.toString());
	    if(reportmap.size()==0){
	       SeleniumCore.generateEmailStep("Run ID Detail Review", "Get the Report generated list", "Failed","Generated Report is empty,see the blow screenshot for detail", driver);
	    }
	    else{
	       SeleniumCore.generateEmailStep("Run ID Detail Review", "Get the Report generated list", "Passed", "Generated Report list is:"+reportmap.toString(), driver);
	    }
	    //click the download button and save the file into the default directory
	    String reportdir=SeleniumCore.getProjectWorkspace()+"PAFRunReports";
	    File[] subzipfiles=FilesUtils.listFilesEndsWith(reportdir, ".zip");
	    String filename="";
	    String filepath="";
	    for(File todayzip:subzipfiles){
	    	filename=todayzip.getName();
	    	filepath=todayzip.getAbsolutePath();
	    }
	    logger.debug("Get the zip file path is:"+filepath);
	    if(subzipfiles.length>1||subzipfiles.length<0){
	    	 SeleniumCore.generateEmailStep("", "Verify the download zip file", "Warning", "Generate zip Report number is:"+subzipfiles.length+",File path is:"+filepath, driver);
	    	 logger.error("the report directory is not clear ,the directory more than one zip file");
	    }else{
	    	//verify the file is download completed
	    	File zipfile=new File(filepath);
	    //	long oldSize = 0L;
	    	long newSize = 0L;
	    	while(newSize<zipfile.length()){
	    	   SeleniumCore.sleepSeconds(4);
	    	   logger.debug("We find the downloaded file size is changing "+newSize+",which means the file is still downloading...");
	    	   newSize=zipfile.length();
	    	}
	    	logger.debug("Now the zip file had been download successfully from the PAF ....");
	    	//unzip the file
	    	FilesUtils.unZipIt(filepath, reportdir);
	    	logger.debug("Unzip the first report file..");
	    	FilesUtils.unZipIt(reportdir+File.separator+"proactive-scan-report.zip", reportdir);
	    	logger.debug("Unzip the proactive scan report zip file..");
	    	String reporthtmlfile=reportdir+File.separator+"proactive-scan-report.html";
	    	
	    	String filecontent=FilesUtils.returnFileContents(reporthtmlfile);
	    	logger.debug("the html report file content is:"+filecontent);
	    	
	    }
	  
	}
}
