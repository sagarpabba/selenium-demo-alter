package com.hp.utility;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.hp.dataprovider.ExcelDataProivderDeviceSheet;
import com.hp.dataprovider.ExcelDataProivderHomeSheet;
import com.hp.dataprovider.ExcelDataProivderLoginSheet;

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

public class SeleniumCore {

	private static Logger logger = Logger.getLogger(SeleniumCore.class);

	/**
	 *  @author huchan
	 * Open a new URL from the browser
	 * @param driver --the webdriver instance
	 * @param url --  the url you want to open
	 *
	 */
	public static void OpenURL(WebDriver driver, String url) {
		logger.info("Open A New URL:" + url + " in the browser......");
		driver.get(url);
	}

	/**
	 * click an element in the page 
	 * @param e --the WebElment we need to click
	 * @author huchan
	 */
	public static void clickElement(WebElement e) {
		logger.info("Click elements in page-clicked this element:"
				+ e.getTagName() + ",the text is:" + e.getText());
		e.click();
	}

	/**
	 * clear the text in the elment and then type the new string in this element
	 * @param e -- the webelment you need to type 
	 * @param text -- the text you want to input
	 * @author huchan
	 */
	public static void clearAndTypeString(WebElement e, String text) {
		logger.info("Type string into the element is:" + e.getTagName()
				+ ", the inputted text:" + text);
		e.clear();
		e.sendKeys(text);

	}

	/**
	 * send key to an element
	 * @param e --the webelement you want to send the key
	 * @param keys -- the key need to send 
	 * @author huchan
	 */
	public static void sendKeys(WebElement e, String keys) {
		logger.info("Send keys in this element:" + e.getTagName()
				+ ",the key we send is:" + keys);
		e.clear();
		e.sendKeys(keys);
	}

	/**
	 * select an option from the drop list, depends on the visible text
	 * @param e --the web element object
	 * @param text -- the visible text from the dropdown list
	 * @author huchan
	 */
	public static void selectElementViaText(WebElement e, String text) {
		logger.info("Select option text from the list ,list element is:"
				+ e.getTagName() + ",the option text is:" + text);
		Select select = new Select(e);
		select.selectByVisibleText(text);
	}

	/**
	 * select an option from the drop list, depends on the tag's attribute value
	 * @param e  --the web element object
	 * @param value -- the value attribute for this element
	 * @author huchan
	 */
	public static void SelectElementViaValue(WebElement e, String value) {
		logger.info("Select option value from the list ,list element is:"
				+ e.getTagName() + ",the option value is:" + value);
		Select select = new Select(e);
		select.selectByValue(value);
	}

	/**
	 * select an option from the drop list, depends on the index ,the index begin with 0
	 * @param e  --the web element object
	 * @param index -- the index of this webelement ,begin with 0
	 * @author huchan
	 */
	public static void SelectElementViaIndex(WebElement e, int index) {
		logger.info("Select option index from the list ,list element is:"
				+ e.getTagName() + ",the option index is:" + index);
		Select select = new Select(e);
		select.selectByIndex(index);
	}

	/**
	 * sleep the current step for a few seconds 
	 * @param seconds -- the seconds we need to sleep 
	 * @throws InterruptedException
	 * @author huchan
	 */
	public static void sleepSeconds(int seconds) throws Exception
			 {
		logger.info("Begin to sleep current step for " + seconds
				+ " seconds........");
		//You need to be in a synchronized block in order for Object.wait() to work.

		//Also, I recommend looking at the concurrency packages instead of the old school threading packages. They are safer and way easier to work with.
		//synchronized (driver)
	//	{
		//    driver.wait(seconds * 1000);
	//	}
		Thread.sleep(seconds*1000);
	
	}

	/**
	 * sleep current step for a few seconds
	 * @param driver -- the webdriver instance
	 * @param seconds  --the seconds we need to sleep
	 * @throws InterruptedException
	 * @author huchan
	 */
	public static void sleep(WebDriver driver, int seconds)
			throws InterruptedException {
		driver.wait(seconds * 1000);
	}


	/**
	 * wait for an object to be dislayed in the page
	 * @param e -- the web element object
	 * @return  true: the object displayed ,
	 *          false:the object not displayed in the page ;
	 * @author huchan
	 * @throws Exception 
	 */
	public static boolean waitForObjectDisplay(WebElement e)
			throws Exception {
		int waitcount = 0;
		boolean isDisplayed = false;
		while (e.isDisplayed()) {
			waitcount = waitcount + 1;
			isDisplayed = e.isDisplayed();
			logger.info("Waitting for the object displayed status-the load object displayed status is:"
					+ isDisplayed);
			sleepSeconds(3);
			if (waitcount == 5) {
				logger.error("Waitting for the object displayed status-the object cannot show in the page:"
						+ e.getTagName() + ",exit the identify the object ");
				break;
			}

		}
		return isDisplayed;

	}
	
	/**
	 * wait for the object be visible in the page 
	 * @param driver
	 * @param e
	 * @return
	 * @author huchan
	 */
	public static WebElement waitForObjectVisible(WebDriver driver,WebElement e){
	//	WebDriverWait wait=new WebDriverWait(driver, 3);
		//wait.until(ExpectedConditions.);
		return e;
	}

	/**
	 * import the test data into the test flow for testing
	 * @param sheetname -- the sheet name in this excel file
	 * @return the map data for this row 
	 * @author huchan
	 */
	public static Map<String, String> importDataTable(String sheetname) {
		String excelpath = System.getProperty("user.dir") + File.separator
				+ "resources" + File.separator + "TestData.xls";

		String hostname = HostUtils.getFQDN();
		@SuppressWarnings("unchecked")
		Map<String, String> mapdata = new LinkedMap();
		if (sheetname.toLowerCase() == "login_page") {
			mapdata = ExcelDataProivderLoginSheet.getSpecifySheet(excelpath,
					hostname);
		} else if (sheetname.toLowerCase() == "home_page") {
			mapdata = ExcelDataProivderHomeSheet.getSpecifySheet(excelpath,
					hostname);
		} else if (sheetname.toLowerCase() == "device_detail") {
			mapdata = ExcelDataProivderDeviceSheet.getSpecifySheet(excelpath,
					hostname);
		} else if (sheetname.toLowerCase() == "email_settings") {
			// mapdata=ExcelDataProivderEmailSheet.getSpecifySheet(excelpath,
			// hostname);
		} else {
			logger.error("Import datatable into project-Sorry we cannot find the sheet in the test data ,stop the testing now ");
		}

		logger.info("Imported data,the imported Map data is:" + mapdata);
		return mapdata;
	}


	/**
	 * execute the script to set the datepicker value
	 * @param driver --the webdriver instance
	 * @param elementid -- the web element's id
	 * @param date   --the date we need to set ,it's string
	 * @author huchan
	 */
	public static void setDateTimePicker(WebDriver driver, String elementid,
			String date) {
		logger.info("Set DatePicker Date or Time --Execute the java script to modify the weblement's attribute:value,the element id is:"
				+ elementid);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.document.getElementById('" + elementid
				+ "').setAttribute('value', '" + date + "');");

	}

	/**
	 * select the checkbox ,if it selectd ,we will not select it again
	 * @param e -- the web element object
	 * @author huchan
	 */
	public static void selectCheckbox(WebElement e) {
		if (!(e.isSelected())) {
			logger.info("Check the checkbox,the webelment :" + e.getTagName()
					+ e.getText() + ",had been selected now");
			e.click();
		} else {
			logger.info("Check the checkbox,the webelment :" + e.getTagName()
					+ e.getText() + ",had been selected default");
		}
	}

	/**
	 * whether this object displayed in the page
	 * @param e  -- the web element object
	 * @return true:object displayed in the page ,false:the object not displayed in the page
	 * @author huchan
	 */
	public static boolean isDisplayed(WebElement e) {
		boolean isdisplay = false;
		if (e.isDisplayed()) {
			logger.info("Verified Object displayed in the page,the object is:"
					+ e.getTagName() + ",Displayed flag in this object is:"
					+ e.isDisplayed());
			isdisplay = true;
		} else {
			logger.info("Verified Object not displayed in the page,the object is:"
					+ e.getTagName()
					+ ",Displayed flag in this object is:"
					+ e.isDisplayed());
		}
		return isdisplay;
	}

	/**
	 * get the text in the web element
	 * @param e  -- the web element object
	 * @return  String -- the web element's text
	 * @author huchan
	 */
	public static String getElementText(WebElement e) {
		logger.info("Get the element text.The webelement is:" + e.getTagName()
				+ ",the text in the webelement is:" + e.getText().trim());
		return e.getText().trim();
	}

	/**
	 * verify the object is enabled in the page
	 * @param e  -- the web element object
	 * @return  true :the object is enabled ,false:the object is disabled
	 * @author huchan
	 */
	public static boolean isEnabled(WebElement e) {
		logger.info("Verify webelement Enabled in the page-the current webelement is:"
				+ e.getTagName()
				+ ",the text in the webelement is:"
				+ e.getText().trim());
		return e.isEnabled();
	}

	/**
	 * verify the object is selected in the page
	 * @param e --the web element object
	 * @return  true:the object is selected,false:the object is not selected
	 * @author huchan
	 */
	public static boolean isSelected(WebElement e) {
		logger.info("Verify webelement Selected in the page-the current webelement is:"
				+ e.getTagName()
				+ ",the text in the webelement is:"
				+ e.getText().trim());
		return e.isSelected();

	}

	/**
	 * get the webelement's attribute value
	 * @param e  --the web element object
	 * @param attributename  -- the web element's attribute
	 * @return String-- the attribute value for this web element
	 * @author huchan
	 */
	public static String getAttribute(WebElement e, String attributename) {
		logger.info("Get the webelement Attribute-the webelement's attribute:"
				+ e.getTagName() + ",the text in the webelement is:"
				+ e.getText().trim());
		String attributevalue = e.getAttribute(attributename);
		logger.info("Get the webelement Attribute-the webelement's attribute:"
				+ attributename + " value is:" + attributevalue);
		return attributevalue;
	}

	/**
	 * get the web element's tag name 
	 * @param e -- the web element object
	 * @return  String --the web element's tag name
	 * @author huchan
	 */
	public static String getTagName(WebElement e) {
		logger.info("Get the webelement TagName-the webelement's tag name is:"
				+ e.getTagName() + ",the text in the webelement is:"
				+ e.getText().trim());
		String tagname = e.getTagName();
		logger.info("Get the webelement TagName-the webelement's tag name is:"
				+ e.getTagName());
		return tagname;
	}

	/**
	 * get all the web elements behind the specified element
	 * @param e -- the web element object
	 * @param tagname  -- the web element's tag name
	 * @return  List<WebElement> a list of all the sub web element we found
	 * @author huchan
	 */
	public static List<WebElement> findElementListByTagName(WebElement e,
			String tagname) {
		logger.info("Find all subelements by Tag Name:" + e.getTagName()
				+ ",the sub elment's tag name is:" + tagname);
		List<WebElement> elements = e.findElements(By.tagName(tagname));
		return elements;
	}

	/**
	 * find the element by xpath in the page
	 * @param e --the web element object
	 * @param xpath -- the web element's xpath
	 * @return  WebElement -- get the found web element
	 */
	public static WebElement findElementByXpath(WebElement e, String xpath) {
		logger.info("Find subelement by Xpath-we will find an sub element with the xpath:"
				+ xpath);
		WebElement element = e.findElement(By.xpath(xpath));
		return element;
	}


	/**
	 * highLight:(highlight the web element in the page). 
	 * 
	 * TODO(here describle this function flow – optional).
	 * TODO(here describle this function how to use– optional).
	 * TODO(here describle this fuction Precautions– optional).
	 *
	 * @author huchan
	 * @param driver -- the web driver instance 
	 * @param e -- the web element object
	 * @since JDK 1.6
	 */
	public static void highLight(WebDriver driver, WebElement e) {
		logger.info("Highlight the element ,the object is:" + e.getTagName()
				+ ",the text in this object is:" + e.getText());
		Actions action = new Actions(driver);
		action.contextClick(e).perform();
		logger.info("Had right click the object ,then press the escape key");
		e.sendKeys(Keys.ESCAPE);
	}


	/**
	 * executeJS:(execute the java script in this page). 
	 * TODO(here describle this function where used– optional).
	 * TODO(here describle this function flow – optional).
	 * TODO(here describle this function how to use– optional).
	 * TODO(here describle this fuction Precautions– optional).
	 *
	 * @author huchan
	 * @param driver -- the web driver's instance
	 * @param script  --the java script we need to execute
	 * @since JDK 1.6
	 */
	public static void executeJS(WebDriver driver, String script) {
		logger.info("Run the javascript from page ,the java script is:"
				+ script);
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript(script);

	}

	/**
	 * click the ok button in the pop up dialog (alert dialog)
	 * @param driver  -- the web driver's instance
	 * @param seconds -- the seconds we need to wait to click it
	 * @author huchan
	 */
	public static boolean alertClickOK(WebDriver driver,int seconds) {
		boolean isclicked=false;
		WebDriverWait wait=new WebDriverWait(driver, seconds);
		try{
		Alert alert=wait.until(ExpectedConditions.alertIsPresent());
		logger.info("Pop up Alert text is:"+alert.getText());
		alert.accept();
		isclicked=true;
		}catch(NoAlertPresentException e){
			logger.info("the Alert didn't pop up currently:"+e.getMessage());
		}catch(TimeoutException e){
			logger.error("Time out we cannot find this OK button:"+seconds);
		}
		
		return isclicked;
		// driver.w
	}
	
	/**
	 * this fuction is used for clicking the cancel button
	 * @category click the Alert dialog ,click the cancel button
	 * @param driver -- the web driver instance
	 * @param seconds -- the second we need to wait to click the cancel button
	 * @see alertClickOK
	 * @author huchan
	 */

	public static boolean alertClickCancel(WebDriver driver,int seconds) {
		boolean isclicked=false;
		WebDriverWait wait=new WebDriverWait(driver, seconds);
		try{
		Alert alert=wait.until(ExpectedConditions.alertIsPresent());
		logger.info("Pop up Alert text is:"+alert.getText());
		alert.dismiss();
		isclicked=true;
		}catch(NoAlertPresentException e){
			logger.info("the alert didn't pop up currently:"+e.getMessage());
		}
		catch(TimeoutException e){
			logger.error("Time out we cannot find this Cancel button:"+seconds);
		}
		
		return isclicked;
	}
	
	
	/**
	 * getBrowserType:(get the current running browser type and version number). 
	 * TODO(here describle this function where used– optional).
	 * TODO(here describle this function flow – optional).
	 * TODO(here describle this function how to use– optional).
	 * TODO(here describle this fuction Precautions– optional).
	 *
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
	
	/**
	 * getCurrentURL:(get the current page URL address). 
	 * TODO(here describle this function where used– optional).
	 * TODO(here describle this function flow – optional).
	 * TODO(here describle this function how to use– optional).
	 * TODO(here describle this fuction Precautions– optional).
	 *
	 * @author huchan
	 * @param driver  --- the web driver instance
	 * @return String ---the url of current page
	 * @since JDK 1.6
	 */
	public static String getCurrentURL(WebDriver driver){
		String pageurl="";
		JavascriptExecutor je=(JavascriptExecutor) driver;
		final String docstate=(String) je.executeScript("return document.readyState");
		logger.info("Current loading page state is:"+docstate);
		WebDriverWait wait=new WebDriverWait(driver,120);
		ExpectedCondition<Boolean> ec = new ExpectedCondition<Boolean>() {
	          public Boolean apply(WebDriver d) {
	            return (docstate.equals("complete"));
	          }
	        };
	    try{
	       logger.info("We just wait for the current page load correctly now...");
	       wait.until(ec);	       
	       pageurl=driver.getCurrentUrl();
	       logger.info("we found the current url is:"+pageurl);
	    }
	    catch(TimeoutException e){
	    	pageurl="time out:120 seconds";
	    	logger.error("Sorry the page cannot be loaded correctly:"+e.getMessage()+driver.getCurrentUrl());
	    }
		return pageurl;
	}
	
	
	/**
	 * waitPageSync:(Here describle the usage of this function). 
	 * TODO(here describle this function where used– optional).
	 * TODO(here describle this function flow – optional).
	 * TODO(here describle this function how to use– optional).
	 * TODO(here describle this fuction Precautions– optional).
	 *
	 * @author huchan
	 * @param driver
	 * @since JDK 1.6
	 */
	public static void waitPageSync(WebDriver driver){
		//driver.manage().timeouts().
	}
}
