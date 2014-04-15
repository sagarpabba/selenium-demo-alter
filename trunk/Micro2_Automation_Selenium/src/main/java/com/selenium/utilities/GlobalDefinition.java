package com.selenium.utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
* @ClassName: GlobalDefinition
* @Description: TODO
* @author alterhu2020@gmail.com
* @date Apr 9, 2014 5:56:22 PM
* 
*/

public class GlobalDefinition {
	
	  //folder path settings
	  public final static String PROJECT_DIR=new File("").getAbsolutePath()+File.separator; //like "C:\workspace\Micro2_Automation_Selenium\"
	
	  public final static String Log4J_LOG_DIR=PROJECT_DIR+"logs";   // IE console log folder
	  public final static String REPORTRESULT_DIR=PROJECT_DIR+"test-result";
	  public final static String DAILY_REPORT_FILE=REPORTRESULT_DIR+File.separator+"TestingExecutionReport_"+ 
                                                   new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime())+".htm";
	  public final static String TESTNG_REPORT_DIR=PROJECT_DIR+"test-output";
	  
	  public final static String MOUSE_CURSE_ICON="src/main/resources/cursor_white.png";
	  public final static String E2E_EXCEL_FILE="src/main/resources/TestData.xls";
	  public final static String REPORT_TEMPLATE_FILE="src/main/resources/report_template.htm";
	  public final static String SELENIUM_DRIVER_PATH=PROJECT_DIR+"selenium_driver";
	  public final static String DESKTOP_SCREEN_RECORDER=PROJECT_DIR+"test-result"
	                             +File.separator+new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(Calendar.getInstance().getTime())+".mp4";
	  //JDBC settings
	  public static String DB_DRIVER_NAME="com.mysql.jdbc.Driver";
	  public static String DB_URL="jdbc:mysql://HUCHAN3.asiapacific.hpqcorp.net:3306/qtpresult";
	  public static String DB_USER="root";
	  public static String DB_PASSWORD="root";
	    
	  //selenium page settings 
	  public static int PAGE_LOADING_TIME=150;
	  public static int WEBELEMENT_LOADING_TIME=60;
	  public static int JS_TIMEOUT=140;
	  
	  //script started time
	  public static String CURRENT_TIME=TimeUtils.getCurrentTime(Calendar.getInstance().getTime());
	  
	  //Chrome settings
	  public static String BROWSER_DOWNLOAD_DIR=PROJECT_DIR+"Downloads";
	  
	  
	  
	  
	  
}
