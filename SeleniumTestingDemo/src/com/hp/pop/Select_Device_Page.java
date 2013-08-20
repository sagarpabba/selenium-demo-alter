package com.hp.pop;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
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

	@FindBy(how = How.XPATH, using = ".//*[@id='divSELECT_DEVICES']/h2")
	private WebElement devicehead;
	@FindBy(how = How.XPATH, using = ".//*[@id='gview_devicesTable']/div[3]")
	private WebElement devicetable;

	@FindBy(how = How.XPATH, using = ".//*[@id='back']")
	private WebElement backbtn;
	@FindBy(how = How.XPATH, using = ".//*[@id='next']")
	private WebElement nextbtn;

	public Select_Device_Page(WebDriver driver) {
		this.driver = driver;
	}

	public void verifyPageElements() {
		boolean displayeddevice = SeleniumCore.isDisplayed(devicehead);
		boolean enabledbackbtn = SeleniumCore.isEnabled(backbtn);
		boolean enablednextbtn = SeleniumCore.isEnabled(nextbtn);
		
		String pagename=this.getClass().getName();
		logger.info("\n***************************************"+pagename+"****************************************************");

		if (displayeddevice && enabledbackbtn && enablednextbtn) {
			logger.info("Select device page-the page webelement is displayed in the page");
			logger.info("Select device page-device head title is:"
					+ SeleniumCore.getElementText(devicehead));
		} else {
			logger.info("Select device page-Sorry the webelements cannot show in the page ,test is failed ,manualy check this value again please");
			logger.info("Select device page-The Expected result is:true,but actually the title display in the page is:"
					+ displayeddevice);
			logger.info("Select device page-the expectd back button is enabled,but actually the back button enabled is:"
					+ enabledbackbtn);
			logger.info("Select device page-The next button expected is enabled ,but actually the next button enabled is:"
					+ enablednextbtn);
			Assert.fail("the device head not displayed in the page or the back button is disabled or the next button is disabled in the page ");
		
		}
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
		for (WebElement row : allrows) {
			WebElement checkboxvalue = SeleniumCore.findElementByXpath(row,
					"td[1]");
			String devicedata = SeleniumCore.findElementByXpath(row, "td[3]")
					.getText().trim();
			logger.info("Select device page-the found device in the page  is:"
					+ devicedata);
			if (devicedata.equals(devicename)) {
				logger.info("Select device page-Now we found the specified device name in the table");
				SeleniumCore.clickElement(checkboxvalue);
				finddevice=true;
				break;
			}

		}
        if(finddevice){
		    logger.info("Select device page- we found the matched devices we will click the next button....");
		    SeleniumCore.clickElement(nextbtn);
		    Resulter.log("STATUS_SCAN_DEVICE", "Passed");
        	Resulter.log("COMMENT_SCAN_DEVICE", "Devcie name is:"+devicename);
     
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
