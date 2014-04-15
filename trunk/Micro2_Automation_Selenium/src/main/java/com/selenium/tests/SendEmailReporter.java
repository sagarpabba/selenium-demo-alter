package com.selenium.tests;

import org.testng.annotations.Test;

import com.selenium.utilities.EmailerUtils;

public class SendEmailReporter {
	
	@Test
	public void sendDetailReport(){
		
		
		EmailerUtils.sendSeleniumEmail();
	}

}
