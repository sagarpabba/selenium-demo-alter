package com.hp.baserunner;

import static java.io.File.separator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
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


import com.hp.dataproviders.ExcelDataProivderLoginSheet;
import com.hp.utility.HostUtil;
import com.thoughtworks.selenium.SeleneseTestBase;

public class NiceBaseDriver extends SeleneseTestBase{
	
	public WebDriver driver=null;
	public String proxyserver, browser, hubUrl;
	
	public static final Logger logger=Logger.getLogger(NiceBaseDriver.class);
	
	protected StringBuffer verificationErrors = new StringBuffer();
	
	@BeforeSuite
	public void setupDriver()
	{
		   String excelpath=System.getProperty("user.dir")+"\\resources\\TestData.xls";
		   logger.debug("Now we find the data driver file path ,the excel path is "+excelpath);
		   String hostname=HostUtil.getFQDN();
		   logger.debug("Now the running host's FQDN is :"+hostname);
		   Map<String,String> mapdata=ExcelDataProivderLoginSheet.getSpecifySheet(excelpath,hostname);
		   browser=mapdata.get("Browser_Type").trim().toLowerCase();
		   proxyserver=mapdata.get("proxy_url").trim();
		   logger.debug("Now we using  browser type is :"+browser);
		   logger.debug("Now we running the remote run host hub is :"+hubUrl); 
		   logger.debug("Now we used browser's  proxy server is :"+proxyserver);
				
		   DesiredCapabilities capability=new DesiredCapabilities();
		  //common settings
		   capability.setCapability("cssSelectorsEnabled", true);
		   capability.setCapability("takesScreenshot", true);
		   capability.setCapability("javascriptEnabled", true);
		   capability.setCapability("ACCEPT_SSL_CERTS", true);
		   capability.setBrowserName(browser);
		  //proxy settings
		   if(!proxyserver.equals(""))
			{
					logger.debug("the current proxy is not null ,we will set the proxy server for this host,proxy server is :"+proxyserver);
					capability.setCapability(CapabilityType.PROXY, new Proxy().setHttpProxy(proxyserver));
					capability.setCapability(CapabilityType.PROXY, new Proxy().setNoProxy("localhost"));
					logger.debug("the proxy had been set correctly now ");
			}
				
			//use different browser
			if (hubUrl == null || hubUrl.trim().isEmpty())
			{
				logger.debug("the blow testing is for the local server testing");
				// if no hubUrl specified, run the tests on localhost
				if (browser == null || browser.trim().isEmpty()) {
					// if no browser specified, use IE
					//capability =DesiredCapabilities.internetExplorer();
					logger.debug("Now the browser we used is IE ");
					capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
					capability.setCapability("ignoreProtectedModeSettings",true);
					capability.setCapability("enablePersistentHover", false);  //prevent frozen
					driver = new InternetExplorerDriver(capability);
					logger.debug("Now Start the IE driver now ");
				} 
				else {
					if (browser.trim().equalsIgnoreCase("firefox")) {
						FirefoxProfile p = new FirefoxProfile();
						p.setPreference("webdriver.log.file", "log/firefox_startup.log");
						driver = new FirefoxDriver(capability);
						logger.debug("Now Start the firefox driver now ");
					} else if (browser.trim().equalsIgnoreCase("chrome")) {
						driver = new ChromeDriver(capability);
						logger.debug("Now start the chrome driver now ");
					} else {
						driver = new InternetExplorerDriver(capability);
						logger.debug("Now start the IE driver now ");
					}
				}
			} 
				
			else {
				logger.debug("Now we will run the remote host for the testing ");
				//	DesiredCapabilities capability=null;				
				if (browser.toLowerCase().trim().equals("ie"))
				{
					//frozen windows 
					capability =DesiredCapabilities.internetExplorer();
					logger.debug(" the browser we used is IE ");
					capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
					capability.setCapability("ignoreProtectedModeSettings",true);
					capability.setCapability("enablePersistentHover", false);  //prevent frozen		    
				}
				else if(browser.toLowerCase().trim().equals("firefox"))
				{
					capability =DesiredCapabilities.firefox();
					logger.debug("the browser we used is firefox");
					//if need the proxy				
				}
				else
				{
					capability =DesiredCapabilities.chrome();
					logger.debug("the browser we used is none");			
				}
				
				//driver = new RemoteWebDriver(new URL(hubUrl), capability);
				    driver = new RemoteWebDriver(capability);
					Capabilities actualCapabilities = ((RemoteWebDriver) driver).getCapabilities();
					logger.debug("the browser used proxy is :"+actualCapabilities.toString());
					
				}
				
				//the driver need to wait time 
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@AfterMethod
	public void errorReport(ITestResult result, ITestContext context) throws Exception
	{
		    Throwable t = result.getThrowable();
			logger.error("the throwable object is :"+t);
			//if the testNG met error or exception
			// if me the webdriver error
			if ( t instanceof WebDriverException){
				logger.error("Sorry ,now we met the WebDriver Exception ,maybe you had not opened the browser caused this ");
				String errorType="WebDriverException";
				//captureErrorScreenshot(result,errorType);
				
			}
			//if met the assert error 
		    if ( t instanceof AssertionError){
		    	logger.error("Sorry ,now we met the Assertion Error ,the Assert statement met error expecting result with the actual result ");
				String errorType="AssertionError";
				captureErrorScreenshot(result,errorType);
		    }
		    //if met the element cannot find in the page 
		    if ( t instanceof NoSuchElementException) {
		    	logger.error("Sorry ,now we met the NoSuchElement Exception ,that means we cannot find the element in the page ,make sure you can identify the object correctly ");
				String errorType="NoSuchElementException";
				captureErrorScreenshot(result,errorType);
		    }
		
	}
	
	@AfterSuite
	public void tearDown()
	{
		driver.quit();
		logger.debug("Now we quite the Browser instance");
	}
	
	//capture the error screenshot if ocurred the error 
	
	public void captureErrorScreenshot(ITestResult result,String errortype) throws IOException
	{

        String filename = result.getTestClass().getName() + "."
						+ result.getMethod().getMethodName() + "."
						+errortype+"."
						+ new SimpleDateFormat("yyyy-MM-dd-HHmmss").format(Calendar.getInstance().getTime()) + ".png";
		logger.debug("we met the error,we will generate a screenshot file for this error, file name is "+filename);
			
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		logger.debug("the source screenshot file is :"+scrFile.getCanonicalPath());
		String path=new File(".").getAbsolutePath();
		String screenshotpath=path.substring(0, path.length()-1);
		logger.debug("the saved screenshot path is :"+screenshotpath);
		// create a new file 
		//try {
			FileUtils.copyFile(scrFile, new File(screenshotpath+"log"+separator + filename));
		//} catch (IOException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//	logger.error("Sorry,we cannot save the error screenshot file for this file,Catch the IOException :"+screenshotpath);
		//}
		logger.debug("the screenshot file saved in this file path:"+screenshotpath+"log"+separator + filename);
		Reporter.setCurrentTestResult(result);
	}

	
	
	//override method
     
	 private static String throwableToString(Throwable t) {
		    StringWriter sw = new StringWriter();
		    PrintWriter pw = new PrintWriter(sw);
		    t.printStackTrace(pw);
		    return sw.toString();
		  }

		  public static String join(String[] sa, char c) {
		    StringBuffer sb = new StringBuffer();
		    for (int j = 0; j < sa.length; j++) {
		      sb.append(sa[j]);
		      if (j < sa.length - 1) {
		        sb.append(c);
		      }
		    }
		    return sb.toString();
		  }

	  private static String stringArrayToString(String[] sa) {
			    StringBuffer sb = new StringBuffer("{");
			    for (int j = 0; j < sa.length; j++) {
			      sb.append(" ").append("\"").append(sa[j]).append("\"");
			    }
			    sb.append(" }");
			    return sb.toString();
			  }

	  /** Like assertEquals, but fails at the end of the test (during tearDown) */
	  public void verifyEquals(Object expected, Object actual) {
	    try {
	      assertEquals(expected, actual);
	    } catch (Error e) {
	      verificationErrors.append(throwableToString(e));
	    }
	  }

	  /** Like assertEquals, but fails at the end of the test (during tearDown) */
	  public void verifyEquals(boolean expected, boolean actual) {
	    try {
	      assertEquals(Boolean.valueOf(expected), Boolean.valueOf(actual));
	    } catch (Error e) {
	      verificationErrors.append(throwableToString(e));
	    }
	  }

	  /** Like JUnit's Assert.assertEquals, but knows how to compare string arrays */
	  public static void assertEquals(Object expected, Object actual) {
	    if (expected == null) {
	      assertTrue("Assert two values whether equals,First Expected value is: \"" + expected + "\" but the actual value saw is: \"" + actual + "\" instead", actual == null);
	    } else if (expected instanceof String && actual instanceof String) {
	      assertEquals((String) expected, (String) actual);
	    } else if (expected instanceof String && actual instanceof String[]) {
	      assertEquals((String) expected, (String[]) actual);
	    } else if (expected instanceof String && actual instanceof Number) {
	      assertEquals((String) expected, actual.toString());
	    } else if (expected instanceof Number && actual instanceof String) {
	      assertEquals(expected.toString(), (String) actual);
	    } else if (expected instanceof String[] && actual instanceof String[]) {
	      assertEquals((String[]) expected, (String[]) actual);
	    } else {
	      assertTrue("Assert two values whether equals,First Expected value is: \"" + expected + "\" but the actual value saw is: \"" + actual + "\" instead",
	          expected.equals(actual));
	    }
	  }

	  
	  
	  static public void assertTrue(String message, boolean condition) {
		    if (!condition) fail(message);
		  }
	  /** Like JUnit's Assert.assertEquals, but handles "regexp:" strings like HTML Selenese */
	  public static void assertEquals(String expected, String actual) {
	    assertTrue("Assert two values whether equals,First Expected value is: \"" + expected + "\" but the actual value saw is: \"" + actual + "\" instead",
	        seleniumEquals(expected, actual));
	  }

	  static public void assertTrue(boolean condition) {
	    assertTrue(null, condition);
	  }

	  static public void assertFalse(String message, boolean condition) {
	    assertTrue(message, !condition);
	  }

	  static public void assertFalse(boolean condition) {
	    assertTrue(null, !condition);
	  }

	  /** Asserts that two booleans are not the same */
	  public static void assertNotEquals(boolean expected, boolean actual) {
	    assertNotEquals(Boolean.valueOf(expected), Boolean.valueOf(actual));
	  }
	  /** Like assertNotEquals, but fails at the end of the test (during tearDown) */
	  public void verifyNotEquals(Object expected, Object actual) {
	    try {
	      assertNotEquals(expected, actual);
	    } catch (AssertionError e) {
	      verificationErrors.append(throwableToString(e));
	    }
	  }

	  /** Like assertNotEquals, but fails at the end of the test (during tearDown) */
	  public void verifyNotEquals(boolean expected, boolean actual) {
	    try {
	      assertNotEquals(Boolean.valueOf(expected), Boolean.valueOf(actual));
	    } catch (AssertionError e) {
	      verificationErrors.append(throwableToString(e));
	    }
	  }

	  /** Asserts that two objects are not the same (compares using .equals()) */
	  public static void assertNotEquals(Object expected, Object actual) {
	    if (expected == null) {
	      assertFalse("Sorry,did not expect null to be null", actual == null);
	    } else if (expected.equals(actual)) {
	      fail("two values are not equals,did not expect values: (" + actual + ") to be equal value to (" + expected + ")");
	    }
	  }
	  
	  
	  /**
	   * Compares two objects, but handles "regexp:" strings like HTML Selenese
	   * 
	   * @see #seleniumEquals(String, String)
	   * @return true if actual matches the expectedPattern, or false otherwise
	   */
	  public static boolean seleniumEquals(Object expected, Object actual) {
	    if (expected == null) {
	      return actual == null;
	    }
	    if (expected instanceof String && actual instanceof String) {
	      return seleniumEquals((String) expected, (String) actual);
	    }
	    return expected.equals(actual);
	  }

	  /** Asserts that two string arrays have identical string contents */
	  public static void assertEquals(String[] expected, String[] actual) {
	    String comparisonDumpIfNotEqual = verifyEqualsAndReturnComparisonDumpIfNot(expected, actual);
	    if (comparisonDumpIfNotEqual != null) {
	      throw new AssertionError(comparisonDumpIfNotEqual);
	    }
	  }

	  /**
	   * Asserts that two string arrays have identical string contents (fails at the end of the test,
	   * during tearDown)
	   */
	  public void verifyEquals(String[] expected, String[] actual) {
	    String comparisonDumpIfNotEqual = verifyEqualsAndReturnComparisonDumpIfNot(expected, actual);
	    if (comparisonDumpIfNotEqual != null) {
	      verificationErrors.append(comparisonDumpIfNotEqual);
	    }
	  }

	  private static String verifyEqualsAndReturnComparisonDumpIfNot(String[] expected, String[] actual) {
	    boolean misMatch = false;
	    if (expected.length != actual.length) {
	      misMatch = true;
	    }
	    for (int j = 0; j < expected.length; j++) {
	      if (!seleniumEquals(expected[j], actual[j])) {
	        misMatch = true;
	        break;
	      }
	    }
	    if (misMatch) {
	      return "Verify two values whether equals ,the Expected result is: " + stringArrayToString(expected) + " but the actual result saw is: "
	          + stringArrayToString(actual);
	    }
	    return null;
	  }

	
}
