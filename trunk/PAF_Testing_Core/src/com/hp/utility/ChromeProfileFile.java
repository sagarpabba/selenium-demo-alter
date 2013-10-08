/**
 * Project Name:PAF_RegressionTesting_LocalServer
 * File Name:ChromeProfileFile.java
 * Package Name:com.hp.utility
 * Date:Sep 24, 20138:51:26 AM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.hp.utility;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * ClassName:ChromeProfileFile 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 TODO ADD REASON. 
 * Date:     Sep 24, 2013 8:51:26 AM 
 * @author   huchan
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class ChromeProfileFile {
	
	
	private static Logger logger=Logger.getLogger(ChromeProfileFile.class);
	
	public static ChromeOptions setChromeProfile(){
		
		 String chromedriver=SeleniumCore.getProjectWorkspace()+"resources"+File.separator+"chromedriver.exe";
		 System.setProperty("webdriver.chrome.driver",chromedriver);
		 logger.debug("the chrome driver path is:"+chromedriver);		 
		 Map<String, Object> prefs = new HashMap<String, Object>();
		 
		 String downloaddir=SeleniumCore.getProjectWorkspace()+"PAFRunReports";
		 ChromeOptions options=new ChromeOptions();
		
		 prefs.put("profile.default_content_settings.popups", 0);
		 
		 prefs.put("download.default_directory",downloaddir);
		 prefs.put("download.directory_upgrade", true);
		 prefs.put("download.extensions_to_open", "");
		 
		 options.setExperimentalOptions("prefs", prefs);
		 options.addArguments("start-maximized");
		 return options;
		
	}

}

