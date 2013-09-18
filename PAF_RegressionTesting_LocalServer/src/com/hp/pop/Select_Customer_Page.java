package com.hp.pop;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.hp.utility.SeleniumCore;

public class Select_Customer_Page extends PageObject {

	@FindBy(how = How.XPATH, using = "//*[@id='divSELECT_CUSTOMER']/h2")
	private WebElement customertitle;
	@FindBy(how = How.XPATH, using = "//*[@id='customerOrgSiteCountry']")
	private WebElement country;
	@FindBy(how = How.XPATH, using = "//*[@id='customerOrgSiteState']")
	private WebElement state;
	@FindBy(how = How.XPATH, using = "//*[@id='customerOrgSiteCity']")
	private WebElement city;
	@FindBy(how = How.XPATH, using = "//*[@id='searchString']")
	private WebElement containtext;

	@FindBy(how = How.XPATH, using = "//*[@id='main_column']/div[2]")
	private WebElement mainresult;

	@FindBy(how = How.XPATH, using = "//*[@id='gview_optionalCustomersGrid']/div[3]")
	private WebElement customerlist;
	
	// this is the FUT element here 
	@FindBy(how=How.XPATH,using="//*[@id='requestTabDiv']/ul/li[1]/a")
	private WebElement filtertab;
    @FindBy(how=How.XPATH,using="//*[@id='requestTabDiv']/ul/li[2]/a")
    private WebElement filetab;
    /*this is for the upload by file 
     * 20130911 Alter update for this feature
    */
    @FindBy(how=How.XPATH,using="//*[@id='multipartFileUpload']")
    private WebElement browserbtn;
    @FindBy(how=How.XPATH,using="//*[@id='btnUpload']")
    private WebElement uploadbtn;  
    @FindBy(how=How.XPATH,using="//*[@id='fileName']")
    private WebElement filename;    
    
	@FindBy(how = How.XPATH, using = "//*[@id='back']")
	private WebElement backbtn;
	@FindBy(how = How.XPATH, using = "//*[@id='next']")
	private WebElement nextbtn;
	
	
	//share the customer name and address across the two class file
	public static String actualCustomername="";
	public static String actualAddress="";

	public Select_Customer_Page(WebDriver driver) {
		super(driver);
	}

	public void verifyPageElements() {
		String pagename=this.getClass().getName();
		logger.info("\n***************************************"+pagename+"****************************************************");
        SeleniumCore.assertDisplayed("Assert the title displayed in the page", customertitle);
        SeleniumCore.assertDisabled("Assert the back button is disabled in the page", backbtn);
        SeleniumCore.assertEnabled("Assert the next button is enabled in the page", nextbtn);
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
		
		
		//change to the customer filter tab,this is for hte FUT environment
		SeleniumCore.clickElement(driver, filtertab);

		SeleniumCore.selectElementViaText(country, countrydata);
		SeleniumCore.waitProcessBarNotAppear(driver.findElement(By
				.xpath(imagepath)));
		logger.info("Select customer page -select the customer country:"
				+ countrydata);
       // Resulter.log("COMMENT_COUNTRY", countrydata);
		
		SeleniumCore.selectElementViaText(state, statedata);
		SeleniumCore.waitProcessBarNotAppear(driver.findElement(By
				.xpath(imagepath)));
		logger.info("Select customer page -select the customer state:"
				+ statedata);
      //  Resulter.log("COMMENT_SITE", statedata);
        
		SeleniumCore.selectElementViaText(city, citydata);
		SeleniumCore.waitProcessBarNotAppear(driver.findElement(By
				.xpath(imagepath)));
		logger.info("Select customer page -select the customer city:"
				+ citydata);
       // Resulter.log("COMMENT_CITY", citydata);
        //Resulter.log("COMMENT_ADDRESS", addressdata);
      //  Resulter.log("COMMENT_ORG_SITE_ID", "N/A");
        
		SeleniumCore.clearAndTypeString(containtext, containtextdata);
		logger.info("Select customer page -input the contain text with text:"
				+ containtextdata);
		SeleniumCore.highLight(driver, containtext);
		SeleniumCore.waitProcessBarNotAppear(driver.findElement(By
				.xpath(imagepath)));
		SeleniumCore.sleepSeconds(4);
		List<WebElement> allrows = SeleniumCore.findElementListByTagName(
				customerlist, "tr");
		logger.info("we will search the Customer list to find the matched customer....");
		for (WebElement row : allrows) {
			
			String customervalue = SeleniumCore
					.findElementByXpath(row, ".//td[2]").getText().trim();
			String addressvalue = SeleniumCore.findElementByXpath(row, ".//td[3]")
					.getText().trim();
			String orgsite= SeleniumCore.findElementByXpath(row, ".//td[4]")
					.getText().trim();
			logger.info("Select customer page -the found customer name  is:"
					+ customervalue + ",adresss is:" + addressvalue);
			if (customervalue.equals(customerdata)
					&& addressvalue.equals(addressdata)) {
				logger.info("Select customer page -Now we found the specified customer name in the table,we will check this customer in the table,and click the next button");
				
				//WebElement primarybox=SeleniumCore.findElementByXpath(row, ".//td[5]/input");
			//	Resulter.log("COMMENT_ORG_SITE_ID", orgsite);
			//	String tagid=checkboxvalue.getAttribute("id");
				//WebElement checkcss=row.findElement(By.cssSelector("#"+tagid));
				
				//SeleniumCore.highLight(driver, checkboxvalue);
				//logger.info(checkboxvalue.getTagName());
			//	checkboxvalue.submit();
				//checkboxvalue.getAttribute(arg0)				
				//SeleniumCore.sendKeys(checkboxvalue, Keys.ENTER);
				WebElement checkboxvalue;
				if(SeleniumCore.getBrowserType(driver).contains("chrome")){
					logger.info("as the current browser is chrome we use the js to select the customer in the page");
					//SeleniumCore.executeJS(driver, "arguments[0].scrollIntoView(true);", checkboxvalue);
					//SeleniumCore.executeJS(driver, "window.scrollTo(0,"+checkboxvalue.getLocation().y+")");
					checkboxvalue = SeleniumCore.findElementByXpath(row,
							".//td[1]");
			        SeleniumCore.clickElementViaJs(driver, checkboxvalue);
				}
				else{
					checkboxvalue = SeleniumCore.findElementByXpath(row,
							".//td[1]");
					SeleniumCore.highLight(driver, checkboxvalue);
					SeleniumCore.clickElement(driver, checkboxvalue);
				}
				findcustomername=true;
				break;
			}
			//wait for the page loading correctly
			 SeleniumCore.waitProcessBarNotAppear(driver.findElement(By
						.xpath(imagepath)));
			 
		}
		
        if(findcustomername){
		// click the next button
        	//Resulter.log("STATUS_SCAN_CUSTOMER", "Passed");
        	//Resulter.log("COMMENT_SCAN_CUSTOMER", "Customer Name:"+customerdata+",Site Address:"+addressdata+"");
		    SeleniumCore.generateEmailStep("Customer Select", "Find Customer name and address", "Passed", "Customer Name:"+customerdata+",Address is:"+addressdata, driver);

		    logger.info("Select customer page -we had found the matched customer then click the next button");
		  
		    SeleniumCore.clickElementViaJs(driver, nextbtn);
		    logger.info("click the next button now ...");
		    SeleniumCore.waitProcessBarNotAppear(driver.findElement(By
					.xpath(imagepath)));
		    
		    
        }
        else
        {
        //	Resulter.log("STATUS_SCAN_CUSTOMER", "Failed");
        //	Resulter.log("COMMENT_SCAN_CUSTOMER", "We cannot find Customer Name:"+customerdata+":,Site Address:"+addressdata+",from the search list result");
		    SeleniumCore.generateEmailStep("Customer Select", "Find Customer name and address", "Failed", "These cannot be found in page.Customer Name:"+customerdata+",Address is:"+addressdata, driver);
        	logger.info("Sorry we cannot find this customer name :"+customerdata+" ,with this site addrsss:"+addressdata);
        	Assert.fail("Sorry we cannot find this customer name :"+customerdata+" ,with this site addrsss:"+addressdata+",the customer name or address not matched in the page");
        }
        
        
        SeleniumCore.sleepSeconds(3);
        
        SeleniumCore.generateEmailData("Customer Name", customerdata);
        SeleniumCore.generateEmailData("Customer Address", addressdata);
       // SeleniumCore.generateData("Device N", datavalue);
        
		return PageFactory.initElements(driver, Select_Device_Page.class);
		
	}
	/*
	 * select customer from the sn/pn file
	 * 
	 */
	
	public Select_Device_Page selectAllCustomersByFile(String devicefilepath) throws Exception {
		
		
		int tablesize= SeleniumCore.findElementListByTagName(
				customerlist, "tr").size();
		SeleniumCore.clickElement(driver, filetab);
	//	if(devicetype.contains(""))
		SeleniumCore.uploadFile(driver,browserbtn, devicefilepath);
		SeleniumCore.sleepSeconds(4);
		String filenametext=filename.getAttribute("value");
	    String expectedfilename=new File(devicefilepath).getName();
	    if(expectedfilename.equals(filenametext)){
	    	SeleniumCore.generateEmailStep("Select Customer", "Verify upload file name in editbox", "Passed", "Expected File name:["+expectedfilename+"],Actual File Name:["+filenametext+"]", driver);
	    }
	    else
	    {
	    	SeleniumCore.generateEmailStep("Select Customer", "Verify upload file name in editbox", "Failed", "Expected File name:["+expectedfilename+"],Actual File Name:["+filenametext+"]", driver);
	    	
	    }
		SeleniumCore.clickElement(driver, uploadbtn);
		
		
	 //   boolean isdisplay=SeleniumCore.waitForObjectDisplay(driver, "//*[@id='dialog-upload-confirmAgain']");
		//if(isdisplay){
		//if this is not the first time to upload the sn/pn file
		if(tablesize>1){
			SeleniumCore.clickElement(driver, driver.findElement(By.xpath("//body/*/div[3]/div/button[2]")));
		}
		SeleniumCore.waitProcessBarNotAppear(driver.findElement(By.xpath(".//*[@id='hpit-busy']/img")));
		SeleniumCore.sleepSeconds(3);
		//check the edit box is clear
		String cleardevice=filename.getAttribute("value");
		if(!cleardevice.equals("")){
			SeleniumCore.generateEmailStep("Select Customer", "Verify upload file name in editbox", "Warning", "Expected File name:[],Actual File Name:["+filenametext+"]", driver);			
		}
		
		SeleniumCore.checkboxed(driver.findElement(By.xpath("//*[@id='cb_optionalCustomersGrid']")));
		//find the customer name and address
		List<WebElement> allrows = SeleniumCore.findElementListByTagName(
				customerlist, "tr");
		logger.info("we will search the Customer list to find the matched customer....");

		for (WebElement row : allrows) {			
		    actualCustomername= SeleniumCore
					.findElementByXpath(row, ".//td[2]").getText().trim();
			 actualAddress= SeleniumCore.findElementByXpath(row, ".//td[3]")
					.getText().trim();
			if((!actualCustomername.equals(""))&&(!actualAddress.equals(""))){
				logger.info("we found the customer name:"+actualCustomername+",the address is:"+actualAddress+" in the customer table list");
				break;
			}
		}
		SeleniumCore.generateEmailStep("", "Customer Selected via SN/PN File", "Passed", "Customer Name:"+actualCustomername+",Address:"+actualAddress, driver);
		SeleniumCore.clickElement(driver, nextbtn);
		SeleniumCore.waitProcessBarNotAppear(driver.findElement(By.xpath(".//*[@id='hpit-busy']/img")));
		SeleniumCore.sleepSeconds(2);
		return PageFactory.initElements(driver, Select_Device_Page.class);
	}

}
