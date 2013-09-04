package com.hp.utility;

import static java.io.File.separator;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Proxy;
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
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.hp.utility.HostUtils;
import com.thoughtworks.selenium.SeleneseTestBase;

/**
 * @author huchan
 *
 */
public class NiceBaseDriver extends SeleneseTestBase {

	protected static WebDriver driver;
	protected static String proxyserver, browser, hubUrl;

	protected static final Logger logger = Logger.getLogger(NiceBaseDriver.class);

	protected StringBuffer verificationErrors = new StringBuffer();

	@BeforeSuite
	public void setupDriver() throws IOException {
		String hostname = HostUtils.getFQDN();
		Resulter.log("COMMENT_HOST_NAME", hostname);
		logger.debug("Now the running host's FQDN is :" + hostname);
		
		Map<String, String> mapdata =SeleniumCore.importDataTable("login_page");
		browser = mapdata.get("Browser_Type").trim().toLowerCase();
		proxyserver = mapdata.get("proxy_url").trim();
		logger.debug("Now we using  browser type is :" + browser);
		// logger.debug("Now we running the remote run host hub is :"+hubUrl);
		logger.debug("Now we used browser's  proxy server is :" + proxyserver);

		DesiredCapabilities capability = new DesiredCapabilities();
		// common settings
		capability.setCapability("cssSelectorsEnabled", true);
		capability.setCapability("takesScreenshot", true);
		capability.setCapability("javascriptEnabled", true);
		//capability.setCapability("ACCEPT_SSL_CERTS", true);
		capability.setCapability("ignoreZoomSetting",true);
		capability.setCapability("ignoreProtectedModeSettings", true);
		capability.setCapability("enablePersistentHover", false); // prevent
		capability.setCapability("EnableNativeEvents", false);		
		capability.setBrowserName(browser);
		// proxy settings
		if (!proxyserver.equals("")) {
			logger.debug("the current proxy is not null ,we will set the proxy server for this host,proxy server is :"
					+ proxyserver);
			org.openqa.selenium.Proxy httpproxy = new org.openqa.selenium.Proxy();  
			httpproxy.setHttpProxy(proxyserver)  
			     .setFtpProxy(proxyserver)  
			     .setSslProxy(proxyserver);  
			capability.setCapability(CapabilityType.PROXY,
					httpproxy);
			capability.setCapability(CapabilityType.PROXY,
					new Proxy().setNoProxy("localhost"));
			logger.debug("the proxy had been set correctly now ");
		}

		// use different browser
		if (hubUrl == null || hubUrl.trim().isEmpty()) {
			logger.debug("the blow testing is for the local server testing........");
			// if no hubUrl specified, run the tests on localhost
			if (browser == null || browser.trim().isEmpty()) {
				// if no browser specified, use IE
				// capability =DesiredCapabilities.internetExplorer();
				logger.debug("Now the browser we used is IE ");
				capability
						.setCapability(
								InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
								true);
																					// frozen
				driver = new InternetExplorerDriver(capability);
				logger.debug("Now Start the IE driver now ");
			} else {
				if (browser.trim().equalsIgnoreCase("firefox")) {
					logger.debug("the current browser is firefox .....");
					//support firefox 23 with clickable the object
					capability.setCapability(FirefoxDriver.PROFILE, FirefoxProfileFile.setFirefoxProfile());
					
					driver = new FirefoxDriver(capability);
					logger.debug("Now had started the firefox driver now ");
				} else if (browser.trim().equalsIgnoreCase("chrome")) {
					logger.debug("the current running browser is chrome");
					//set the chrome system path
					String chromedriver=SeleniumCore.getProjectWorkspace()+"resources"+File.separator+"chromedriver.exe";
					System.setProperty("webdriver.chrome.driver",chromedriver);
		             
					capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
					driver = new ChromeDriver(capability);
					logger.debug("Now had started the chrome driver now ");
				} else {
					logger.debug("The current running browser is IE");
					String iedriver=SeleniumCore.getProjectWorkspace()+"resources"+File.separator+"IEDriverServer.exe";
					System.setProperty("webdriver.ie.driver",iedriver);
					
					capability.setCapability(InternetExplorerDriver.NATIVE_EVENTS,false);
					capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
					driver = new InternetExplorerDriver(capability);
					logger.debug("Now had started the IE driver now ");
				}
			}
		}

		else {
			logger.debug("Now we will run the remote host for the testing ......");
			// DesiredCapabilities capability=null;
			if (browser.toLowerCase().trim().equals("ie")) {
				// frozen windows
				capability = DesiredCapabilities.internetExplorer();
				logger.debug(" the browser we used is IE ");
				capability
						.setCapability(
								InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
								true);
				capability.setCapability("ignoreProtectedModeSettings", true);
				capability.setCapability("enablePersistentHover", false); // prevent
																			// frozen
			} else if (browser.toLowerCase().trim().equals("firefox")) {
				capability = DesiredCapabilities.firefox();
				logger.debug("the browser we used is firefox");
				// if need the proxy
			} else {
				capability = DesiredCapabilities.chrome();
				logger.debug("the browser we used is none");
			}
			// driver = new RemoteWebDriver(new URL(hubUrl), capability);
			driver = new RemoteWebDriver(capability);
			Capabilities actualCapabilities = ((RemoteWebDriver) driver)
					.getCapabilities();
			logger.debug("the browser used proxy is :"
					+ actualCapabilities.toString());

		}

		// the driver need to wait time
		driver.manage().window().maximize();
		try{
			//page load time
			driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
			//the web element to find time we need to wait 
		    driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		    // the js executor timeout
		    driver.manage().timeouts().setScriptTimeout(40, TimeUnit.SECONDS);
		   
		}catch(TimeoutException e){
			logger.info("The page or the webelment had been waited for 120 second,it cannot showed ,so the test failed");
			Assert.fail("The page or the webelment had been waited for 120 second,it cannot showed ,so the test failed");
		}
	}
	@AfterMethod
	public void errorReport(ITestResult result, ITestContext context)throws Exception {
		Throwable t = result.getThrowable();
		// logger.debug("Involved the After Method from Parent class,the current throwable object is :"+t);
		// if the testNG met error or exception
		// if me the webdriver error
		boolean findfirefoxerror=false;
		boolean findierror=false;
		if(t instanceof Exception){
			findfirefoxerror=RecoveryScenario.firefoxAddException(driver);
			findierror=RecoveryScenario.ieContinueToWebsite(driver);		
		}
		if(!findfirefoxerror||!findierror){
			//then got the exception we need 
			logger.info("we had found a real error occurred in the testing ... so we will catch a screenshot for this error.....");
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

	}

	@AfterSuite
	public void tearDown() {
	    driver.quit();
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
