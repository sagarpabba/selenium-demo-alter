package com.selenium.listeners;

import org.testng.ISuite;
import org.testng.ISuiteListener;

public class SuiteExceptionHandler implements ISuiteListener {

	@Override
	public void onStart(ISuite suite) {
		// TODO Auto-generated method stub
		//Thread.currentThread().setDefaultUncaughtExceptionHandler(new RecoveryScenario());
	}

	@Override
	public void onFinish(ISuite suite) {
		// TODO Auto-generated method stub
		
	}

}
