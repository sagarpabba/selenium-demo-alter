package com.hp.pop;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.hp.utility.Resulter;
import com.hp.utility.SeleniumCore;

public class Select_Device_Page {

	private static Logger logger = Logger.getLogger(Select_Device_Page.class);
	private WebDriver driver;

	@FindBy(how = How.XPATH, using = "//*[@id='divSELECT_DEVICES']/h2")
	private WebElement devicehead;
	@FindBy(how = How.XPATH, using = "//*[@id='gview_devicesTable']/div[3]")
	private WebElement devicetable;

	@FindBy(how = How.XPATH, using = "//*[@id='back']")
	private WebElement backbtn;
	@FindBy(how = How.XPATH, using = "//*[@id='next']")
	private WebElement nextbtn;

	public Select_Device_Page(WebDriver driver) {
		this.driver = driver;
	}

	public void verifyPageElements() {
		
		String pagename=this.getClass().getName();
		logger.info("\n***************************************"+pagename+"****************************************************");
		//boolean isdes=devicehead.isDisplayed();
        SeleniumCore.assertDisplayed("Assert the title displayed in the page", devicehead);
        SeleniumCore.assertEnabled("Assert the back button is enabled in the page", backbtn);
        SeleniumCore.assertEnabled("Assert the next button is enabled in the page", nextbtn);
		
	}

	public Select_Options_Page selectDevice() throws Exception {

		boolean finddevice=false;
		Map<String, String> mapdata = SeleniumCore
				.importDataTable("device_detail");

		String devicename = mapdata.get("Device_Name");
		logger.info("Select device page-the device get from the test data is:"
				+ devicename);

		List<WebElement> allrows = SeleniumCore.findElementListByTagName(
				devicetable, "tr");
		//get the assoicated device detail information
		String devicedata="";
		String devicetype="";
		String os="";
		String osversion="";
		String pn="";
		String sn="";
		String collection="";
		String collectiondate="";
		
		for (WebElement row : allrows) {
			// in FUT it changed to td[4]
		//	String trid=row.getAttribute("id");		
			try{
				devicedata= SeleniumCore.findElementByXpath(row, "td[4]").getText().trim();
				devicetype=SeleniumCore.findElementByXpath(row, "td[5]").getText().trim();
				os=SeleniumCore.findElementByXpath(row, "td[6]").getText().trim();
				osversion=SeleniumCore.findElementByXpath(row, "td[7]").getText().trim();
				pn=SeleniumCore.findElementByXpath(row, "td[9]").getText().trim();
				sn=SeleniumCore.findElementByXpath(row, "td[10]").getText().trim();
				collection=SeleniumCore.findElementByXpath(row, "td[11]").getText().trim();
				collectiondate=SeleniumCore.findElementByXpath(row, "td[12]").getText().trim();
			}
			catch(NoSuchElementException nsp){
				logger.info("Current row had not fourth column ,so we ignore it");
			}
			logger.info("Select device page-the found device in the page  is:"
					+ devicedata);
			if (devicedata.equals(devicename)) {
				logger.info("Select device page-Now we found the specified device name in the table");
				WebElement checkboxvalue = SeleniumCore.findElementByXpath(row,".//td[1]/input");
				if(SeleniumCore.getBrowserType(driver).contains("chrome")){
					logger.info("as the current browser is chrome we use the js to select the customer in the page");
					//SeleniumCore.executeJS(driver, "arguments[0].scrollIntoView(true);", checkboxvalue);
					//SeleniumCore.executeJS(driver, "window.scrollTo(0,"+checkboxvalue.getLocation().y+")");	
				  //  SeleniumCore.executeJS(driver, "var evt = document.createEvent('MouseEvents');"
					//		+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
				    	//	+ "arguments[0].dispatchEvent(evt);", checkboxvalue);
					String tagid=checkboxvalue.getAttribute("id");
					logger.info("the checkbox id is:"+tagid);
					//WebElement checkcss=row.findElement(By.cssSelector("#"+tagid));
					SeleniumCore.executeJS(driver, "window.scrollTo(0,"+checkboxvalue.getLocation().y+")");
					//SeleniumCore.executeJS(driver, "arguments[0].scrollIntoView(true);", checkboxvalue);
					SeleniumCore.highLight(driver, checkboxvalue);
					///SeleniumCore.clickElement(checkboxvalue);
					String script="document.getElementById('"+tagid+"').click();";
					SeleniumCore.executeJS(driver, script);
					
				}else{
				SeleniumCore.clickElement(driver, checkboxvalue);
				}
				finddevice=true;
				if(devicetype.equalsIgnoreCase("UNKNOWN")
						||os.equalsIgnoreCase("UNKNOWN")
						||osversion.equalsIgnoreCase("Not Available")
						){
				    Resulter.log("STATUS_SCAN_DEVICE", "Passed");
				    logger.error("the device list information showed is incorrect......Devcie Name:"+devicedata+",Device Type:"+devicetype+",Device OS:"+
							os+",Device OS Version:"+osversion+",Product Number:"+pn+",Serial Number:"+sn+",Collections:"+collection
							+",Collection Date:"+collectiondate);
				}
				else{
					Resulter.log("STATUS_SCAN_DEVICE", "Passed");
					
				}
		        Resulter.log("COMMENT_SCAN_DEVICE", "Device Name:"+devicedata+",Device Type:"+devicetype+",Device OS:"+
				os+",Device OS Version:"+osversion+",Product Number:"+pn+",Serial Number:"+sn+",Collections:"+collection
				+",Collection Date:"+collectiondate);
				break;
			}

		}
        if(finddevice){
		    logger.info("Select device page- we found the matched devices we will click the next button....");
		    SeleniumCore.clickElement(driver, nextbtn);
		   
     
        }
        else
        {
        	Resulter.log("STATUS_SCAN_DEVICE", "Failed");
        	Resulter.log("COMMENT_SCAN_DEVICE", "We cannot find device name:"+devicename+" ,from the search list result");
        	logger.info("Sorry we cannot find this device name :"+devicename);
        	Assert.fail("Sorry we cannot find this device name :"+devicename+",the device name not matched in the page");

        }
		SeleniumCore.sleepSeconds(3);
		return PageFactory.initElements(driver, Select_Options_Page.class);
	}
}
