package com.hp.tests;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.testng.annotations.Test;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import com.hp.pop.HomePage;
import com.hp.pop.LoginPage;
import com.hp.utility.NiceBaseDriver;
import com.hp.utility.Resulter;

public class E2EProcessTest extends NiceBaseDriver {
	
  private Logger logger=Logger.getLogger(E2EProcessTest.class);
  
  @Test
  public void processTest() {
	  //init all the used pages
	  LoginPage loginpage=PageFactory.initElements(driver, LoginPage.class);
	  HomePage homepage=PageFactory.initElements(driver, HomePage.class);
      Resulter.log("START_TIME",new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(Calendar.getInstance().getTime()));
	  driver.get("https://saui-itg.corp.hp.com/portal/site/sadbsite/public/sadb/");
	  Resulter.log("STATUS_FWSW_CUSTOMER","passed");
	  
	  homepage=loginpage.logAsValidUser("chang-yuan.hu@hp.com", "gu.chan-1026");
	  //search device
	  //check device's detail info
	  //check device's event or collection 
  }
}
