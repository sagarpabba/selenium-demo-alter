package com.selenium.utilities;



import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.selenium.utilities.recorder.RecordConfig;
import com.selenium.utilities.recorder.ScreenRecorder;


/**
* @ClassName: BaseDriver
* @Description: TODO
* @author alterhu2020@gmail.com
* @date Apr 9, 2014 5:56:07 PM
* 
*/

public class BaseDriver{

	public static WebDriver driver;	
	protected static final Logger logger = Logger.getLogger(BaseDriver.class);
	
	public static String datatable;
	
	public static ScreenRecorder recorder=null;
	
	/** 
	* @Title: setupDriver 
	* @Description: TODO
	* @author alterhu2020@gmail.com
	* @param @param browsertype
	* @param @param context
	* @param @throws Exception    
	* @return void    return type
	* @throws 
	*/ 
	
	@BeforeSuite(description="TestNG-Before Test Suite,Run Only Once in current TestNg Session")
	public void setupDriver(ITestContext context) throws Exception 
	{
        	/*
        	 * started the browser instance ,then we can invoke the selenium API in ur testing	
        	 */
		//RecoveryScenario.registerExceptionHandler();
		
		//capture the screenshot when we run the testing execution	
		RecordConfig recConfig=new RecordConfig();
		recConfig.setFramesRate(24);
		recConfig.setVideoFile(new File(GlobalDefinition.DESKTOP_SCREEN_RECORDER));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		recConfig.setFrameDimension(new Rectangle(dim));
		recConfig.setCursorImage(ImageIO.read(new File(GlobalDefinition.MOUSE_CURSE_ICON)));
		recorder=new ScreenRecorder();
		recorder.startRecording(recConfig);
         	
		String browsername=context.getCurrentXmlTest().getAllParameters().get("browsername");
		String proxy=context.getCurrentXmlTest().getAllParameters().get("proxy");
		logger.info("browser is :"+browsername+",proxy is :"+proxy);
		
		logger.info("browser is :"+browsername+",proxy is :"+proxy);
		DesiredCapabilities capability=new DesiredCapabilities();
		SeleniumCore.browserCommonSettings(capability);
		if(proxy!=null){
		    SeleniumCore.browserProxySettings(capability, proxy);
		}
	    
		if (browsername.trim().equalsIgnoreCase("ie")){
			SeleniumCore.browserIESettings(capability);
			driver=new InternetExplorerDriver(capability);
		}
		
		 if (browsername.trim().equalsIgnoreCase("firefox")) {
			SeleniumCore.browserFirefoxSettings(capability);
			driver=new FirefoxDriver(capability);
		 }
		 
		 if(browsername.trim().equalsIgnoreCase("chrome")) {
			SeleniumCore.browserChromeSettings(capability);
			driver=new ChromeDriver(capability);
		 }
		 //check the browser settings is as desired before
		 Capabilities actualCapabilities = ((RemoteWebDriver) driver).getCapabilities();
		 logger.info("Selenium Server Capabilities actually is :\n"+ actualCapabilities.toString());
	     
		 SeleniumCore.seleniumManager(driver);
		 
		 //Import all the test data from excel file
		
	}

	/** 
	* @Title: catchAnyExceptions 
	* @Description: TODO
	* @author alterhu2020@gmail.com
	* @param @param result
	* @param @param context
	* @param @throws Exception    
	* @return void    return type
	* @throws 
	*/ 
	
	@AfterMethod(description="this method will capture any Exception when the testing is running")
	public void catchAnyExceptions(ITestResult result, ITestContext context)throws Exception {
			final Throwable t = result.getThrowable();
		    if(t instanceof IOException){
		    	
		    }

	}

	/**
	 * @throws IOException  
	* @Title: tearDown 
	* @Description: TODO
	* @author alterhu2020@gmail.com
	* @param     
	* @return void    return type
	* @throws 
	*/ 	
	@AfterSuite(description="TestNG-After Test Suite,Close the browser or do some other things")
	public void tearDown() throws IOException {
		driver.quit();
		//long endTime=System.nanoTime();
		recorder.stopRecording();
		logger.info("Involved AfterSuite method from parent.Now we quite the Browser instance");
	}
}
