package com.hp.pop;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.hp.utility.Resulter;
import com.hp.utility.SeleniumCore;

public class Select_Customer_Page {

	private static Logger logger = Logger.getLogger(Select_Customer_Page.class);
	private WebDriver driver;

	@FindBy(how = How.XPATH, using = ".//*[@id='divSELECT_CUSTOMER']/h2")
	private WebElement customertitle;
	@FindBy(how = How.XPATH, using = ".//*[@id='customerOrgSiteCountry']")
	private WebElement country;
	@FindBy(how = How.XPATH, using = ".//*[@id='customerOrgSiteState']")
	private WebElement state;
	@FindBy(how = How.XPATH, using = ".//*[@id='customerOrgSiteCity']")
	private WebElement city;
	@FindBy(how = How.XPATH, using = ".//*[@id='searchString']")
	private WebElement containtext;

	@FindBy(how = How.XPATH, using = ".//*[@id='main_column']/div[2]")
	private WebElement mainresult;

	@FindBy(how = How.XPATH, using = ".//*[@id='gview_optionalCustomersGrid']/div[3]")
	private WebElement customerlist;

	@FindBy(how = How.XPATH, using = ".//*[@id='back']")
	private WebElement backbtn;
	@FindBy(how = How.XPATH, using = ".//*[@id='next']")
	private WebElement nextbtn;

	public Select_Customer_Page(WebDriver driver) {
		this.driver = driver;
	}

	public void verifyPageElements() {
		boolean title = SeleniumCore.isDisplayed(customertitle);
		boolean backenabled = SeleniumCore.isEnabled(backbtn);
		boolean nextenabled = SeleniumCore.isDisplayed(nextbtn);
		
		String pagename=this.getClass().getName();
		logger.info("\n***************************************"+pagename+"****************************************************");

		if (title && (backenabled == false) & nextenabled) {
			logger.info("Select customer page -verify all the page webelements are displayed in the page,we will filter the customer ");
		} else {
			Assert.fail("the page cannot show the web element correctly");
			logger.info("Select customer page -Sorry the webelements cannot show in the page ,test is failed ,manualy check this value again please");
			logger.info("Select customer page -The Expected result is:true,but actually the title display in the page is:"
					+ title);
			logger.info("Select customer page -the expectd back button is disabled,but actually the back button enabled is:"
					+ backenabled);
			logger.info("Select customer page -The next button expected is enabled ,but actually the next button enabled is:"
					+ nextenabled);
		}

	}

	public Select_Device_Page newCustomer() throws Exception {
		//
		boolean findcustomername=false;
		Map<String, String> mapdata = SeleniumCore
				.importDataTable("device_detail");
		String countrydata = mapdata.get("Country");
		String statedata = mapdata.get("State");
		String citydata = mapdata.get("City");
		String containtextdata = mapdata.get("Contain_Text");

		String customerdata = mapdata.get("Customer_Name");
		String addressdata = mapdata.get("Address");

		String imagepath = ".//*[@id='hpit-busy']/img";

		SeleniumCore.selectElementViaText(country, countrydata);
		SeleniumCore.waitForObjectDisplay(driver.findElement(By
				.xpath(imagepath)));
		logger.info("Select customer page -select the customer country:"
				+ countrydata);
        Resulter.log("COMMENT_COUNTRY", countrydata);
		
		SeleniumCore.selectElementViaText(state, statedata);
		SeleniumCore.waitForObjectDisplay(driver.findElement(By
				.xpath(imagepath)));
		logger.info("Select customer page -select the customer state:"
				+ statedata);
        Resulter.log("COMMENT_SITE", statedata);
        
		SeleniumCore.selectElementViaText(city, citydata);
		SeleniumCore.waitForObjectDisplay(driver.findElement(By
				.xpath(imagepath)));
		logger.info("Select customer page -select the customer city:"
				+ citydata);
        Resulter.log("COMMENT_CITY", citydata);
        Resulter.log("COMMENT_ADDRESS", addressdata);
      //  Resulter.log("COMMENT_ORG_SITE_ID", "N/A");
        
		SeleniumCore.clearAndTypeString(containtext, containtextdata);
		logger.info("Select customer page -input the contain text with text:"
				+ containtextdata);

		List<WebElement> allrows = SeleniumCore.findElementListByTagName(
				customerlist, "tr");
		logger.info("we will search the Customer list to find the matched customer....");
		for (WebElement row : allrows) {
			WebElement checkboxvalue = SeleniumCore.findElementByXpath(row,
					"td[1]");
			String customervalue = SeleniumCore
					.findElementByXpath(row, "td[2]").getText().trim();
			String addressvalue = SeleniumCore.findElementByXpath(row, "td[3]")
					.getText().trim();
			String orgsite= SeleniumCore.findElementByXpath(row, "td[4]")
					.getText().trim();
			logger.info("Select customer page -the found customer name  is:"
					+ customervalue + ",adresss is:" + addressvalue);
			if (customervalue.equals(customerdata)
					&& addressvalue.equals(addressdata)) {
				logger.info("Select customer page -Now we found the specified customer name in the table,we will check this customer in the table,and click the next button");
				Resulter.log("COMMENT_ORG_SITE_ID", orgsite);
				SeleniumCore.clickElement(checkboxvalue);
				findcustomername=true;
				break;
			}

		}
        if(findcustomername){
		// click the next button
        	Resulter.log("STATUS_SCAN_CUSTOMER", "Passed");
        	Resulter.log("COMMENT_SCAN_CUSTOMER", "Customer Name:"+customerdata+",Site Address:"+addressdata+"");
		    logger.info("Select customer page -we had found the matched customer then click the next button");
		    SeleniumCore.clickElement(nextbtn);		
        }
        else
        {
        	Resulter.log("STATUS_SCAN_CUSTOMER", "Failed");
        	Resulter.log("COMMENT_SCAN_CUSTOMER", "We cannot find Customer Name:"+customerdata+":,Site Address:"+addressdata+",from the search list result");
        	logger.info("Sorry we cannot find this customer name :"+customerdata+" ,with this site addrsss:"+addressdata);
        	Assert.fail("Sorry we cannot find this customer name :"+customerdata+" ,with this site addrsss:"+addressdata+",the customer name or address not matched in the page");
        }
        
        
        SeleniumCore.sleepSeconds(4);
		return PageFactory.initElements(driver, Select_Device_Page.class);
		
	}

}
