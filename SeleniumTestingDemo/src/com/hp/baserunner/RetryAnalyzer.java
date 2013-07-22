package com.hp.baserunner;

import org.testng.ITestResult;
import org.testng.util.RetryAnalyzerCount;

public class RetryAnalyzer extends RetryAnalyzerCount {

	@Override
	public boolean retryMethod(ITestResult result) {
		// using parent class default retry of 1
		this.setCount(3);
		//result.setStatus(ITestResult.);
		return true;

	}

}
