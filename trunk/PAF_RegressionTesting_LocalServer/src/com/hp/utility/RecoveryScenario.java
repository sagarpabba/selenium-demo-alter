/**
 * Project Name:PAF_HC
 * File Name:RecoveryScenario.java
 * Package Name:com.hp.utility
 * Date:Aug 22, 20139:23:06 AM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.hp.utility;

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

	//private static Logger logger=Logger.getLogger(RecoveryScenario.class);
	
	/**
	 * @return
	 */
	public static boolean invokeAllRecoveries(WebDriver driver){
	    boolean userecovery=false;
	    boolean useie=ieContinueToRun(driver);
	    boolean usefirefoxexception=firefoxAddException();
	    
	    
	    //at less we use one recovery scenior ,we will return true
	    if(useie||usefirefoxexception){
	        userecovery=true;	
	    }
	    
	    return userecovery;
	}
	//met the ie continue to run error
	public static boolean ieContinueToRun(WebDriver driver){
		 boolean userecovery=false;
		 try{
		//	 driver.navigate().to("javascript:document.getElementById('overridelink').click();");
		//	 logger.debug("Now actually we had use the javascript to click the override link in the page");
		//	 userecovery=true;
		 }
		 catch(Exception e){
			 userecovery=false;
		 }
		 return userecovery;
	}
	
	public static boolean firefoxAddException(){
		boolean userecovery=false;
		
		return userecovery;
	}
	
}

