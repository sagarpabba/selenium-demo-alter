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
import org.openqa.selenium.WebDriver;

/**
 * ClassName:RecoveryScenario 

 * Date:     Aug 22, 2013 9:23:06 AM 
 * @author   huchan

 * @since    JDK 1.6
 * @see 	 
 * @version $Revision: 1.0 $
 */
public class RecoveryScenario {
	
//	private WebDriver driver;

	private static Logger logger=Logger.getLogger(RecoveryScenario.class);
	
	/**
	
	 * @param driver WebDriver
	 * @return boolean
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
	/**
	 * Method ieContinueToRun.
	 * @param driver WebDriver
	 * @return boolean
	 */
	public static boolean ieContinueToRun(WebDriver driver){
		 boolean userecovery=false;
	
		 return userecovery;
	}
	
	/**
	 * Method firefoxAddException.
	 * @return boolean
	 */
	public static boolean firefoxAddException(){
		boolean userecovery=false;
		
		return userecovery;
	}
	
}

