package com.selenium.tests;

import org.testng.annotations.Test;

import com.selenium.utilities.EmailerUtils;

public class EmailSenterTest {

  @Test
  public void sendEmail() {
	  EmailerUtils.sendSeleniumEmail();
  }
}
