package com.selenium.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;

import com.selenium.utilities.RetryFail;

/**
 */
public class RetryListener implements IAnnotationTransformer {

	/* (non-Javadoc)
	 * @see org.testng.IAnnotationTransformer#transform(org.testng.annotations.ITestAnnotation, java.lang.Class, java.lang.reflect.Constructor, java.lang.reflect.Method)
	 */
	@SuppressWarnings("rawtypes")
	public void transform(ITestAnnotation annotation, Class testClass,
			Constructor testConstructor, Method testMethod) {

		IRetryAnalyzer retry = annotation.getRetryAnalyzer();
		if (retry == null) {
			// annotation.setRetryAnalyzer(RetryAnalyzer.class);
			 annotation.setRetryAnalyzer(RetryFail.class);
		}
		
		Thread.setDefaultUncaughtExceptionHandler(new RecoveryScenario());
	}

}
