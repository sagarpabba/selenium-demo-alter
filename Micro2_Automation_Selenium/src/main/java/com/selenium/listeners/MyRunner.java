package com.selenium.listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;


public class MyRunner {
	
	public static void main(String[] args) {
		
		Thread.setDefaultUncaughtExceptionHandler(new RecoveryScenario());
		XmlSuite suite=new XmlSuite();
		suite.setSuiteFiles(Arrays.asList("./test_suite/testng_template.xml"));
		List<XmlSuite> suitefile=new ArrayList<XmlSuite>();
		suitefile.add(suite);
		TestListenerAdapter tla = new TestListenerAdapter();
		TestNG testng = new TestNG();
		//testng.s
		//testng.setTestClasses(new Class[] { GoogleSearch.class });
		testng.setXmlSuites(suitefile);
		testng.addListener(tla);
		testng.run();

		
	}

}
