package com.hp.utility;

import static java.io.File.separator;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

/**
 * @author huchan
 *
 */
public class BaseDriver{

	protected static WebDriver driver;
	protected static final Logger logger = Logger.getLogger(BaseDriver.class);

	@BeforeSuite
	public void setupDriver(ITestContext  context) throws IOException 
	{
		String runhostname=context.getCurrentXmlTest().getParameter("hostname");
		String browsername=context.getCurrentXmlTest().getParameter("browsername");
		String proxyserver=context.getCurrentXmlTest().getParameter("proxyserver");
		Resulter.log("COMMENT_HOST_NAME", runhostname);
		logger.info("Now the automation testing will be run in this host FQDN is :" + runhostname);	
		logger.info("Now we using  browser type is :" + browsername);
		// logger.debug("Now we running the remote run host hub is :"+hubUrl);
		logger.info("Now we using the  browser's  proxy server is :" + proxyserver);
		logger.info("Now we will run the selenium grid 2 as the remote host for the testing ......");
		
		DesiredCapabilities capability=null;
		if (browsername.trim().equalsIgnoreCase("ie")) 
		{
			logger.debug("running testing server is using IE......");
			String iedriver=SeleniumCore.getProjectWorkspace()+"resources"+File.separator+"IEDriverServer.exe";
			logger.info("the IE driver path is:"+iedriver);
			//System.setProperty("webdriver.ie.driver",iedriver);
			// frozen windows
			capability = DesiredCapabilities.internetExplorer();
			capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capability.setCapability(InternetExplorerDriver.NATIVE_EVENTS,false);
			capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);	
			
																	
		 }
		 if (browsername.trim().equalsIgnoreCase("firefox")) 
		 {
			logger.info("the browser we used is firefox......");
			capability = DesiredCapabilities.firefox();
			//support firefox 23 with clickable the object
			capability.setCapability(FirefoxDriver.PROFILE, FirefoxProfileFile.setFirefoxProfile());
			
			// if need the proxy
		  } 
		 if(browsername.trim().equalsIgnoreCase("chrome")) 
		  {
			  logger.info("the browser we used is Chrome......");
			  //set the chrome system path
			  String chromedriver=SeleniumCore.getProjectWorkspace()+"resources"+File.separator+"chromedriver.exe";
			//  System.setProperty("webdriver.chrome.driver",chromedriver);
			  logger.info("the chrome driver path is:"+chromedriver);
			  capability = DesiredCapabilities.chrome();	
			  //accept the ssl error
			  capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			  //disabled all the extensions
			  capability.setCapability("chrome.switches", Arrays.asList("--disable-extensions"));
			  capability.setCapability("chrome.switches", Arrays.asList("--disable-logging"));
			  //capability.setCapability("chrome.binary","C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
			  logger.info("the browser we used is none");
		  }
			
		// common settings
		capability.setCapability("cssSelectorsEnabled", true);
		capability.setCapability("takesScreenshot", true);
		capability.setCapability("javascriptEnabled", true);
		capability.setCapability("ignoreZoomSetting",true);
		capability.setCapability("ignoreProtectedModeSettings", true);
		capability.setCapability("enablePersistentHover", false); // prevent
		capability.setCapability("EnableNativeEvents", false);		
		
		org.openqa.selenium.Proxy httpproxy = new org.openqa.selenium.Proxy();  
		httpproxy.setHttpProxy(proxyserver); 
		httpproxy.setSslProxy(proxyserver);
		//httpproxy.setNoProxy("localhost");
		capability.setCapability(CapabilityType.PROXY,httpproxy);
	    logger.info("the proxy server had been set for the browser correctly now ......");	
		
	    //start the proxy selenium grid 2 server
		try {
			logger.info("we are going to start the remote seleinum server and open the browser ,this may takes a few minutes...... ");
			driver = new RemoteWebDriver(new URL("http://"+runhostname+":4444/wd/hub"),capability);		
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			logger.error("Selenium grid cannot use the specified URL to run the tests ,the host is:"+runhostname+",met error is:"+e1.getMessage());
		}
		Capabilities actualCapabilities = ((RemoteWebDriver) driver).getCapabilities();
		logger.info("Selenium server using capabilities are blow :"+ actualCapabilities.toString());
		// the driver need to wait time
		driver.manage().window().maximize();
		try{
			//page load time
			driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
			//the web element to find time we need to wait 
		    driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		    // the js executor timeout
		    driver.manage().timeouts().setScriptTimeout(40, TimeUnit.SECONDS);
		   
		}
		catch(TimeoutException e){
			logger.error("The page or the webelment had been waited for 120 second,it cannot showed ,so the test failed");
			Assert.fail("The page or the webelment had been waited for 120 second,it cannot showed ,so the test failed");
		}
	}
	@AfterMethod
	public void errorReport(ITestResult result, ITestContext context)throws Exception {
		Throwable t = result.getThrowable();
		// logger.debug("Involved the After Method from Parent class,the current throwable object is :"+t);
		// if the testNG met error or exception
		// if me the webdriver error
		//boolean userecovery=false;
		//if(t instanceof Exception){
			//boolean findfirefoxerror=RecoveryScenario.firefoxAddException(driver);
		//	boolean findierror=RecoveryScenario.ieContinueToWebsite(driver);
		//	userecovery=findfirefoxerror||findierror;
		//}
	
		if (t instanceof WebDriverException) {
			logger.error("Sorry ,now we met the WebDriver Exception ,maybe you had not opened the browser caused this ");
				// String errorType="WebDriverException";
				// captureErrorScreenshot(result,errorType);
		}
		// if met the assert error
		if (t instanceof AssertionError) {
			logger.error("Sorry ,now we met the Assertion Error ,the Assert statement met error expecting result with the actual result ");
			String errorType = "AssertionError";
			captureErrorScreenshot(result, errorType);
		}
			// if met the element cannot find in the page
		if (t instanceof NoSuchElementException) {
			logger.error("Sorry ,now we met the NoSuchElement Exception ,that means we cannot find the element in the page ,make sure you can identify the object correctly ");
			String errorType = "NoSuchElementException";
			captureErrorScreenshot(result, errorType);
		}
	

	}

	@AfterSuite
	public void tearDown() {
		
	  //  driver.quit();
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

		File scrFile = ((TakesScreenshot) new Augmenter().augment( driver ))
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
