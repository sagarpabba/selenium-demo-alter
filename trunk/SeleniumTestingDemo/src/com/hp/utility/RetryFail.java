package com.hp.utility;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * @author sumeetmisri@gmail.com
 * @modify alterhu2020@gmail.com
 * @version 1.0
 * @category
 
	 * 
	 */

public class RetryFail implements IRetryAnalyzer {
	private final int m_maxRetries = 1;
	private final int m_sleepBetweenRetries = 1000;
	private int currentTry;
	private String previousTest = null;
	private String currentTest = null;

	public RetryFail() {
		currentTry = 0;
	}

	@Override
	public boolean retry(final ITestResult result) {
		// If a testcase has succeeded, this function is not called.
		boolean retValue = false;

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
				System.out
						.println("NumberFormatException while parsing maxRetries from suite file."
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
				System.out
						.println("NumberFormatException while parsing sleepBetweenRetries from suite file."
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
			currentTry++;
			result.setStatus(ITestResult.SUCCESS_PERCENTAGE_FAILURE);
			retValue = true;

		} else {
			currentTry = 0;
		}
		previousTest = currentTest;
		// if this method returns true, it will rerun the test once again.

		return retValue;
	}
}
