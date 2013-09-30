/**
 * Project Name:PAF_RegressionTesting
 * File Name:PageBase.java
 * Package Name:com.hp.pop
 * Date:Sep 13, 20131:06:16 PM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.hp.pop;


import java.awt.AWTException;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Strings;
import com.hp.utility.FilesUtils;
import com.hp.utility.SeleniumCore;


/**
 * ClassName:PageBase 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 TODO ADD REASON. 
 * Date:     Sep 13, 2013 1:06:16 PM 
 * @author   huchan
 * @version  
 * @since    JDK 1.6
 * @see 	  http://chon.techliminal.com/page_object/#/slide4
 * @see       https://github.com/ChonC/wtbox/blob/master/src/wtbox/pages/PageBase.java
 */
/**
 * @author huchan
 *
 */
public abstract class PageObject {

    public final WebDriver driver; // this is the webdriver instance
    public static Logger logger=Logger.getLogger(PageObject.class);  // this is the log4j instance
    
	/*
	 * the blow is for the test data we will generate in the email report
	 */
    public  static String status;  //this is the static step status we need 
    public  static String comments;  //this is the comment generate for this step
    //the blow is the data we need to populate in our email for the data used 
    public static String comment_executionstart=null;
    public static String comment_login_credential=null;
    public static String comment_paf_buildnumber=null;
    public static String comment_paf_url=null;
	
	public PageObject(WebDriver driver){
		this.driver=driver;
	}
	
	/**
	 * this is the first method we must use in every page ,so that we can get the page loading time 
	 * @param pagename
	 * @throws IOException 
	 * @throws Exception
	 */
	public void verifyPageElements(String pagename) throws IOException{
	//	this is a line seperator so that we can see the debug log clearly
		logger.info("\n"+Strings.repeat("*", 20)+pagename+Strings.repeat("*", 20));
		//get the page loading time  in every page if we use this method
		long pageloadingtime=getPageLoadTime();
		generatePageLoadTime(pagename, pageloadingtime);			
	}
	
	/** Is the text present in page. */ 
	public  boolean isTextPresent(String text){
		  boolean textpresent=driver.getPageSource().contains(text); 
		  logger.info("Verify the element text present in the page,the text seacrh is :"+text+",and found the element status is:"+textpresent);
	      return textpresent;
	}

	/** Is the Element in page. */
	public boolean isElementPresent(By by) {
			try {
				driver.findElement(by);//if it does not find the element throw NoSuchElementException, thus returns false. 
				return true;
			} catch (NoSuchElementException e) {
				return false;
			}
	 }

	  /**
		* Checks if the elment is in the DOM and displayed. 
		* 
		* @param by - selector to find the element
		* @return true or false
		*/
	  public boolean isElementPresentAndDisplay(WebElement e) {
		  boolean isdisplay=false;
			try {			
				isdisplay=e.isDisplayed(); 
			    logger.info("Verify current element is displayed in the page :"+isdisplay);
			} catch (NoSuchElementException error) {
				logger.info("Sorry,this element not displayed in the page,throw:"+error.getMessage());
			}
			return isdisplay;
	  }

	  /** 
	   * Returns the first WebElement using the given method.  	   
	   * It shortens "driver.findElement(By)". 
	   * @param by 		element locater. 
	   * @return 		the first WebElement
	   */
	  public WebElement getWebElement(By by){
		  	return driver.findElement(by); 			
	  }
	  
	  
	  /**
		 * clear the text in the elment and then type the new string in this element
		 * @param e -- the webelment you need to type 
		 * @param text -- the text you want to input
		 * @author huchan
		 */
		public void clearAndTypeString(WebElement e, String text) {
			logger.info("Type a text into the element is:" + e.getTagName()
					+ ", the inputted text:" + text);
			//e.sendKeys(Keys.DELETE);
			//String code=getInnerHtmlCode(driver, e);
		//	logger.info("Clicked element html code is:"+code);
			highLight(e);
			e.clear();
			e.sendKeys(text);
			//e.sendKeys(Keys.TAB);

		}
				
		/**
		 * highLight:(highlight the web element in the page). 
		 * 

		 * @author huchan
		 * @param driver -- the web driver instance 
		 * @param e -- the web element object
		 * @since JDK 1.6
		 */
		public  void highLightExt(WebDriver driver, WebElement e) {
			logger.info("Highlight the element ,the object is:" + e.getTagName()
					+ ",the text in this object is:" + e.getText());
			Actions action = new Actions(driver);
			action.contextClick(e).perform();
			logger.info("Had right click the object ,then press the escape key");
			e.sendKeys(Keys.ESCAPE);
		}
		public void highLight(WebElement e) {
			if (driver instanceof JavascriptExecutor) {
				executeJS("arguments[0].style.border='3px solid red'",e);
			}
		}
		
		/**
		 * executeJS:(execute the java script in this page). 

		 * @author huchan
		 * @param driver -- the web driver's instance
		 * @param script  --the java script we need to execute
		 * @since JDK 1.6
		 */
		public Object executeJS(String script) {
			logger.info("Run the javascript from page ,the java script is:"
					+ script);
			JavascriptExecutor je = (JavascriptExecutor) driver;
			return je.executeScript(script);

		}
		public void executeJS(String script,WebElement e) {
			logger.info("Run the javascript from page ,the java script is:"
					+ script);
			//highLight(e);
			JavascriptExecutor je = (JavascriptExecutor) driver;
			je.executeScript(script,e);

		}
		public Object executeJSReturn(String script,WebElement e) {
			logger.info("Run the javascript from page ,the java script is:"
					+ script);
			//highLight(e);
			JavascriptExecutor je = (JavascriptExecutor) driver;
			Object object=je.executeScript(script,e);
			return object;

		}
		public Object executeJSReturn(String script) {
			logger.info("Run the javascript from page ,the java script is:"
					+ script);
			JavascriptExecutor je = (JavascriptExecutor) driver;
			Object object=je.executeScript(script);
	        return object;
		}
		
		

		/**
		 * click an element in the page 
		 * @param driver TODO
		 * @param e --the WebElment we need to click
		 * @author huchan
		 */
		public void clickElement(WebElement e) {
			logger.info("Click elements in page-clicked this element:"
					+ e.getTagName() + ",the text is:" + e.getText());
			//In chrome browser this function didn't work ,so give a solution to load the page correctly
		//	((JavascriptExecutor) driver).executeScript("window.scrollTo(0,"+e.getLocation().y+")");
			//String code=getInnerHtmlCode(driver, e);
			//logger.info("Clicked element html code is:"+code);
			highLight(e);
			e.click();
		}
		
		/**
		 * click an element in the page 
		 * @param e --the WebElment we need to click
		 * @author huchan
		 */
		public void clickElementViaJs(WebElement e) {
			logger.info("Click elements in page-clicked this element:"
					+ e.getTagName() + ",the text is:" + e.getText());
			//In chrome browser this function didn't work ,so give a solution to load the page correctly
			highLight(e);
			executeJS("arguments[0].click();", e); 
		
		}
		/**
		 * click an element in the page 
		 * @param e --the WebElment we need to click
		 * @author huchan
		 */
		public void clickElementViaJsID(String elementid) {
			  logger.info("Click elements in page-clicked this element html id is"+elementid);
			//In chrome browser this function didn't work ,so give a solution to load the page correctly
			  //highLight(e);
			  executeJS("document.getElementById('"+elementid+"').click();");
			  logger.info("Clicked element's html ID is:"+elementid);	
		}
		
		/**
		 * click an element in the page 
		 * @param e --the WebElment we need to click
		 * @author huchan
		 */
		public void clickElementViaMouse(WebElement e) {
			logger.info("Click elements in page-clicked this element:"
					+ e.getTagName() + ",the text is:" + e.getText());
			//In chrome browser this function didn't work ,so give a solution to load the page correctly
		//	((JavascriptExecutor) driver).executeScript("window.scrollTo(0,"+e.getLocation().y+")");
			highLight(e);
			new Actions(driver).moveToElement(e).clickAndHold().release().build().perform();
			 //"return arguments[0].fireEvent('onclick');",
			// String code=getInnerHtmlCode(driver, e);
			// logger.info("Clicked element html code is:"+code);
			
		}
		/**
		 * right click an element in the page 
		 * @param e --the WebElment we need to click
		 * @author huchan
		 */
		public void rightClickElement(WebElement e) {
			logger.info("Right Click elements in page-clicked this element:"
					+ e.getTagName() + ",the text is:" + e.getText());
			//In chrome browser this function didn't work ,so give a solution to load the page correctly
		//	((JavascriptExecutor) driver).executeScript("window.scrollTo(0,"+e.getLocation().y+")");
			highLight(e);
			new Actions(driver).contextClick(e).perform();
			 //"return arguments[0].fireEvent('onclick');",
			// String code=getInnerHtmlCode(driver, e);
			//logger.info("Right Clicked element html code is:"+code);
			
		}
		
		/**
		 * send key to an element
		 * @param e --the webelement you want to send the key
		 * @param enter -- the key need to send 
		 * @author huchan
		 */
		public void sendKeys(WebElement e, String enter) {
			logger.info("Send keys in this element:" + e.getTagName()
					+ ",the key we send is:" + enter);
		//	e.clear();
			highLight(e);
			e.sendKeys(enter);
		}

		/**
		 * @param testedURL
		 * @throws Exception 
		 */
		public void open(String testedURL) throws Exception {
			// TODO Auto-generated method stub
			//Date:Sep 26, 20139:14:57 AM
			driver.get(testedURL);
		}



		/**
		 * select an option from the drop list, depends on the visible text
		 * @param e --the web element object
		 * @param text -- the visible text from the dropdown list
		 * @author huchan
		 */
		public void selectElementViaText(WebElement e, String text) {
			logger.info("Select option text from the list ,list element is:"
					+ e.getTagName() + ",the option text is:" + text);
			highLight(e);
			Select select = new Select(e);
			select.selectByVisibleText(text);
		}

		/**
		 * select an option from the drop list, depends on the tag's attribute value
		 * @param e  --the web element object
		 * @param value -- the value attribute for this element
		 * @author huchan
		 */
		public void SelectElementViaValue(WebElement e, String value) {
			logger.info("Select option value from the list ,list element is:"
					+ e.getTagName() + ",the option value is:" + value);
			highLight(e);
			Select select = new Select(e);
			select.selectByValue(value);
		}

		/**
		 * select an option from the drop list, depends on the index ,the index begin with 0
		 * @param e  --the web element object
		 * @param index -- the index of this webelement ,begin with 0
		 * @author huchan
		 */
		public void SelectElementViaIndex(WebElement e, int index) {
			logger.info("Select option index from the list ,list element is:"
					+ e.getTagName() + ",the option index is:" + index);
			highLight(e);
			Select select = new Select(e);
			select.selectByIndex(index);
		}

		


		/**
		 * wait for an object to be dislayed in the page
		 * @param e -- the web element object
		 * @return  true: the object displayed ,
		 *          false:the object not displayed in the page ;
		 * @author huchan
		 * @throws Exception 
		 */
		public boolean waitProcessBarNotAppear(WebElement e){
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
		 * wait for the object displayed in the page ,the time out will be 120 seconds ,if it still not show ,it will failed
		 * @param driver
		 * @param xpathExpression
		 * @return
		 */
		public boolean waitForObjectDisplay(final String xpathExpression){
			boolean findobject=false;
			WebDriverWait wait=new WebDriverWait(driver, 120);
			try{
			wait.until(new ExpectedCondition<Boolean>() {

					@Override
			        public Boolean apply(WebDriver driver) {
			        	logger.info("Enter the waitForObjectDisplay method to wait for the object displayed in the page ");
			        	return (driver.findElement(By.xpath(xpathExpression)).isDisplayed());
			        }
			});
			findobject=true;
			}
			catch(TimeoutException te){
				logger.info("throw expection ,cannot find the web element:"+te.getMessage());
				logger.info("the time out is 120 ,we cannot find this webelment:"+xpathExpression);
				Assert.fail("Cannot find this web element in the page:"+xpathExpression);
			}
			
			return findobject;
		}
		
	
		/**
		 * execute the script to set the datepicker value
		 * @param driver --the webdriver instance
		 * @param elementid -- the web element's id
		 * @param date   --the date we need to set ,it's string
		 * @author huchan
		 */
		public void setDateTimePicker(String elementid,
				String date) {
			logger.info("Set DatePicker Date or Time --Execute the java script to modify the weblement's attribute:value,the element id is:"
					+ elementid);
			
			executeJS("window.document.getElementById('" + elementid
					+ "').setAttribute('value', '" + date + "');");

		}

		/**
		 * select the checkbox ,if it selectd ,we will not select it again
		 * @param e -- the web element object
		 * @author huchan
		 */
		public void checkboxed(WebElement e) {
			highLight(e);
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
		 * get the text in the web element
		 * @param e  -- the web element object
		 * @return  String -- the web element's text
		 * @author huchan
		 */
		public String getElementText(WebElement e) {
			logger.info("Get the element text.The webelement is:" + e.getTagName()
					+ ",the text in the webelement is:" + e.getText().trim());
			highLight(e);
			return e.getText().trim();
		}

		/**
		 * verify the object is enabled in the page
		 * @param e  -- the web element object
		 * @return  true :the object is enabled ,false:the object is disabled
		 * @author huchan
		 */
		public boolean isEnabled(WebElement e) {
			logger.info("Verify webelement Enabled in the page-the current webelement is:"
					+ e.getTagName()
					+ ",the text in the webelement is:"
					+ e.getText().trim());
			highLight(e);
			return e.isEnabled();
		}

		/**
		 * verify the object is selected in the page
		 * @param e --the web element object
		 * @return  true:the object is selected,false:the object is not selected
		 * @author huchan
		 */
		public boolean isSelected(WebElement e) {
			logger.info("Verify webelement Selected in the page-the current webelement is:"
					+ e.getTagName()
					+ ",the text in the webelement is:"
					+ e.getText().trim());
			highLight(e);
			return e.isSelected();

		}

		/**
		 * get the webelement's attribute value
		 * @param e  --the web element object
		 * @param attributename  -- the web element's attribute
		 * @return String-- the attribute value for this web element
		 * @author huchan
		 */
		public String getAttribute(WebElement e, String attributename) {
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
		public String getTagName(WebElement e) {
			logger.info("Get the webelement TagName-the webelement's tag name is:"
					+ e.getTagName() + ",the text in the webelement is:"
					+ e.getText().trim());
			highLight(e);
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
		public List<WebElement> findElementListByTagName(WebElement e,
				String tagname) {
			logger.info("Find all subelements by Tag Name:" + e.getTagName()
					+ ",the sub elment's tag name is:" + tagname);
			highLight(e);
			List<WebElement> elements = e.findElements(By.tagName(tagname));
			return elements;
		}

		/**
		 * find the element by xpath in the page
		 * @param e --the web element object
		 * @param xpath -- the web element's xpath
		 * @return  WebElement -- get the found web element
		 */
		public WebElement findElementByXpath(WebElement e, String xpath) {
			logger.info("Find subelement by Xpath-we will find an sub element with the xpath:"
					+ xpath);
			highLight(e);
			WebElement element = e.findElement(By.xpath(xpath));
			//highLight(driver, element);
			return element;
		}
		/**
		 * find the element by xpath in the page
		 * @param e --the web element object
		 * @param xpath -- the web element's xpath
		 * @return  WebElement -- get the found web element
		 */
		public WebElement findElementByCSS(WebElement e, String css) {
			logger.info("Find subelement by css-we will find an sub element with the css selector:"
					+ css);
			highLight(e);
			WebElement element = e.findElement(By.cssSelector(css));
			//highLight(driver, element);
			return element;
		}
		/**
		 * click the ok button in the pop up dialog (alert dialog)
		 * @param driver  -- the web driver's instance
		 * @param seconds -- the seconds we need to wait to click it
		 * @author huchan
		 */
		public boolean alertClickOK(int seconds) {
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

		public boolean alertClickCancel(int seconds) {
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
		 * getCurrentURL:(get the current page URL address). 

		 * @author huchan
		 * @param driver  --- the web driver instance
		 * @return String ---the url of current page
		 * @since JDK 1.6
		 */
		public String getCurrentPageURL(){
			String pageurl="";
			JavascriptExecutor je=(JavascriptExecutor) driver;
			final String docstate=(String) je.executeScript("return document.readyState");
			logger.info("Current loading page state is:"+docstate);
			WebDriverWait wait=new WebDriverWait(driver,120);
			ExpectedCondition<Boolean> ec = new ExpectedCondition<Boolean>() {
		          @Override
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
		 * wait for the web page to full loading correctly ,it will wait for 3 minutes to load the page ,
		 * if the page not loading in 3 minutes ;it will throw error;
		 * @param driver
		 */
		public boolean waitForBrowserFullSync(){
			final String currentbowserstate=(String)executeJS("return document.readyState;");
			logger.info("Current browser state is:"+currentbowserstate);
			WebDriverWait wdw=new WebDriverWait(driver, 180);
			ExpectedCondition<Boolean> ec=new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
					// TODO Auto-generated method stub
					String newpagestate=(String) executeJS("return document.readyState;");
					logger.debug("the new page state is:"+newpagestate);
					return (newpagestate.equals("complete"));
				}
			};
			
			boolean loaded=wdw.until(ec);
			logger.debug("finally the load is loading with completed status is:"+loaded);
			return loaded;
		}
		
		//***********************************************************************************************************
		/**
		 * switchtoWindow:(Here describle the usage of this function). 
		 * http://santoshsarmajv.blogspot.com/2012/04/how-to-switch-control-to-pop-up-window.html
		 * http://stackoverflow.com/questions/11614188/switch-between-two-browser-windows-using-selenium-webdriver
		 *
		 * @author huchan
		 * @param driver
		 * @param windowTitle
		 * @throws AWTException 
		 * @since JDK 1.6
		 */
		public void switchtoWindow(String windowTitle) throws AWTException{
			Robot robot=new Robot();
			Set<String> allwindows=driver.getWindowHandles();
			for (String window : allwindows) {
	            driver.switchTo().window(window);
	            if (driver.getTitle().contains(windowTitle)) {
	               robot.delay(5000);
	              // robot.keyPress(keycode);
	            }
	        }
		}
		
		/**
		 * refresh the current page
		 * @param driver
		 */
		public void refreshPage(){
			//driver.navigate().refresh();
			logger.info("Now refresh the page to keep the session valid");
			//or blow
			Actions actions = new Actions(driver);
			actions.sendKeys(Keys.F5).perform();
		}
		/**
		 * get the page title
		 * @param driver
		 * @return String
		 */
		public String getPageTitle(){
			String title=driver.getTitle();
			logger.info("Get current page title is:"+title);
			return title;
		}
		
		/**
		 * get the webelement's html code
		 * @param driver
		 * @param e
		 * @return String
		 */
		public String getInnerHtmlCode(WebElement e){
			String contents = (String)executeJSReturn("return arguments[0].innerHTML;", e);
			logger.info("Get the html code for this webelement:"+contents);
			highLight(e);
			return contents;
		}
		
		/**
		 * wait for a few time to find the object displayed in the page
		 * @param driver
		 * @param e
		 * @param timeout
		 * @return true found the element visible displayed in the page ,false ,cannot find the webelement
		 */
		public boolean waitForGUIAppear(WebElement e,long timeout){
			boolean findelement=false;
			WebDriverWait wdw=new WebDriverWait(driver, timeout);
			logger.info("wait for the object displayed in the page:"+getInnerHtmlCode(e));
			wdw.until(ExpectedConditions.visibilityOf(e));
			return findelement;
			
		}
		
		/**
		 * scroll the view to we can see in the page
		 * @param driver
		 * @param e
		 */
		public void scrollToView(WebElement e){
			   highLight(e);
			   executeJS("window.scrollTo(0,"+e.getLocation().y+")");
			   executeJS("arguments[0].scrollIntoView(true);", e);
			   logger.info("Now we scroll the view to the position we can see");

		}
		/**
		 * click the upload button to upload the file ,this is for hte webFile element ,the input type is file
		 * @param driver
		 * @param e
		 * @param filepath
		 * http://sauceio.com/index.php/2012/03/selenium-tips-uploading-files-in-remote-webdriver/
		 * upload the local file from remote webdriver
		 */
		public void uploadFile(WebElement e,String filepath){
			String uploadcode=getInnerHtmlCode(e);
			highLight(e);
			logger.info("the upload webelement html code we get is:"+uploadcode);
			e.sendKeys(filepath);
		}
		
		  
	
		 /**
		  * generate a email page loading row in the page loading table
		 * @param stepname
		 * @param stepchecker
		 * @param status
		 * @param comments
		 * @param driver TODO
		 * @throws IOException 
		 */
		public static void generatePageLoadTime(String pagename,long pageloadtime) throws IOException{
			 String pageloadingfolder=SeleniumCore.getProjectWorkspace()+"reporter";
			 String pageloadingfile=pageloadingfolder+File.separator+"pageloading.log";
			 boolean updatedbefore=false;
			 File pagefile=null;
			 try{
				 
				FilesUtils.newFolder(pageloadingfolder);
				logger.debug("As there is no reporter folder,we created it again");
				 pagefile= new File(pageloadingfile);
				 
				 if (!pagefile.exists()) {
					 pagefile.createNewFile();
					}			
				    BufferedReader br=new BufferedReader(new FileReader(pageloadingfile));
				    String strline = null;
				    while ((strline = br.readLine()) != null) {
						if (strline.contains(pagename)) {
							//find the status report we had reported before
			                FilesUtils.replaceStringOfFile(pageloadingfile,strline,pagename + "|" +pageloadtime);
							updatedbefore=true;
						}
					}
				    br.close();
				    if(!updatedbefore){
					    //if this is the first time to report the step
					    FileWriter writer = new FileWriter(pageloadingfile, true);
						BufferedWriter bufferwriter = new BufferedWriter(writer);
						bufferwriter.write(pagename + " Loading Time|"+pageloadtime + "\n");					
						bufferwriter.close();
				    }
				    
		    	}
					catch (IOException e) {
						logger.error("Sorry Met the IOException for the reporter file :"
								+ pagefile.getAbsolutePath() + " the error Exception is :"
								+ e.getMessage());
					}
			 
		 }

		/**
		 * get the current page loading time ,it will return seconds
		 * @param driver
		 * 
		 * @see http://www.softwareishard.com/blog/firebug/support-for-performance-timing-in-firebug/
		 * @see http://selenium.polteq.com/en/implement-web-timings/
		 * @see http://www.html5rocks.com/en/tutorials/webperformance/basics/
		 * @see http://www.theautomatedtester.co.uk/blog/2010/selenium-webtimings-api.html
		 */
		public long getPageLoadTime(){
			long pageloadtime=0;
			
			//try{
			//different with browser ,ie will return is double value but firefox and chrome will return is long
		//	long pagestartedtime=(Long) executeJSReturn("return window.performance.timing.navigationStart;");
		//	Long tempvalue=(Long) executeJSReturn("return window.performance.timing.loadEventEnd;");
			@SuppressWarnings("unchecked")
			Map<String,Long> pagetimer=(Map<String, Long>)executeJSReturn("var performance = window.performance || window.webkitPerformance || window.mozPerformance || window.msPerformance || {};"+
                           " var timings = performance.timing || {};"+
                           " return timings;");
			long pageloadend=(pagetimer.get("loadEventEnd"))/1000;
			long pageloadstart=(pagetimer.get("navigationStart"))/1000;
			pageloadtime=(pageloadend-pageloadstart);
			logger.info("Get current page loading time is:"+pageloadtime);
		
			return pageloadtime;
		}
		
		/**
		 * sleep the current step for a few seconds 
		 * @param seconds -- the seconds we need to sleep 
		 * @throws InterruptedException
		 * @author huchan
		 */
		public void sleepSeconds(int seconds)
				 {
			logger.info("Begin to sleep current step for " + seconds
					+ " seconds........");
			//You need to be in a synchronized block in order for Object.wait() to work.

			//Also, I recommend looking at the concurrency packages instead of the old school threading packages. They are safer and way easier to work with.
			//synchronized (driver)
		//	{
			//    driver.wait(seconds * 1000);
		//	}
			try {
				Thread.sleep(seconds*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				logger.error("Sleep current step met an error:"+e.getMessage());
			}
		
		}	
}

