package com.hp.utility;

import java.util.logging.Logger;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

/**
 * @author sumeetmisri@gmail.com

 * @version 1.0

 
	 * 
	 */

public class RetryFail implements IRetryAnalyzer {
	
	private static Logger logger=Logger.getAnonymousLogger();
	public static int m_maxRetries = 4;
	private final int m_sleepBetweenRetries = 1000;
	private int currentTry;
	private String previousTest = null;
	private String currentTest = null;
   
	public RetryFail() {
		currentTry = 0;
	}

	/* (non-Javadoc)
	 * @see org.testng.IRetryAnalyzer#retry(org.testng.ITestResult)
	 */
	@Override
	public boolean retry(final ITestResult result) {
		// If a testcase has succeeded, this function is not called.
		boolean retValue = false;
		System.out.println("Verify if need to retry the method:"+currentTry+",result is:"+result.isSuccess());
		// Getting the max retries from suite.
		// String maxRetriesStr =
		// result.getTestContext().getCurrentXmlTest().getParameter("maxRetries");
		String maxRetriesStr = result.getTestContext().getSuite()
				.getParameter("maxRetries");
		int maxRetries = m_maxRetries;
		if (maxRetriesStr != null) {
			try {
				maxRetries = Integer.parseInt(maxRetriesStr);
			} catch (final NumberFormatException e) {
				logger.info("NumberFormatException while parsing maxRetries from suite file."
								+ e);
			}
		}

		// Getting the sleep between retries from suite.you can from the suite
		// parameter
		String sleepBetweenRetriesStr = result.getTestContext().getSuite()
				.getParameter("sleepBetweenRetries");
		int sleepBetweenRetries = m_sleepBetweenRetries;
		if (sleepBetweenRetriesStr != null) {
			try {
				sleepBetweenRetries = Integer.parseInt(sleepBetweenRetriesStr);
			} catch (final NumberFormatException e) {
			    logger.info("NumberFormatException while parsing sleepBetweenRetries from suite file."
								+ e);
			}
		}

		currentTest = result.getTestContext().getCurrentXmlTest().getName();

		if (previousTest == null) {
			previousTest = currentTest;
		}
		if (!(previousTest.equals(currentTest))) {
			currentTry = 0;
		}

		if (currentTry < maxRetries && !result.isSuccess()) {
			try {
				Thread.sleep(sleepBetweenRetries);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
			currentTry=currentTry+1;
			result.setStatus(ITestResult.FAILURE);
			Reporter.setCurrentTestResult(result);
			 String message = Thread.currentThread().getName() + ": Error in " + result.getName() + " Retrying "
			            + (currentTry) + " more times";
			        //System.out.println(message);
			Reporter.log(message);
			retValue = true;

		} else {
			result.setStatus(ITestResult.SUCCESS);
			Reporter.setCurrentTestResult(result);
			currentTry = 0;
		}
		previousTest = currentTest;
		// if this method returns true, it will rerun the test once again.

		return retValue;
	}
}
