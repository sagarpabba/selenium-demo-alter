package com.selenium.run;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import com.selenium.utilities.GlobalDefinition;

public class RunTestNG {
    private static Logger logger=Logger.getLogger(RunTestNG.class);
    static TestNG testng;
    private static String TESTNG_SUITE_FILE=GlobalDefinition.TESTSUITE_DIR+"/testng_template.xml";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        logger.info("Runing the selenium project testing via TestNG Now......");
        testng = new TestNG();
		testng.setPreserveOrder(true);
		testng.setVerbose(2);

		// create a suite programmatically
		XmlSuite suite = new XmlSuite();
		suite.setName("Programmatic XmlSuite in TestNG");

		// create a test case for the suite
		/*XmlTest xmltest = new XmlTest(suite);
		xmltest.setName("Test 1");
		xmltest.setXmlClasses(Arrays.asList(new XmlClass("FastTest")));*/

		// add a suite-file to the suite
		//suite.setSuiteFiles(Arrays.asList(TESTNG_SUITE_FILE));

		// 1. To run with testng.xml file, uncomment this one, comment 2
		// testng.setTestSuites(Arrays.asList("testng.xml"));

		// 2. to run with XmlSuite, uncomment this one, comment 1
	    testng.setSuiteThreadPoolSize(3);
		//testng.setXmlSuites(Arrays.asList(suite));
	    testng.setTestSuites(Arrays.asList(TESTNG_SUITE_FILE));

		testng.run();
      
	}

}
