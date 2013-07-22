package com.hp.baserunner;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;
//import org.testng.log4testng.Logger;

public class RemoteMainDriverTest {
	
  static final Logger log=Logger.getLogger(RemoteMainDriverTest.class);
  
  
  @BeforeSuite
  public void beforeSuite() {
	  log.info("this is the before suite");
  }

  @AfterSuite
  public void afterSuite() {
	  log.info("this is the after suite");  

}
  
  @Test(dataProvider = "dp")
  public void f(Integer n, String s) {
	  log.info("this is the test method");
  }
  @BeforeMethod
  public void beforeMethod() {
	  log.info("this is the before method");
  }

  @AfterMethod
  public void afterMethod() {
	  log.info("this is the after method");
  }


  @DataProvider
  public Object[][] dp() {
    return new Object[][] {
      new Object[] { 1, "a" },
      new Object[] { 2, "b" },
    };
  }
  @BeforeClass
  public void beforeClass() {
	  log.info("this is the before class");
  }

  @AfterClass
  public void afterClass() {
	  log.info("this is the after class");
  }

  @BeforeTest
  public void beforeTest() {
	  log.info("this is the before test");
  }

  @AfterTest
  public void afterTest() {
	  log.info("this is the after test");
  }

}
 
