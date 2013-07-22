package com.hp.baserunner;

import static java.io.File.separator;

import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import com.hp.dataproviders.ExcelDataProivderLoginSheet;
import com.hp.utility.HostUtil;

public class BaseSeleniumDriver {

  public  WebDriver driver=null;
  public String proxyserver, browser, hubUrl;
  private static final Logger log=Logger.getLogger(BaseSeleniumDriver.class);
  
  @BeforeSuite
  @Parameters({ "excelpath","hostname" }) 
  public void beforeSuite(String excelpath,String hostname) {
//		Properties p = new Properties();
//	    FileInputStream  conf = new FileInputStream(configfile);
//	    p.load(conf);
	//    String hostname=HostUtil.getFQDN();
	    Map<String,String> mapdata=ExcelDataProivderLoginSheet.getSpecifySheet(hostname,excelpath);

//	    hubUrl = p.getProperty("hubUrl");
	    hubUrl="http://"+hostname+":4444/wd/hub";
//	    browser = p.getProperty("browser");
	    browser=mapdata.get("Browser_Type").trim();
	 //   testUrl = p.getProperty("testUrl");
	//	log.info("The Page URL is:"+testUrl);
	    proxyserver=mapdata.get("proxy_url").trim();
		log.debug("the browser type is :"+browser);
		log.debug("the remote run host hub is :"+hubUrl); 
		log.debug("the browser's proxy server is :"+proxyserver);
		
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
			//common settings
			capability.setCapability("cssSelectorsEnabled", true);
			capability.setCapability("takesScreenshot", true);
			capability.setCapability("javascriptEnabled", true);
			capability.setCapability("ACCEPT_SSL_CERTS", true);
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
				//if need the proxy
							}
			else
			{
				log.debug("the browser we used is none");
				
			}
			//proxy settings
			if(!proxyserver.equals(""))
			{
				log.debug(" the current proxy is not null ,we will set the proxy server for this host,proxy server is :"+proxyserver);
				//capability.setCapability(CapabilityType.PROXY, new Proxy().setHttpProxy(proxyserver));
				//capability.setCapability(CapabilityType.PROXY, new Proxy().setNoProxy("localhost"));
			}
			try {
				driver = new RemoteWebDriver(new URL(hubUrl), capability);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch(UnreachableBrowserException e){
				log.error("Sorry ,mabye you had not opened the selenium sever firstly,please start the slenium server ....");
			}
			catch(java.lang.NullPointerException e)
			{
				log.error("Sorry ,mabye you had not opened the selenium sever firstly,please start the slenium server ...."+e);
			}
		}
		//the driver need to wait time 
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }
  @AfterMethod
  public void afterMethod(ITestResult result, ITestContext context) throws Exception {
	  
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
  }

 

  @AfterSuite
  public void afterSuite() {
	  
	  driver.quit();
	  log.debug("quit the driver now ");
  }

}
