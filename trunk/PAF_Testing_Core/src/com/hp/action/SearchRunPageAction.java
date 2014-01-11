/**
 * Project Name:PAF_Testing_Core
 * File Name:SearchRun_Page.java
 * Package Name:com.hp.pop
 * Date:Sep 27, 20139:32:42 AM
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
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.hp.po.SearchRunPage;
import com.hp.utility.TimeUtils;

/**
 * ClassName:SearchRun_Page 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 TODO ADD REASON. 
 * Date:     Sep 27, 2013 9:32:42 AM 
 * @author   huchan

 * @since    JDK 1.6
 * @see 	 
 * @version $Revision: 1.0 $
 */
/**
 * @author huchan
 *
 */
public class SearchRunPageAction extends SearchRunPage {

		
	/**
	 * @param driver
	 */
	public SearchRunPageAction(WebDriver driver) {
		this.driver=driver;
		// TODO Auto-generated constructor stub
	}
	  
   /**
    * Method downloadRun.
    * @param assessmenttype String
    * @param runid String
    * @param runstarttime String
    * @return SearchRunDetailPageAction
    * @throws IOException
    */
   public SearchRunDetailPageAction downloadRun(String assessmenttype, String runid,String runstarttime) throws IOException{
	   boolean findresult = false;	
		int searchcount = 0;
		//String finalstate="";
		
		String starttime=TimeUtils.getCurrentTime(Calendar.getInstance().getTime());
		
		while (!findresult) {		
			logger.info("Now go to the run result page to search the run result,the run ID is:"
					+ runid+",firstly ,refresh the page to make the session valid....");
		   // SeleniumCore.refreshPage(driver);
			if (assessmenttype.toLowerCase().equals("fwsw")) {
				selectElementViaText(driver.findElement(By.xpath("//*[@id='analysisTypeCode']")),
						"FW & SW Release Analysis");
			}
			if (assessmenttype.toLowerCase().equals("pc")) {
				selectElementViaText(driver.findElement(By.xpath("//*[@id='analysisTypeCode']")),
						"Proactive Scan");
			}
			if (assessmenttype.toLowerCase().equals("shc")) {
				selectElementViaText(driver.findElement(By.xpath("//*[@id='analysisTypeCode']")),
						"System Health Check");
			}
			// SeleniumCore.clickElement(driver, iscompletedcheckbox);
			checkboxed(driver.findElement(By.xpath("//*[@id='isCompleted']")));
			checkboxed(driver.findElement(By.xpath("//*[@id='isStarted']")));
			checkboxed(driver.findElement(By.xpath("//*[@id='isFailed']")));			
			checkboxed(driver.findElement(By.xpath("//*[@id='isPending']")));

			String runstartenddate = new SimpleDateFormat("MM/dd/yyyy")
					.format(Calendar.getInstance().getTime());

			setDateTimePicker("fromDtOfStartTime",
					runstartenddate);
			setDateTimePicker("toDtOfStartTime",
					runstartenddate);		
			searchcount = searchcount + 1;
								
           clickElement(driver.findElement(By.xpath("//*[@id='Search']")));
		   waitProcessBarNotAppear( driver.findElement(By
					.xpath(".//*[@id='hpit-busy']/img")));
//***********************************************************************************
			//if the session is invalid ,we will refresh the page again and then search the result
			boolean refreshpage=false;
			try{
			   driver.findElement(By.xpath(".//*[@id='dialog-sessionTimeout-confirm']/p"));
			   refreshpage=true;
			}catch(NoSuchElementException sessionelement){
				logger.info("the session current page is valid ,we will not refresh the page again..."+sessionelement.getMessage());
			}			
			if(refreshpage){
			   logger.info("We found that the session id had been invalid ,we will refresh the page again to find the result...");
			   refreshPage();
			   
			   clickElement(driver.findElement(By.xpath("//*[@id='Search']")));
			   waitProcessBarNotAppear(driver.findElement(By
						.xpath(".//*[@id='hpit-busy']/img")));
			   tablelist=driver.findElement(By.xpath("//*[@id='reportList']"));
			}
//***********************************************************************************
			WebElement secondrow = null;
			boolean isempty = true;
			try {
				secondrow = tablelist.findElement(By.xpath("tbody/tr[2]"));
				isempty = false;
			} catch (NoSuchElementException exception) {
				logger.info("as the search list is empty ,throw this exception:"+exception.getMessage());
				logger.info("the result list is empty ,we will try to search the result again ......" );
			}
			// if the search result is not empty ,we will just find the result
			String runstatus="";
			String finalstate;
			if (!isempty) {
				boolean showedtable = isElementPresentAndDisplay(secondrow);
				if (showedtable) {
					List<WebElement> allrows = tablelist.findElements(By
							.tagName("tr"));
					for (WebElement row : allrows) {
						WebElement assessmentrunid = row.findElement(By
								.xpath("td[2]"));
						String pagerunid = assessmentrunid.getText().trim();
						runstatus=row.findElement(By.xpath("td[7]")).getText().trim();
						logger.info("in the table list ,the found assessment run id  is:"+ pagerunid+",the run status is:"+runstatus+"...");
						logger.info("this is the "+searchcount+" times to search the result ...");
						//if the run status is completed
						if (pagerunid.equals(runid)&&runstatus.equalsIgnoreCase("COMPLETED")) {
														
							String endtime=TimeUtils.getCurrentTime(Calendar.getInstance().getTime());
							String totaltime=TimeUtils.howManyMinutes(starttime, endtime);
							finalstate=runstatus;
							logger.info("Now we found the matched Run ID status is completed ,and we will check the box and then download the report.");
						    status="Passed";
						    comments="Expected:COMPLATED;Actual:"+runstatus+",the Search Run takes:"+totaltime;
							reporterNewStep("Search the Run ID:"+runid, "Check the Run Assessment Status is Completed or Failed or Pending with 10 minutes Search", 
									status,comments, driver);
							WebElement checkbox = row.findElement(By
									.xpath("td[1]"));
							checkboxed(checkbox);
							WebElement downloadfile = row.findElement(By
									.xpath("td[8]/input"));
							clickElement(downloadfile);
							findresult = true;
							break;
						}
						if (pagerunid.equals(runid)&&runstatus.equalsIgnoreCase("FAILED")) {
							String endtime=TimeUtils.getCurrentTime(Calendar.getInstance().getTime());
							String totaltime=TimeUtils.howManyMinutes(starttime, endtime);
							finalstate=runstatus;
							logger.info("Now we found the matched Run ID status is failed ,maybe we need to retry this assessment again.");
							status="Failed";
							comments="Expected:COMPLATED;Actual:"+finalstate+",the Search Run takes:"+totaltime;
							reporterNewStep("Search the Run ID:"+runid, "Check the Run Assessment Status is Completed or Failed or Pending with 10 minutes Search", 
									status,comments, driver);							
							WebElement checkbox = row.findElement(By
									.xpath("td[1]"));
							checkboxed(checkbox);
							WebElement retryfile = row.findElement(By
									.xpath("td[8]/input"));
							clickElement(retryfile);
							findresult=true;
							break;
						}
						if (pagerunid.equals(runid)&&runstatus.equalsIgnoreCase("STARTED")) {
							//String endtime=SeleniumCore.getCurrentTime(Calendar.getInstance().getTime());
							//String totaltime=SeleniumCore.timeLastMinutes(starttime, endtime);
							sleepSeconds(30);
							logger.info("Now we found the matched Run ID status is STARTED ,so we will wait a few moments then continue to search this RUN ID again......");						
						    break;
						}

					}
				} else {
					logger.error("Sorry we cannot showed the table list in the page... ");
				}
			} else {
				logger.info("the search run result list is empty... ");
			}
			if (searchcount > 10) {
				String endtime=TimeUtils.getCurrentTime(Calendar.getInstance().getTime());
				String totaltime=TimeUtils.howManyMinutes(starttime, endtime);
				finalstate=runstatus;
				
				logger.error("Time out,Sorry we cannot find this result and had tried to search it with :"
						+ searchcount
						+ " times,and time had took about 10 minutes");
				status="Passed";
				comments="Expected:COMPLATED;Actual:"+finalstate+",the Search Run takes:"+totaltime;
				reporterNewStep("Search the Run ID:"+runid, "Check the Run Assessment Status is Completed or Failed or Pending with 10 minutes Search", 
						status,comments, driver);
	           Assert.fail("We cannot find the RUN ID after 10 minutes,so this test gives failed result "
						+ ",the Run ID is:"+runid+",Had run it from: "+runstarttime+",end search time is:"+endtime);
				break;
			}
			logger.info("Now this is the :"
					+ searchcount + " time to search the Run Result ID :"+runid+" ,now the status is: "+runstatus+" ,so we will wait the status changed to completed or failed or pending......");
		}
		
	   return PageFactory.initElements(driver, SearchRunDetailPageAction.class);
   }
}

