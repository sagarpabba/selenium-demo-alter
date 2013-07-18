package com.hp.baserunner;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
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

public class LocalMainDriverTest {
	
  private static final Logger log=Logger.getLogger(LocalMainDriverTest.class);
  private WebDriver driver;
  
  @BeforeSuite
  public void beforeSuite(String browserType) {
//	  driver=new 
	  log.info("this is the before suite");
	  if(browserType.equals("firefox"))
	  {
		  driver=new FirefoxDriver();
		  driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
	  }
	  else if (browserType.equals("ie"))
	  {
          File file=new File("c:/temp/IEDriverServer.exe");		  
		  System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
		  DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
		  ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true); 
		  driver=new InternetExplorerDriver(ieCapabilities);
		  driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
	  }
	  else if(browserType.equals("chrome"))
	  {
		  File file=new File("c:/temp/chromedriver.exe");		  
		  System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		 // DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
		 // ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true); 
		  driver=new ChromeDriver();
		  driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS); 
	  }
	  else
	  {
		  log.warn("sorry we not not support this browser:"+browserType);
	  }
  }

  @AfterSuite
  public void afterSuite() {
	  log.info("this is the after suite");  

}
  @DataProvider(name="testdata.xls")
  public Object[][] dp() {
    return new Object[][] {
      new Object[] { 1, "a" },
      new Object[] { 2, "b" },
    };
  }
  @Test(dataProvider = "testda.xls",enabled=true)
  public void RunTests(WebDriver driver) {
	  log.info("this is the test method");
	  this.driver=driver;
	  	  
  }
  @BeforeMethod
  public void beforeMethod() {
	  log.info("this is the before method");
  }

  @AfterMethod
  public void afterMethod() {
	  log.info("this is the after method");
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
 
