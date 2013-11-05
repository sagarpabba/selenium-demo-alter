package com.hp.utility;


import java.io.File;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;


import org.apache.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.jacob.com.LibraryLoader;

/**
 * ClassName: SeleniumCore 
 * Function: common function we had wrapped the selenium function .
 * Reason: use the function easily(optional). 
 * date: Aug 18, 2013 2:36:42 PM 
 *
 * @author huchan
 * @version 1.0
 * @since JDK 1.6
 */

/**
 * @author huchan
 *
 */
public class SeleniumCore {

	private static Logger logger = Logger.getLogger(SeleniumCore.class);
	
	
	
	/**
	 * import the test data into the test flow for testing
	 * @param sheetname -- the sheet name in this excel file
	 * @return the map data for this row 
	 * @author huchan
	 */
	public static Map<String, String> importDataTable(String sheetname) {
		String excelpath = getProjectWorkspace()+ "resources" + File.separator + "TestData.xls";
		//String casetype = HostUtils.getFQDN();
		@SuppressWarnings("unchecked")
		Map<String, String> mapdata = new LinkedMap();
		if (sheetname.toLowerCase() == "login_page") {
			mapdata = ExcelUtils.getSpecifySheet(excelpath,"Login_Page","postive");
		} else if (sheetname.toLowerCase() == "home_page") {
			mapdata =ExcelUtils.getSpecifySheet(excelpath,"Home_Page","postive");
		} else if (sheetname.toLowerCase() == "device_detail") {
			mapdata = ExcelUtils.getSpecifySheet(excelpath,"Device_Detail","postive");
		} else if (sheetname.toLowerCase() == "email_settings") {
			mapdata=ExcelUtils.getSpecifySheet(excelpath,"Email_Settings","postive");
		} else {
			logger.error("Import datatable into project-Sorry we cannot find the sheet in the test data ,stop the testing now ");
		}

		logger.info("Imported data,the imported Map data is:" + mapdata);
		return mapdata;
	}
	
	/**
	 * get current eclipse workspace we used 
	 * @return
	 */
	public static String getProjectWorkspace(){
		String path = new File(".").getAbsolutePath();
		String probasepath = path.substring(0, path.length() - 1);
		logger.debug("Current project's workspace path is:"+probasepath);
		return probasepath;
	}
	
//******************************************AutoItX dll API*****************************************************************************	
	/**
	 * getAutoItX:(get the AutoItX instance). 
	 *
	 * @author huchan
	 * @return
	 * @since JDK 1.6
	 */
	public static AutoItXUtils getAutoItX(){
		File file;
		try{
		   file= new File("lib", "jacob-1.18-M1-x86.dll"); //path to the jacob dll
		   logger.info("Using jacob dll x86 bit file to load the jacob now...");
		}catch(UnsatisfiedLinkError error){
			logger.info("the java VM is x86 so we change to load the jacob dll with x64 bit:"+error.getMessage());
			file= new File("lib", "jacob-1.18-M1-x64.dll"); //path to the jacob dll
		}
		logger.info("Get the jacob dll file path is:"+file.getAbsolutePath());
        System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());
        
        return new AutoItXUtils();
	}
	
	/**
	 * Using AutoIt to click the button ,this is not suitable for the RemoteWebDriver
	 * @param title
	 * @param text
	 * @param controlID
	 */
	public static void autoit_clickButton(String title,String text,String controlID){
		AutoItXUtils autoit=getAutoItX();
		autoit.winWait(title, text, 120);
		autoit.winActivate(title, text);
		boolean clicked=autoit.controlClick(title, text, controlID);
		if(clicked){
			logger.info("Now we use the AutoItx API to click the button ");
			logger.info("the clicked button's window title is:"+title+",the window text is:"+text+",the button ID is:"+controlID);
		}
		else
		{
			logger.info("Now we try to use the AutoItX API to click the button ,it's failed ,we cannot cick it .sorry: ");
			logger.info("the failed button's window title is:"+title+",the window text is:"+text+",the button ID is:"+controlID);
		}
		
	}
	
	/**
	 * Using the AutoIt to set the string into a control object
	 * @param title
	 * @param text
	 * @param controlID
	 * @param typetext
	 */
	public static void autoit_typeText(String title,String text,String controlID,String typetext){
		AutoItXUtils autoit=getAutoItX();
		autoit.winWait(title, text, 120);
		autoit.winActivate(title, text);
		boolean seet=autoit.controlSend(title, text, controlID, typetext);
		if(seet){
			logger.info("Now we use the AutoItx API to send string to the control ");
			logger.info("the control window title is:"+title+",the window text is:"+text+",the control ID is:"+controlID);
		}
		else
		{
			logger.info("Now we try to use the AutoItX API to input string ,it's failed ,we cannot input the string .sorry: ");
			logger.info("the failed control's window title is:"+title+",the window text is:"+text+",the control ID is:"+controlID);
		}
	}

//**************************************************AutoItX API**************************************************************************


	//read the email
	//http://www.avajava.com/tutorials/lessons/how-do-i-receive-email-in-java.html?page=1
	//http://metoojava.wordpress.com/2010/03/21/java-code-to-receive-mail-using-javamailapi/
	 public static void receiveEmail(String pop3Host, String storeType, String user, String password) {
		 
	   }
	
		/**
		 * getBrowserType:(get the current running browser type and version number). 
		 * @author huchan
		 * @param driver  ---the web driver instance
		 * @return String --- the browser type and version number
		 * @since JDK 1.6
		 */
		public static String getBrowserType(WebDriver driver){
			Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
			String browserName = caps.getBrowserName();
			String browserVersion = caps.getVersion();
			logger.info("Get the current running browser is:"+browserName+",the browser version is:"+browserVersion);
			return browserName+" "+browserVersion;
		}
	
	
	
		
	
}