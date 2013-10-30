/**
 * Project Name:PAF_Testing_Core
 * File Name:SearchRunDetail_Page.java
 * Package Name:com.hp.pop
 * Date:Sep 27, 20139:33:52 AM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.hp.action;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.hp.po.SearchRunDetailPage;
import com.hp.utility.FilesUtils;
import com.hp.utility.SeleniumCore;

/**
 * ClassName:SearchRunDetail_Page 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 TODO ADD REASON. 
 * Date:     Sep 27, 2013 9:33:52 AM 
 * @author   huchan
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class SearchRunDetailPageAction extends SearchRunDetailPage {

	
	public SearchRunDetailPageAction(WebDriver driver) {
		this.driver=driver;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void verifyPageElements(String pagename) throws IOException {
		// TODO Auto-generated method stub
		super.verifyPageElements(pagename);
		//Date:Sep 27, 201310:24:16 AM
	}
	
	
	public void downloadReport() throws IOException{
		
		//check the check all box 
		checkboxed(checkallbtn);
		// find the report name 
		List<WebElement> reportlist=findElementListByTagName(reporttable, "tr");
		highLight(reporttable);
		Map<String,String> reportmap=new HashMap<String, String>();
		for(WebElement e:reportlist){
			String reportname=findElementByXpath(e, "td[2]").getText().trim();
			String language=findElementByXpath(e, "td[3]").getText().trim();
			logger.info("Fond the report name is:"+reportname+",the langauge is:"+language);
			if((!reportname.equals("")) &&(!language.equals(""))){
				  // get the report name and language we used now 
				    reportmap.put(reportname, language);
			}
		}
				
		logger.info("Get the report list is:"+reportmap);
		clickElement(downloadbtn);
				
		//wait the object displayed;
	   waitForObjectDisplay("//*[@id='downloadreason']");
	   WebElement downloadreason=driver.findElement(By.xpath("//*[@id='downloadreason']"));
	   SelectElementViaValue(downloadreason, "DT");
	   sleepSeconds(3);
	   clickElement(driver.findElement(By.xpath("//body/div[4]/div[3]/*/button[1]")));
	
	   waitProcessBarNotAppear(driver.findElement(By
						.xpath(".//*[@id='hpit-busy']/img")));
			    
	   if(reportmap.size()==0){
		  status="Failed";
		  comments="Generated Report List is empty,see blow screenshot for more detail";
		  SeleniumCore.generateEmailStep("Run ID Detail Review", "Get the Report generated list",status,comments, driver);
	   }
	   else{
		   status="Passed";
		   comments="Generated Report list is:"+reportmap.toString();
		  SeleniumCore.generateEmailStep("Run ID Detail Review", "Get the Report generated list",status,comments, driver);
	   }
	 //click the download button and save the file into the default directory
	  String reportdir=SeleniumCore.getProjectWorkspace()+"PAFRunReports";
	  File[] subzipfiles=FilesUtils.listFilesEndsWith(reportdir, ".zip");
	  String filename="";
	  String filepath="";
	  for(File todayzip:subzipfiles){
		  filename=todayzip.getName();
		  filepath=todayzip.getAbsolutePath();
		  logger.info("Get the zip file name is:"+filename);
	  }
	  logger.debug("Get the zip file path is:"+filepath);
	  if(subzipfiles.length>1||subzipfiles.length<0){
		  status="warning";
		  comments="Generate zip Report number is:"+subzipfiles.length+",File path is:"+filepath;
		  SeleniumCore.generateEmailStep("", "Verify the download zip file",status,comments, driver);
		  logger.error("the report directory is not clear ,the directory more than one zip file");
	  }else{
		//verify the file is download completed
		  File zipfile=new File(filepath);
		//	long oldSize = 0L;
		  long newSize = 0L;
		  while(newSize<zipfile.length()){
			    sleepSeconds(4);
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
		status="Passed";
		comments="unzipped Report File path:"+reporthtmlfile;
		SeleniumCore.generateEmailStep("", "Get the extracted PAF Report File",status,comments, driver);
		//String filecontent=FilesUtils.returnFileContents(reporthtmlfile);		
	  }
	  
	  
	}
	

}

