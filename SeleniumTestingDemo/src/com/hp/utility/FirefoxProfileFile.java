/**
 * Project Name:PAF_HC
 * File Name:FirefoxProfileFile.java
 * Package Name:com.hp.utility
 * Date:Aug 27, 20132:24:13 PM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.hp.utility;

import org.apache.log4j.Logger;
import org.openqa.selenium.firefox.FirefoxProfile;

/**
 * ClassName:FirefoxProfileFile 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 TODO ADD REASON. 
 * Date:     Aug 27, 2013 2:24:13 PM 
 * @author   huchan
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class FirefoxProfileFile {
	
	private static Logger logger=Logger.getLogger(FirefoxProfileFile.class);
	public static FirefoxProfile setFirefoxProfile(){
		FirefoxProfile fp=new FirefoxProfile();
		//fp.addExtension(extensionToInstall);
		
		fp.setAcceptUntrustedCertificates(true);
		fp.setAssumeUntrustedCertificateIssuer(true);
		fp.setEnableNativeEvents(false);
		//fp.setProxyPreferences(proxy);
		// for the download bar to automatically download it
		fp.setPreference("browser.download.folderList", 2);
		fp.setPreference("browser.download.manager.showWhenStarting", false);
		String downloaddir=SeleniumCore.getProjectWorkspace();
		fp.setPreference("browser.download.dir", downloaddir);
		fp.setPreference("browser.helperApps.alwaysAsk.force", false);
		fp.setPreference("security.default_personal_cert", "Select Automatically");
		//fp.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/zip");
		logger.info("Had set a firefox profile for current session....");
		return fp;
	}

}

