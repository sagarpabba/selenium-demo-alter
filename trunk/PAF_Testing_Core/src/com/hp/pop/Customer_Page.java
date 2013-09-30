/**
 * Project Name:PAF_Testing_Core
 * File Name:Customer_Page.java
 * Package Name:com.hp.pop
 * Date:Sep 27, 20139:34:58 AM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/
/**
 * 
 */

package com.hp.pop;

import java.io.File;
import java.io.IOException;
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

/**
 * ClassName:Customer_Page 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 TODO ADD REASON. 
 * Date:     Sep 27, 2013 9:34:58 AM 
 * @author   huchan
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
/**
 * @author huchan
 *
 */
public class Customer_Page extends PageObject {

	
	
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
    
    
	/**
	 * @param driver
	 */
	public Customer_Page(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void verifyPageElements(String pagename) throws IOException {
		// TODO Auto-generated method stub
		super.verifyPageElements(pagename);
		//Date:Sep 27, 201310:23:35 AM
		status="Passed";
		comments="Customer Page Elements displayed correctly";
	}
	
	public Device_Page selectCustomerViaSNPN(String snpnfile) throws IOException{
		
		
		int tablesize= findElementListByTagName(
				customerlist, "tr").size();
		clickElement(filetab);
        //upload the excel file 
		uploadFile(browserbtn, snpnfile);
		String displayedfilename=filename.getAttribute("value");
	    String expectedfilename=new File(snpnfile).getName();
	    if(expectedfilename.equals(displayedfilename)){
	    	SeleniumCore.generateEmailStep("Select Customer", "Verify upload file name in editbox", "Passed", "Expected File name:["+expectedfilename+"],Actual File Name:["+displayedfilename+"]", driver);
	    }
	    else
	    {
	    	SeleniumCore.generateEmailStep("Select Customer", "Verify upload file name in editbox", "Failed", "Expected File name:["+expectedfilename+"],Actual File Name:["+displayedfilename+"]", driver);  	
	    }
		clickElement(uploadbtn);
		
		if(tablesize>1){ // if this is not the first time to upload the file
			clickElement(driver.findElement(By.xpath("//body/*/div[3]/div/button[2]")));
		}
		waitProcessBarNotAppear(driver.findElement(By.xpath(".//*[@id='hpit-busy']/img")));
		
		//check the edit box is clear
		String cleardevice=filename.getAttribute("value");
		if(!cleardevice.equals("")){
			SeleniumCore.generateEmailStep("Select Customer", "Verify upload file name in editbox", "Warning", "Expected File name:[],Actual File Name:["+cleardevice+"]", driver);			
		}
		
		checkboxed(driver.findElement(By.xpath("//*[@id='cb_optionalCustomersGrid']")));
		//find the customer name and address
		List<WebElement> allrows = findElementListByTagName(
				customerlist, "tr");
		logger.info("we will search the Customer list to find the matched customer....");

		for (WebElement row : allrows) {			
		    actualCustomername= findElementByXpath(row, ".//td[2]").getText().trim();
			actualAddress=findElementByXpath(row, ".//td[3]")
					.getText().trim();
			if((!actualCustomername.equals(""))&&(!actualAddress.equals(""))){
				logger.info("we found the customer name:"+actualCustomername+",the address is:"+actualAddress+" in the customer table list");
				break;
			}
		}
		clickElement(nextbtn);
		waitProcessBarNotAppear(driver.findElement(By.xpath(".//*[@id='hpit-busy']/img")));
		sleepSeconds(2);
		status="Passed";
		comments="Selected Customer Name:"+actualCustomername+",Address is:"+actualAddress;
		SeleniumCore.generateEmailStep("Select Specified Customer", "Select a Customer with Address", status, comments, driver);
		return PageFactory.initElements(driver, Device_Page.class);
	}
	
	public Device_Page selectCustomerViaFilter() throws IOException{
		
		boolean findcustomername=false;
		Map<String, String> mapdata = SeleniumCore
				.importDataTable("device_detail");
		String countrydata = mapdata.get("Country");
		String statedata = mapdata.get("State");
		String citydata = mapdata.get("City");
		String containtextdata = mapdata.get("Contain_Text");

		String customerdata = mapdata.get("Customer_Name");
		String addressdata = mapdata.get("Address");

	
		//change to the customer filter tab,this is for hte FUT environment
		clickElement(filtertab);

		selectElementViaText(country, countrydata);
		waitProcessBarNotAppear(driver.findElement(By
				.xpath(".//*[@id='hpit-busy']/img")));
		logger.info("Select customer page -select the customer country:"
				+ countrydata);
       // Resulter.log("COMMENT_COUNTRY", countrydata);
		
		selectElementViaText(state, statedata);
		waitProcessBarNotAppear(driver.findElement(By
				.xpath(".//*[@id='hpit-busy']/img")));
		logger.info("Select customer page -select the customer state:"
				+ statedata);
      //  Resulter.log("COMMENT_SITE", statedata);
        
		selectElementViaText(city, citydata);
		waitProcessBarNotAppear(driver.findElement(By
				.xpath(".//*[@id='hpit-busy']/img")));
		logger.info("Select customer page -select the customer city:"
				+ citydata);
       // Resulter.log("COMMENT_CITY", citydata);
        //Resulter.log("COMMENT_ADDRESS", addressdata);
      //  Resulter.log("COMMENT_ORG_SITE_ID", "N/A");
        
		clearAndTypeString(containtext, containtextdata);
		logger.info("Select customer page -input the contain text with text:"
				+ containtextdata);
		waitProcessBarNotAppear(driver.findElement(By
				.xpath(".//*[@id='hpit-busy']/img")));
		List<WebElement> allrows =findElementListByTagName(
				customerlist, "tr");
		logger.info("we will search the Customer list to find the matched customer....");
		for (WebElement row : allrows) {
			String customervalue = findElementByXpath(row, ".//td[2]").getText().trim();
			String addressvalue =  findElementByXpath(row, ".//td[3]")
					.getText().trim();
			String orgsite= findElementByXpath(row, ".//td[4]")
					.getText().trim();
			logger.info("Select customer page -the found customer name  is:"
					+ customervalue + ",adresss is:" + addressvalue+",org site is:"+orgsite);
			if (customervalue.equals(customerdata)
					&& addressvalue.equals(addressdata)) {
				logger.info("Select customer page -Now we found the specified customer name in the table,we will check this customer in the table,and click the next button");
				WebElement checkboxvalue;
				if(SeleniumCore.getBrowserType(driver).contains("chrome")){
					logger.info("as the current browser is chrome we use the js to select the customer in the page");
					//SeleniumCore.executeJS(driver, "arguments[0].scrollIntoView(true);", checkboxvalue);
					//SeleniumCore.executeJS(driver, "window.scrollTo(0,"+checkboxvalue.getLocation().y+")");
					checkboxvalue =findElementByXpath(row,
							".//td[1]");
			        clickElementViaJs(checkboxvalue);
				}
				else{
					checkboxvalue = findElementByXpath(row,
							".//td[1]");
					highLight(checkboxvalue);
					clickElement(checkboxvalue);
				}
				findcustomername=true;
				status="Passed";
				comments="Customer Name:"+customervalue+",Address is:"+addressvalue+",Org Site is:"+orgsite;
				break;
			}
			//wait for the page loading correctly
			waitProcessBarNotAppear(driver.findElement(By
						.xpath(".//*[@id='hpit-busy']/img")));
			 
		}
		
        if(findcustomername){
		    SeleniumCore.generateEmailStep("Customer Select", "Find Customer name and address", status, comments, driver);
		    logger.info("Select customer page -we had found the matched customer then click the next button");		  
		    clickElementViaJs(nextbtn);
		    logger.info("click the next button now ...");
		    waitProcessBarNotAppear(driver.findElement(By
					.xpath(".//*[@id='hpit-busy']/img")));
		    
		    
        }
        else
        {
        	status="Failed";
        	comments="Cannot find the specified Customer from the list,Customer Name:"+customerdata;
        	SeleniumCore.generateEmailStep("Customer Select", "Find Customer name and address",status, comments, driver);
        		
            logger.info("Sorry we cannot find this customer name :"+customerdata+" ,with this site addrsss:"+addressdata);
        	Assert.fail("Sorry we cannot find this customer name :"+customerdata+" ,with this site addrsss:"+addressdata+",the customer name or address not matched in the page");
        }
        
        
        sleepSeconds(3);
        
		return PageFactory.initElements(driver, Device_Page.class);
	}
}

