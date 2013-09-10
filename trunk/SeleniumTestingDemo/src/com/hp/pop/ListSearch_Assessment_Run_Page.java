package com.hp.pop;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.hp.utility.Resulter;
import com.hp.utility.SeleniumCore;
import com.hp.utility.TimeUtils;

public class ListSearch_Assessment_Run_Page {

	private static Logger logger = Logger
			.getLogger(ListSearch_Assessment_Run_Page.class);
	private WebDriver driver;
	@CacheLookup
	@FindBy(how = How.XPATH, using = "//*[@id='content']/div/h2")
	
	private WebElement header;
	@CacheLookup
	@FindBy(how = How.XPATH, using = "//*[@id='analysisTypeCode']")

	private WebElement assessmenttypelist;
	@FindBy(how = How.XPATH, using = "//*[@id='isCompleted']")
	private WebElement iscompletedcheckbox;
	@CacheLookup
	@FindBy(how = How.XPATH, using = "//*[@id='isStarted']")
	
	private WebElement isstartedcheckbox;
	@CacheLookup
	@FindBy(how = How.XPATH, using = "//*[@id='isFailed']")
	private WebElement isfailedcheckbox;
	@CacheLookup
	@FindBy(how = How.XPATH, using = "//*[@id='isPending']")
	private WebElement ispendingcheckbox;
	@CacheLookup
	@FindBy(how = How.XPATH, using = "//*[@id='fromDtOfStartTime']")
	private WebElement runstartdate;
	@CacheLookup
	@FindBy(how = How.XPATH, using = "//*[@id='toDtOfStartTime']")
	private WebElement runenddate;
	@CacheLookup
	@FindBy(how = How.XPATH, using = "//*[@id='customerPattern']")
	private WebElement customername;
	@CacheLookup
	@FindBy(how = How.XPATH, using = "//*[@id='emailAddress']")
	private WebElement emailaddress;
	@CacheLookup
	@FindBy(how = How.XPATH, using = "//*[@id='Search']")
	private WebElement searchbtn;
	@CacheLookup
	@FindBy(how = How.XPATH, using = "//*[@id='Search']/following-sibling::input[1]")
	private WebElement resetbtn;
	
	@CacheLookup
	@FindBy(how = How.XPATH, using = "//*[@id='reportList']")
	private WebElement tablelist;

	public ListSearch_Assessment_Run_Page(WebDriver driver) {
		this.driver = driver;
	}

	public void verifyPageElements() {
		String pagename=this.getClass().getName();
		logger.info("\n***************************************"+pagename+"****************************************************");       
		logger.info("Search run Result page .this page head is:"+ header.getText());
		SeleniumCore.assertDisplayed("Assert the assessment type list is displayed in the page", assessmenttypelist);
		SeleniumCore.assertEnabled("Assert the search button is enabled in the page", searchbtn);
		SeleniumCore.assertEnabled("Assert the reset button is enabled in the page", resetbtn);
	
	}

	public RunDetail_Page searchRun(String assessmenttype, String runid,String runstarttime)
			throws Exception {

		boolean findresult = false;	
		int searchcount = 0;
		String finalstate;
		
		String starttime=TimeUtils.getCurrentTime(Calendar.getInstance().getTime());
		
		while (!findresult) {		
			logger.info("Now go to the run result page to search the run result,the run ID is:"
					+ runid+",firstly ,refresh the page to make the session valid....");
		   // SeleniumCore.refreshPage(driver);
			if (assessmenttype.toLowerCase().equals("fwsw")) {
				SeleniumCore.selectElementViaText(driver.findElement(By.xpath("//*[@id='analysisTypeCode']")),
						"FW & SW Release Analysis");

			}
			if (assessmenttype.toLowerCase().equals("pc")) {
				SeleniumCore.selectElementViaText(driver.findElement(By.xpath("//*[@id='analysisTypeCode']")),
						"Proactive Scan");
			}
			if (assessmenttype.toLowerCase().equals("shc")) {
				SeleniumCore.selectElementViaText(driver.findElement(By.xpath("//*[@id='analysisTypeCode']")),
						"System Health Check");
			}

			// SeleniumCore.clickElement(driver, iscompletedcheckbox);
			SeleniumCore.checkboxed(driver.findElement(By.xpath("//*[@id='isCompleted']")));
			SeleniumCore.checkboxed(driver.findElement(By.xpath("//*[@id='isStarted']")));
			SeleniumCore.checkboxed(driver.findElement(By.xpath("//*[@id='isFailed']")));			
			SeleniumCore.checkboxed(driver.findElement(By.xpath("//*[@id='isPending']")));

			String runstartenddate = new SimpleDateFormat("MM/dd/yyyy")
					.format(Calendar.getInstance().getTime());

			SeleniumCore.setDateTimePicker(driver, "fromDtOfStartTime",
					runstartenddate);
			SeleniumCore.setDateTimePicker(driver, "toDtOfStartTime",
					runstartenddate);
			
			searchcount = searchcount + 1;
			
			
			WebElement e = driver.findElement(By
					.xpath(".//*[@id='hpit-busy']/img"));
			
            SeleniumCore.clickElement(driver, driver.findElement(By.xpath("//*[@id='Search']")));
			SeleniumCore.waitForObjectDisplay(e);
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
			   SeleniumCore.refreshPage(driver);
			 //  ListSearch_Assessment_Run_Page lsap=PageFactory.initElements(driver, ListSearch_Assessment_Run_Page.class);
			   SeleniumCore.clickElement(driver, driver.findElement(By.xpath("//*[@id='Search']")));
			   SeleniumCore.waitForObjectDisplay(driver.findElement(By
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
			if (!isempty) {
				boolean showedtable = SeleniumCore.isDisplayed(secondrow);
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
							String totaltime=TimeUtils.timeLastMinutes(starttime, endtime);
							finalstate=runstatus;
							logger.info("Now we found the matched Run ID status is completed ,and we will check the box and then download the report.");
							Resulter.log("STATUS_RUN_ID", "Passed");
							Resulter.log("COMMENT_RUN_ID", "RUN ID:"+runid+",STATUS:"+finalstate+",Search Time Takes :"+totaltime+" minutes");
							WebElement checkbox = row.findElement(By
									.xpath("td[1]"));
							SeleniumCore.checkboxed(checkbox);
							WebElement downloadfile = row.findElement(By
									.xpath("td[8]/input"));
							SeleniumCore.clickElement(driver, downloadfile);
							findresult = true;
							break;
						}
						if (pagerunid.equals(runid)&&runstatus.equalsIgnoreCase("FAILED")) {
							String endtime=TimeUtils.getCurrentTime(Calendar.getInstance().getTime());
							String totaltime=TimeUtils.timeLastMinutes(starttime, endtime);
							finalstate=runstatus;
							logger.info("Now we found the matched Run ID status is failed ,maybe we need to retry this assessment again.");
							Resulter.log("STATUS_RUN_ID", "Failed");
							Resulter.log("COMMENT_RUN_ID", "RUN ID:"+runid+"STATUS:"+finalstate+",Search Time Takes :"+totaltime+" minutes");
							WebElement checkbox = row.findElement(By
									.xpath("td[1]"));
							SeleniumCore.checkboxed(checkbox);
							WebElement retryfile = row.findElement(By
									.xpath("td[8]/input"));
							SeleniumCore.clickElement(driver, retryfile);
							findresult=true;
							break;
						}
						if (pagerunid.equals(runid)&&runstatus.equalsIgnoreCase("STARTED")) {
							//String endtime=SeleniumCore.getCurrentTime(Calendar.getInstance().getTime());
							//String totaltime=SeleniumCore.timeLastMinutes(starttime, endtime);
							SeleniumCore.sleepSeconds(50);
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
				String totaltime=TimeUtils.timeLastMinutes(starttime, endtime);
				finalstate=runstatus;
				
				logger.error("Time out,Sorry we cannot find this result and had tried to search it with :"
						+ searchcount
						+ " times,and time had took about 10 minutes");
				Resulter.log("STATUS_RUN_ID", "Failed");
				Resulter.log("COMMENT_RUN_ID", "RUN ID:"+runid+"STATUS is:"+finalstate+" Canot be found,Run ID Run From:"+runstarttime+",End Search time:"+endtime+",Search Time Takes :"+totaltime+" minutes");
				Assert.fail("We cannot find the RUN ID after 10 minutes,so this test gives failed result "
						+ ",the Run ID is:"+runid+",Had run it from: "+runstarttime+",end search time is:"+endtime);
				break;
			}
			logger.info("Now this is the :"
					+ searchcount + " time to search the Run Result ID :"+runid+" ,now the status is: "+runstatus+" ,so we will wait the status changed to completed or failed or pending......");
			
			
			

		}
		
		// next do
		return PageFactory.initElements(driver, RunDetail_Page.class);

	}

}
