package com.selenium.utilities;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

public class BasePageTest {
  @BeforeMethod
  public void beforeMethod() {
  }


  @Test
  public void reporterNewData() {
   // throw new RuntimeException("Test not implemented");
    
      BasePage.reporterNewStep("abcd", "dd", "PASSED", "testing");
      BasePage.reporterNewStep("abcd", "dd", "PASSED", "testing");
      BasePage.reporterNewData("TESTING DAT", "CASEEWE3");
      BasePage.reporterNewData("TESTING DATDSDSD", "CASEEWE3AA");
      
  }
}
