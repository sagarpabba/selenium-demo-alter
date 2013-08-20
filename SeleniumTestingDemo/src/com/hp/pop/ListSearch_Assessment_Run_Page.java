package com.hp.pop;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.hp.utility.Resulter;
import com.hp.utility.SeleniumCore;
import com.hp.utility.TimerUtils;

public class ListSearch_Assessment_Run_Page {

	private static Logger logger = Logger
			.getLogger(ListSearch_Assessment_Run_Page.class);
	private WebDriver driver;

	@FindBy(how = How.XPATH, using = ".//*[@id='content']/div/h2")
	private WebElement header;
	@FindBy(how = How.XPATH, using = ".//*[@id='analysisTypeCode']")
	private WebElement assessmenttypelist;
	@FindBy(how = How.XPATH, using = ".//*[@id='isCompleted']")
	private WebElement iscompletedcheckbox;
	@FindBy(how = How.XPATH, using = ".//*[@id='isStarted']")
	private WebElement isstartedcheckbox;
	@FindBy(how = How.XPATH, using = ".//*[@id='isFailed']")
	private WebElement isfailedcheckbox;
	@FindBy(how = How.XPATH, using = ".//*[@id='isPending']")
	private WebElement ispendingcheckbox;
	@FindBy(how = How.XPATH, using = ".//*[@id='fromDtOfStartTime']")
	private WebElement runstartdate;
	@FindBy(how = How.XPATH, using = ".//*[@id='toDtOfStartTime']")
	private WebElement runenddate;
	@FindBy(how = How.XPATH, using = ".//*[@id='customerPattern']")
	private WebElement customername;
	@FindBy(how = How.XPATH, using = ".//*[@id='emailAddress']")
	private WebElement emailaddress;
	@FindBy(how = How.XPATH, using = ".//*[@id='Search']")
	private WebElement searchbtn;
	@FindBy(how = How.XPATH, using = ".//*[@id='searchFrom']/input[2]")
	private WebElement resetbtn;

	@FindBy(how = How.XPATH, using = ".//*[@id='reportList']")
	private WebElement tablelist;

	public ListSearch_Assessment_Run_Page(WebDriver driver) {
		this.driver = driver;
	}

	public void verifyPageElements() {
		String pagename=this.getClass().getName();
		logger.info("\n***************************************"+pagename+"****************************************************");

		logger.info("Search run Result page .this page head is:"
				+ header.getText());
		boolean typelist = assessmenttypelist.isDisplayed();
		boolean searchenabled = searchbtn.isEnabled();
		boolean resetenabled = resetbtn.isEnabled();
		if (typelist && searchenabled && resetenabled) {
			logger.info("Search run result page .found all the elements in the page ");
		} else {
			logger.error("Search run result page.we cannot find some elements in the page,the test is failed");
			Assert.fail();

		}
	}

	public RunDetail_Page searchRun(String assessmenttype, String runid,String runstarttime)
			throws Exception {

		boolean findresult = false;	

		int searchcount = 0;
		while (!findresult) {
			
			
			logger.info("Now go to the run result page to search the run result,the run ID is:"
					+ runid);
			if (assessmenttype.toLowerCase().equals("fwsw")) {
				SeleniumCore.selectElementViaText(assessmenttypelist,
						"FW & SW Release Analysis");

			}
			if (assessmenttype.toLowerCase().equals("pc")) {
				SeleniumCore.selectElementViaText(assessmenttypelist,
						"Proactive Scan");
			}
			if (assessmenttype.toLowerCase().equals("shc")) {
				SeleniumCore.selectElementViaText(assessmenttypelist,
						"System Health Check");
			}

			// SeleniumCore.clickElement(driver, iscompletedcheckbox);
			SeleniumCore.selectCheckbox(iscompletedcheckbox);

			String runstartenddate = new SimpleDateFormat("MM/dd/yyyy")
					.format(Calendar.getInstance().getTime());

			SeleniumCore.setDateTimePicker(driver, "fromDtOfStartTime",
					runstartenddate);
			SeleniumCore.setDateTimePicker(driver, "toDtOfStartTime",
					runstartenddate);
			
			searchcount = searchcount + 1;
			logger.info("Now we begin to search the Run result ,this is the running :"
					+ searchcount + " times......");
			SeleniumCore.clickElement(searchbtn);
			WebElement e = driver.findElement(By
					.xpath(".//*[@id='hpit-busy']/img"));
			SeleniumCore.waitForObjectDisplay(e);
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
			if (!isempty) {
				boolean showedtable = SeleniumCore.isDisplayed(secondrow);
				if (showedtable) {
					List<WebElement> allrows = tablelist.findElements(By
							.tagName("tr"));
					for (WebElement row : allrows) {
						WebElement assessmentrunid = row.findElement(By
								.xpath("td[2]"));
						String pagerunid = assessmentrunid.getText().trim();
						logger.info("the found assessment run id  is:"
								+ pagerunid);
						if (pagerunid.equals(runid)) {
							logger.info("Now we found the matched Run ID ,and we will check the box and then download the report.");
							Resulter.log("STATUS_RUN_ID", "Passed");
							Resulter.log("COMMENT_RUN_ID", "RUN ID:"+runid);
							WebElement checkbox = row.findElement(By
									.xpath("td[1]"));
							SeleniumCore.selectCheckbox(checkbox);
							WebElement downloadfile = row.findElement(By
									.xpath("td[8]/input"));
							SeleniumCore.clickElement(downloadfile);
							findresult = true;
							break;
						}

					}
				} else {
					logger.error("Sorry we cannot showed the table list in the page ");
				}
			} else {
				logger.info("the search run result list is empty ");
			}
			if (searchcount > 10) {
				String endtime=TimerUtils.getCurrentTime(Calendar.getInstance().getTime());
				logger.error("Time out,Sorry we cannot find this result and had tried to search it with :"
						+ searchcount
						+ " times,and time had took about 10 minutes");
				Resulter.log("STATUS_RUN_ID", "Failed");
				Resulter.log("COMMENT_RUN_ID", "RUN ID:"+runid+",Canot be found,Run ID Run From:"+runstarttime+",End Search time:"+endtime);
				Assert.fail("We cannot find the RUN ID after 10 minutes,so this test gives failed result "
						+ ",the Run ID is:"+runid+",Had run it from: "+runstarttime+",end search time is:"+endtime);
				break;
			}
			SeleniumCore.sleepSeconds(60);

		}

		// next do
		return PageFactory.initElements(driver, RunDetail_Page.class);

	}

}
