package com.hp.baserunner;

import static java.io.File.separator;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.Augmenter;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;

public class DemoDriver {

	private static Logger log=Logger.getLogger(DemoDriver.class);
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
		//	WebDriver augmentedDriver = new Augmenter().augment(driver);
//			File scrFile = ((TakesScreenshot) augmentedDriver)
//					.getScreenshotAs(OutputType.FILE);
//			String path=new File(".").getAbsolutePath();
//			String screenshotpath=path.substring(0, path.length()-1);
//			// create a new file 
//			FileUtils.copyFile(scrFile, new File(screenshotpath+"log"+separator + filename));
		//	log.debug("the screenshot file in this file path:"+screenshotpath+"log"+separator + filename);
			Reporter.setCurrentTestResult(result);
			//Reporter.log("<a href=\"" + filename + "\">Screenshot</a>");
		}
		else
		{
			log.debug("This test method is working fine ,we marked it as passed");
		}
		
		
		
	//	String verificationErrorString = verificationErrors.toString();
		//if (!"".equals(verificationErrorString)) {
			//fail(verificationErrorString);
		//}

		for(int i=0;i<context.getAllTestMethods().length;i++){
			log.debug("this is to remove the failed testing:"+context.getAllTestMethods().length);
			if(context.getAllTestMethods()[i].getCurrentInvocationCount()==4) //current retry test cases
			{
				int failednum=context.getFailedTests().getResults(context.getAllTestMethods()[i]).size();
				log.debug(" the faild is :"+failednum);
				int passednum=context.getPassedTests().getResults(context.getAllTestMethods()[i]).size();
				log.debug("the passed is :"+passednum);
				System.out.println(" test failed is :"+failednum+",test case pass is :"+passednum);
			  if ( failednum== 2 || passednum== 1)
			    {
				      log.debug("remove this method report");
			           context.getFailedTests().removeResult(context.getAllTestMethods()[i]);
			    }
			}
			}
	}
}
