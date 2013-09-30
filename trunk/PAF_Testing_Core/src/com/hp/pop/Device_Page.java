/**
 * Project Name:PAF_Testing_Core
 * File Name:Device_Page.java
 * Package Name:com.hp.pop
 * Date:Sep 27, 20139:35:11 AM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.hp.pop;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.hp.utility.SeleniumCore;

/**
 * ClassName:Device_Page 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 TODO ADD REASON. 
 * Date:     Sep 27, 2013 9:35:11 AM 
 * @author   huchan
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class Device_Page extends PageObject {

	
	@FindBy(how = How.XPATH, using = "//*[@id='divSELECT_DEVICES']/h2")
	private WebElement devicehead;
	@FindBy(how = How.XPATH, using = "//*[@id='gview_devicesTable']/div[3]")
	private WebElement devicetable;
	
	@FindBy(how=How.XPATH,using="//*[@id='cb_devicesTable']")
	private WebElement checkallbox;
	
	@FindBy(how=How.XPATH,using="//*[@id='devicesTable']/*/tr[2]/td/b")
    private WebElement customerline;

	@FindBy(how = How.XPATH, using = "//*[@id='back']")
	private WebElement backbtn;
	@FindBy(how = How.XPATH, using = "//*[@id='next']")
	private WebElement nextbtn;
	
	public Device_Page(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void verifyPageElements(String pagename) throws IOException {
		// TODO Auto-generated method stub
		super.verifyPageElements(pagename);
		//Date:Sep 27, 201310:23:41 AM
	}
	
	public Options_Page selectSpecifiedDevice() throws IOException{
		
		
		boolean finddevice=false;
		Map<String, String> mapdata = SeleniumCore
				.importDataTable("device_detail");

		String devicename = mapdata.get("Device_Name");
		logger.info("Select device page-the device get from the test data is:"
				+ devicename);

		List<WebElement> allrows =findElementListByTagName(
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
				devicedata= findElementByXpath(row, "td[4]").getText().trim();
				devicetype=findElementByXpath(row, "td[5]").getText().trim();
				os=findElementByXpath(row, "td[6]").getText().trim();
				osversion=findElementByXpath(row, "td[7]").getText().trim();
				pn=findElementByXpath(row, "td[9]").getText().trim();
				sn=findElementByXpath(row, "td[10]").getText().trim();
				collection=findElementByXpath(row, "td[11]").getText().trim();
				collectiondate=findElementByXpath(row, "td[12]").getText().trim();
			}
			catch(NoSuchElementException nsp){
				logger.info("Current row had not fourth column ,so we ignore it");
			}
			logger.info("Select device page-the found device in the page  is:"
					+ devicedata);
			if (devicedata.equals(devicename)) {
				logger.info("Select device page-Now we found the specified device name in the table");
				WebElement checkboxvalue = findElementByXpath(row,".//td[1]/input");
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
					executeJS("window.scrollTo(0,"+checkboxvalue.getLocation().y+")");
					//SeleniumCore.executeJS(driver, "arguments[0].scrollIntoView(true);", checkboxvalue);
					highLight(checkboxvalue);
					///SeleniumCore.clickElement(checkboxvalue);
					String script="document.getElementById('"+tagid+"').click();";
					executeJS(script);
					
				}else{
				     clickElement(checkboxvalue);
				}
				finddevice=true;
				if(devicetype.equalsIgnoreCase("UNKNOWN")
						||os.equalsIgnoreCase("UNKNOWN")
						||osversion.equalsIgnoreCase("Not Available")
						){
				    //Resulter.log("STATUS_SCAN_DEVICE", "Passed");
				    logger.error("the device list information showed is incorrect[Devcie Name:"+devicedata+",Device Type:"+devicetype+",Device OS:"+
							os+",Device OS Version:"+osversion+",Product Number:"+pn+",Serial Number:"+sn+",Collections:"+collection
							+",Collection Date:"+collectiondate+"]");
				    status="Warning";
				    comments="the device list information showed is incorrect:[Devcie Name:"+devicedata+",Device Type:"+devicetype+",Device OS:"+
							os+",Device OS Version:"+osversion+",Product Number:"+pn+",Serial Number:"+sn+",Collections:"+collection
							+",Collection Date:"+collectiondate+"]";
				    SeleniumCore.generateEmailStep("Device Select", "Selected Matched Device from page",status,comments, driver);
				}
				else{
					status="Passed";
					comments="the device list information showed is incorrect:[Devcie Name:"+devicedata+",Device Type:"+devicetype+",Device OS:"+
							os+",Device OS Version:"+osversion+",Product Number:"+pn+",Serial Number:"+sn+",Collections:"+collection
							+",Collection Date:"+collectiondate+"]";
					SeleniumCore.generateEmailStep("Device Select", "Selected Matched Device from page",status,comments, driver);
					
				}
		      
				break;
			}

		}
        if(finddevice){
		    logger.info("Select device page- we found the matched devices we will click the next button....");
		    clickElement(nextbtn);
		   
     
        }
        else
        {
        	status="Failed";
        	comments="From list cannot find this device:"+devicename;
            SeleniumCore.generateEmailStep("Device Select", "Selected Matched Device from page",status,comments, driver);
        	logger.info("Sorry we cannot find this device name :"+devicename);
        	Assert.fail("Sorry we cannot find this device name :"+devicename+",the device name not matched in the page");

        }
        waitProcessBarNotAppear(driver.findElement(By.xpath(".//*[@id='hpit-busy']/img")));
		return PageFactory.initElements(driver, Options_Page.class);
	}
	
	public Options_Page selectAllDevices() throws IOException{
		String customeraddress=getElementText(customerline);
		String expectedcustomer=Customer_Page.actualCustomername;
		String expectedaddress=Customer_Page.actualAddress;
	    String actualcustomer=customeraddress.split("\\--")[0].trim();
	    String actualaddress=customeraddress.split("\\--")[1].split("\\-")[0].trim();
	   
	    comments="Expected Customer:["+expectedcustomer+"],Acutal Customer:["+actualcustomer+"],Expected Address:["+expectedaddress+"],Actual Address:["+actualaddress+"]";
	    if((expectedcustomer.equals(actualcustomer))&&(expectedaddress.equals(actualaddress))){
	    	logger.info("Customer and Address infomation check is correct with the customer select page ");
	    	status="Passed";
	    	SeleniumCore.generateEmailStep("Device Select", "Select matched device",status,comments, driver);
	    	
	    }
	    else{
	    	status="Warning";
	    	SeleniumCore.generateEmailStep("Device Select", "Select matched device",status,comments, driver);    	
	    }
	    
		checkboxed(checkallbox);
		clickElement(nextbtn);
		sleepSeconds(3);
		return PageFactory.initElements(driver, Options_Page.class);
	}
}

