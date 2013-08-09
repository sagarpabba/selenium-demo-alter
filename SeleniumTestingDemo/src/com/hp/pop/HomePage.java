package com.hp.pop;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class HomePage {
	

   private WebDriver driver;
   private Logger logger=Logger.getLogger(HomePage.class);
   
   public HomePage(WebDriver driver)
   {
	   this .driver=driver;
   }
   
   
   public void searchDevice(String guid)
   {
	   
   }

}
