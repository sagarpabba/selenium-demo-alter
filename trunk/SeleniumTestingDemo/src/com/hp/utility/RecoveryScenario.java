/**
 * Project Name:PAF_HC
 * File Name:RecoveryScenario.java
 * Package Name:com.hp.utility
 * Date:Aug 22, 20139:23:06 AM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.hp.utility;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

/**
 * ClassName:RecoveryScenario 

 * Date:     Aug 22, 2013 9:23:06 AM 
 * @author   huchan
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class RecoveryScenario {
	
//	private WebDriver driver;

	private static Logger logger=Logger.getLogger(RecoveryScenario.class);
	//public RecoveryScenario(WebDriver driver) throws Exception{
		//this.driver=driver;
		//logger.info("Current capture an exception ,we will try to use the recovery scenario to perform it firstly ,then identify whether it's an unexpected error");
		//firefoxAddException(driver);
	//}
	
	public static boolean firefoxAddException(WebDriver driver) throws Exception{
		//firefox  Untrusted page element
		boolean useRecovery=false;
	
		try{	  
			useRecovery=true;
		}
		catch(NoSuchElementException e){
			logger.info("Now we are not in the firefox exception page,we try to identify where this page and continue to catch the exception error..."+e.getMessage());
		}
		return useRecovery;
	}

	public static boolean ieContinueToWebsite(WebDriver driver){
		logger.info("Using the javascript to bypass the continue to this websitelink error in the page");
		boolean useRecovery=false;
		String elementid=(String) SeleniumCore.executeJS(driver, "return window.document.getElementById('overridelink');");
		logger.info("get the overridelink id is:"+elementid);
		if(elementid!=null){
			try{
			   driver.findElement(By.id("overridelink"));
			   logger.info("we found the continue to run link in the page ,so we will click it and navigate to the normal page");
			   driver.navigate().to("javascript:document.getElementById('overridelink').click()");
			   useRecovery=true;
		     }
		    catch(NoSuchElementException se){
			//SeleniumCore.executeJS(driver, script);
		    	logger.info("we had not find the continue to run link in this page ,so we will not use this recovery in this page");
		    }
		}
		return useRecovery;
	}
}

