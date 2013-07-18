package com.hp.baserunner;

import java.io.File;
import java.io.FileInputStream;

import static java.io.File.separator;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.thoughtworks.selenium.SeleneseTestBase;
//import org.testng.log4testng.Logger;

public class BaseDriver extends SeleneseTestBase{
	

	protected WebDriver driver;
	protected StringBuffer verificationErrors = new StringBuffer();
	protected String testUrl, browser, hubUrl;


	private static final Logger log=Logger.getLogger(BaseDriver.class);
	
	// you should know there how the annotations worked in testNG 
	//public void setUpBeforeClass(ITestContext context) throws Exception {
	@BeforeSuite(alwaysRun=true)
	@Parameters({ "configfile" })
	public void setUpBeforeClass(String configfile) throws Exception {
		//testUrl = context.getSuite().getParameter("testUrl");
		//browser = context.getSuite().getParameter("browser");
		//hubUrl = context.getSuite().getParameter("hubUrl");
		Properties p = new Properties();
	    FileInputStream  conf = new FileInputStream(configfile);
	    p.load(conf);

	    hubUrl = p.getProperty("hubUrl");
	    browser = p.getProperty("browser");
	    testUrl = p.getProperty("testUrl");
		log.info("The Page URL is:"+testUrl);
		log.info("the browser type is :"+browser);
		log.info("the remote run host hub is :"+hubUrl);
//	}
//
//	@BeforeSuite
//	public void setUp() throws Exception {
		// BasicConfigurator.configure();
		if (hubUrl == null || hubUrl.trim().isEmpty())
		{
			// if no hubUrl specified, run the tests on localhost
			if (browser == null || browser.trim().isEmpty()) {
				// if no browser specified, use IE
				driver = new InternetExplorerDriver();
			} else {
				if (browser.trim().equalsIgnoreCase("firefox")) {
					driver = new FirefoxDriver();
				} else if (browser.trim().equalsIgnoreCase("chrome")) {
					driver = new ChromeDriver();
				} else {
					driver = new InternetExplorerDriver();
				}
			}
		} 
		
		else {
			log.debug("we will run the remote host for the testing ");
			DesiredCapabilities capability = new DesiredCapabilities();
			if (browser.toLowerCase().trim()=="ie")
			{
				//frozen windows 
				log.debug(" the browser we used is IE ");
				capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				capability.setCapability("ignoreProtectedModeSettings",true);
				capability.setCapability("enablePersistentHover", false);  //prevent frozen
			    capability.setBrowserName(browser);
			}
			else if(browser.toLowerCase().trim()=="firefox")
			{
				log.debug("the browser we used is firefox");
				
			}
			else
			{
				log.debug("the browser we used is none");
				
			}
			
			driver = new RemoteWebDriver(new URL(hubUrl), capability);
		}
		//the driver need to wait time 
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@AfterMethod(alwaysRun=true)
	public void tearDown(ITestResult result, ITestContext context)
			throws Exception {
		Throwable t = result.getThrowable();
		log.debug("the throwable object is :"+t);
		//if the testNG met error or exception
		if ((!result.isSuccess())||t instanceof WebDriverException || t instanceof AssertionError) {
			log.debug("WebDriver or Assert Exception");
			// get filename
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
			// concat prefix with current time and return

			String filename = result.getTestClass().getName() + "."
					+ result.getMethod().getMethodName() + "."
					+ sf.format(cal.getTime()) + ".png";
			log.debug("we met the error ,we will generate a screenshot file for this error, file name is "+filename);
			WebDriver augmentedDriver = new Augmenter().augment(driver);
			File scrFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
			String path=new File(".").getAbsolutePath();
			String screenshotpath=path.substring(0, path.length()-1);
			// create a new file 
			FileUtils.copyFile(scrFile, new File(screenshotpath+"log"+separator + filename));
			log.debug("the screenshot file in this file path:"+screenshotpath+"log"+separator + filename);
			Reporter.setCurrentTestResult(result);
			//Reporter.log("<a href=\"" + filename + "\">Screenshot</a>");
		}
		else
		{
			log.debug("This test method is working fine ,we marked it as passed");
		}
		
		
		
//	//	String verificationErrorString = verificationErrors.toString();
//		//if (!"".equals(verificationErrorString)) {
//			//fail(verificationErrorString);
//		//}
//
//		for(int i=0;i<context.getAllTestMethods().length;i++){
//			if(context.getAllTestMethods()[i].getCurrentInvocationCount()==4) //current retry test cases
//			{
//				int failednum=context.getFailedTests().getResults(context.getAllTestMethods()[i]).size();
//				int passednum=context.getPassedTests().getResults(context.getAllTestMethods()[i]).size();
//				System.out.println(" test failed is :"+failednum+",test case pass is :"+passednum);
//			  if ( failednum== 2 || passednum== 1)
//			    {
//			           context.getFailedTests().removeResult(context.getAllTestMethods()[i]);
//			    }
//			}
//			}
	}
	@AfterSuite
	public void quite() throws Exception{
	driver.quit();
	log.debug("quit the driver now ");
	}
	


}
