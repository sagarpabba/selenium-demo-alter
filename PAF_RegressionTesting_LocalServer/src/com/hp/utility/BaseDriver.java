package com.hp.utility;

import static java.io.File.separator;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;



import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
//import org.openqa.selenium.remote.Augmenter;
//import org.openqa.selenium.remote.CapabilityType;
//import org.openqa.selenium.remote.DesiredCapabilities;
//import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

/**
 * this is the RemoteWebDriver instance ,every testNG test need to extends this class ,so we can catch
 * and setup our driver easily
 * @author huchangyuan
 *
 */
public class BaseDriver{

	protected  static WebDriver driver;

	protected static final Logger logger = Logger.getLogger(BaseDriver.class);

	
	
	@BeforeSuite(description="this test run before our testNG test suite,it only run once ")
	@Parameters({"browsertype"})
	public void setupDriver(String browsertype,ITestContext context) throws Exception 
	{
		String runhostname=HostUtils.getFQDN();
//		String browsername=context.getCurrentXmlTest().getParameter("browsername");
//		String proxyserver=context.getCurrentXmlTest().getParameter("proxyserver");
		//String runhostname=hostname;
		String browsername=browsertype;
	//	String proxyserver=context.getCurrentXmlTest().getParameter("proxyserver");
		String osversion=HostUtils.getOperatingSystemName()+"-"+HostUtils.getOperatingSystemVersion()+" ("+HostUtils.getOSType()+")";
/*********************************************************************************************/	
		SeleniumCore.generateEmailData("Execution Start Time",TimeUtils.getCurrentTime(Calendar.getInstance().getTime()));
/*********************************************************************************************/
		logger.debug("Now the automation testing will be run in this host FQDN is :" + runhostname);	
		logger.debug("Now we using  browser type is :" + browsername);
		
		logger.debug("Now we will run the selenium as the local host for the testing ......");
		//this is used for the perfermance testing
		//ProxyServer 
		//server = new ProxyServer(4444);
		//server.start();
		//Proxy proxy=server.seleniumProxy();
		
		DesiredCapabilities capability=new DesiredCapabilities();
		
		// common settings
		capability.setCapability("cssSelectorsEnabled", true);
		capability.setCapability("takesScreenshot", true);
		capability.setCapability("javascriptEnabled", true);
		capability.setCapability("ignoreZoomSetting",true);
		capability.setCapability("ignoreProtectedModeSettings", true);
		capability.setCapability("enablePersistentHover", false); // prevent
		capability.setCapability("EnableNativeEvents", false);	
		capability.setCapability("acceptSslCerts", true); //accept the securty ssl url
		capability.setCapability("unexpectedAlertBehaviour", "accept");
		
		org.openqa.selenium.Proxy httpproxy = new org.openqa.selenium.Proxy();  
		//httpproxy.setHttpProxy(proxyserver); 
		//httpproxy.setSslProxy(proxyserver);
	
		httpproxy.setNoProxy("localhost");
	//	capability.setCapability(CapabilityType.PROXY,proxy);
		
		if (browsername.trim().equalsIgnoreCase("ie")) 
		{
			logger.debug("running testing server is using IE......");
			String iedriver=SeleniumCore.getProjectWorkspace()+"resources"+File.separator+"IEDriverServer.exe";
			String ielogfile=SeleniumCore.getProjectWorkspace()+"log"+File.separator+"selenium_ie_log.log";
			logger.debug("the IE driver path is:"+iedriver);
			//System.setProperty("webdriver.ie.driver",iedriver);
			// frozen windows
			capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capability.setCapability(InternetExplorerDriver.NATIVE_EVENTS,false);
			capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);	
			capability.setCapability(InternetExplorerDriver.LOG_LEVEL, "TRACE");
			capability.setCapability(InternetExplorerDriver.LOG_FILE, ielogfile);
			driver=new InternetExplorerDriver(capability);																	
		 }
		 if (browsername.trim().equalsIgnoreCase("firefox")) 
		 {
			logger.debug("the browser we used is firefox......");
		//	String firefoxlogfile=SeleniumCore.getProjectWorkspace()+"log"+File.separator+"selenium_firefox_log.log";
			//support firefox 23 with clickable the object
			capability.setCapability(FirefoxDriver.PROFILE, FirefoxProfileFile.setFirefoxProfile());
			//capability.setCapability(FirefoxDriver.ACCEPT_UNTRUSTED_CERTIFICATES, true);
			driver=new FirefoxDriver(capability);
		
		  } 
		 if(browsername.trim().equalsIgnoreCase("chrome")) 
		  {
			  logger.debug("the browser we used is Chrome......");
			  //set the chrome system path
			  String chromedriver=SeleniumCore.getProjectWorkspace()+"resources"+File.separator+"chromedriver.exe";
			  System.setProperty("webdriver.chrome.driver",chromedriver);
			  logger.debug("the chrome driver path is:"+chromedriver);
			  //accept the ssl error
			  capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			  //disabled all the extensions
			  capability.setCapability("chrome.switches", Arrays.asList("--disable-extensions"));
			  capability.setCapability("chrome.switches", Arrays.asList("--disable-logging"));
			  //capability.setCapability("chrome.binary","C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
			  driver=new ChromeDriver(capability);
		  }
			
	    logger.debug("the proxy server had been set for the browser correctly now ......");		
	    //start the proxy selenium grid 2 server
		Capabilities actualCapabilities = ((RemoteWebDriver) driver).getCapabilities();
	//	driver.setFileDetector(new LocalFileDetector());
		logger.debug("Selenium server using capabilities are blow :"+ actualCapabilities.toString());
		// the driver need to wait time
		driver.manage().window().maximize();
		//log for a value data in our report
/************************************************************************************************/
		SeleniumCore.generateEmailData("Host Name", runhostname);
		SeleniumCore.generateEmailData("Host Operation System With Version", osversion);
		SeleniumCore.generateEmailData("Browser Type and Version", SeleniumCore.getBrowserType(driver));
						
/************************************************************************************************/			
		try
		{
			//page load time
			driver.manage().timeouts().pageLoadTimeout(150, TimeUnit.SECONDS);
			//the web element to find time we need to wait 
		    driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		    // the js executor timeout
		    driver.manage().timeouts().setScriptTimeout(140, TimeUnit.SECONDS);
		   
		 }
		catch(TimeoutException e){
			logger.error("The page or the webelment had been waited for 120 second,it cannot showed ,so the test failed");
			Assert.fail("The page or the webelment had been waited for 120 second,it cannot showed ,so the test failed");
		}
	}
	@AfterMethod
	public void errorReport(ITestResult result, ITestContext context)throws Exception {
		final Throwable t = result.getThrowable();
		// logger.debug("Involved the After Method from Parent class,the current throwable object is :"+t);
		// if the testNG met error or exception
		// if me the webdriver error
		boolean userecovery=false;
		if(t instanceof Exception){
			//boolean findfirefoxerror=RecoveryScenario.firefoxAddException(driver);
			userecovery=RecoveryScenario.invokeAllRecoveries(driver);
		}
	   if(!userecovery){
		   
		   logger.debug("Sorry ,we had not used the recovery to catch the exception ,so we will capture this error screenshot ");
           logger.debug("the throwable instance now is:"+t);
		   if(t instanceof WebDriverException) {
				logger.error("Sorry ,now we met the WebDriver Exception "+t.getMessage()+",maybe you had not opened the browser caused this ");
					// String errorType="WebDriverException";
					// captureErrorScreenshot(result,errorType);
			}
			// if met the assert error
			if (t instanceof AssertionError) {
				logger.error("Sorry ,now we met the Assertion Error ,the Assert statement met error expecting result with the actual result ");
				String errorType = "AssertionError";
				SeleniumCore.generateEmailStep("", "Test Ocurred the AssertionError", "Failed", "This Error happened when we met the checkpoint error manually,please see the blow screenshot for detail", driver);
				captureErrorScreenshot(result, errorType);
			}
				// if met the element cannot find in the page
			if (t instanceof NoSuchElementException) {
				logger.error("Sorry ,now we met the NoSuchElement Exception ,that means we cannot find the element in the page ,make sure you can identify the object correctly ");
				String errorType = "NoSuchElementException";
				SeleniumCore.generateEmailStep("", "We cannot find the Page Object in current step", "Failed", "Current page not showed currectly,we cannot find the page object we need ,please see the blow screenshot for detail", driver);
				captureErrorScreenshot(result, errorType);
			}
	   }else{
		   logger.debug("the throwable instance now is:"+t);
	   }
	

	}

	@AfterSuite(description="this is used for closing the browser instance ,but for our debug.it's better not to close it")
	public void tearDown() {		
	  //  driver.quit();
		//server.stop();
		logger.debug("Involved AfterSuite method from parent.Now we quite the Browser instance");
	}

	// capture the error screenshot if ocurred the error
	public void captureErrorScreenshot(ITestResult result, String errortype)
			throws IOException {
		String filename = result.getTestClass().getName()
				+ "."
				+ result.getMethod().getMethodName()
				+ "."
				+ errortype
				+ "."
				+ new SimpleDateFormat("yyyy-MM-dd-HHmmss").format(Calendar
						.getInstance().getTime()) + ".png";
		logger.debug("we met the error,we will generate a screenshot file for this error, file name is "
				+ filename);

		//File scrFile = ((TakesScreenshot) new Augmenter().augment( driver ))
	    File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		logger.debug("the source screenshot file is :"
				+ scrFile.getCanonicalPath());
		String path = new File(".").getAbsolutePath();
		String screenshotpath = path.substring(0, path.length() - 1);
		logger.debug("the saved screenshot path is :" + screenshotpath);
		// create a new file
		// try {
		FileUtils.copyFile(scrFile, new File(screenshotpath + "reporter"
				+ separator + filename));
		// } catch (IOException e) {

		// e.printStackTrace();
		// logger.error("Sorry,we cannot save the error screenshot file for this file,Catch the IOException :"+screenshotpath);
		// }
		logger.debug("the screenshot file saved in this file path:"
				+ screenshotpath + "screenshot" + separator + filename);
		Reporter.setCurrentTestResult(result);
	}


}
