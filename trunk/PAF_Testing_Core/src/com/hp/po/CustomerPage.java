/**
 * Project Name:PAF_Testing_Core
 * File Name:CustomerPage.java
 * Package Name:com.hp.pageobject
 * Date:Oct 27, 201310:45:10 AM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.hp.po;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * ClassName:CustomerPage 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 TODO ADD REASON. 
 * Date:     Oct 27, 2013 10:45:10 AM 
 * @author   huchan
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class CustomerPage extends PageObject {

	//define some web element object
	@FindBy(how = How.XPATH, using = "//*[@id='divSELECT_CUSTOMER']/h2")
	public WebElement customertitle;
	@FindBy(how = How.XPATH, using = "//*[@id='customerOrgSiteCountry']")
	public WebElement country;
	@FindBy(how = How.XPATH, using = "//*[@id='customerOrgSiteState']")
	public WebElement state;
	@FindBy(how = How.XPATH, using = "//*[@id='customerOrgSiteCity']")
	public WebElement city;
	@FindBy(how = How.XPATH, using = "//*[@id='searchString']")
	public WebElement containtext;

	@FindBy(how = How.XPATH, using = "//*[@id='main_column']/div[2]")
	public WebElement mainresult;

	@FindBy(how = How.XPATH, using = "//*[@id='gview_optionalCustomersGrid']/div[3]")
	public WebElement customerlist;
	
	// this is the FUT element here 
	@FindBy(how=How.XPATH,using="//*[@id='requestTabDiv']/ul/li[1]/a")
	public WebElement filtertab;
    @FindBy(how=How.XPATH,using="//*[@id='requestTabDiv']/ul/li[2]/a")
    public WebElement filetab;
    /*this is for the upload by file 
     * 20130911 Alter update for this feature
    */
    @FindBy(how=How.XPATH,using="//*[@id='multipartFileUpload']")
    public WebElement browserbtn;
    @FindBy(how=How.XPATH,using="//*[@id='btnUpload']")
    public WebElement uploadbtn;  
    @FindBy(how=How.XPATH,using="//*[@id='fileName']")
    public WebElement filename;    
    
	@FindBy(how = How.XPATH, using = "//*[@id='back']")
	public WebElement backbtn;
	@FindBy(how = How.XPATH, using = "//*[@id='next']")
	public WebElement nextbtn;
		
}

