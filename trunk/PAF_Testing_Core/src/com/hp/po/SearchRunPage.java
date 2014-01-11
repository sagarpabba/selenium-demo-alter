/**
 * Project Name:PAF_Testing_Core
 * File Name:SearchRunPage.java
 * Package Name:com.hp.pageobject
 * Date:Oct 27, 201310:48:06 AM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.hp.po;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * ClassName:SearchRunPage 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 TODO ADD REASON. 
 * Date:     Oct 27, 2013 10:48:06 AM 
 * @author   huchan

 * @since    JDK 1.6
 * @see 	 
 * @version $Revision: 1.0 $
 */
public class SearchRunPage extends PageObject {

	
	@CacheLookup
	@FindBy(how = How.XPATH, using = "//*[@id='content']/div/h2")
	
	public WebElement header;
	@CacheLookup
	@FindBy(how = How.XPATH, using = "//*[@id='analysisTypeCode']")
	public WebElement assessmenttypelist;
	@FindBy(how = How.XPATH, using = "//*[@id='isCompleted']")
	public WebElement iscompletedcheckbox;
	@CacheLookup
	@FindBy(how = How.XPATH, using = "//*[@id='isStarted']")
	
	public WebElement isstartedcheckbox;
	@CacheLookup
	@FindBy(how = How.XPATH, using = "//*[@id='isFailed']")
	public WebElement isfailedcheckbox;
	@CacheLookup
	@FindBy(how = How.XPATH, using = "//*[@id='isPending']")
	public WebElement ispendingcheckbox;
	@CacheLookup
	@FindBy(how = How.XPATH, using = "//*[@id='fromDtOfStartTime']")
	public WebElement runstartdate;
	@CacheLookup
	@FindBy(how = How.XPATH, using = "//*[@id='toDtOfStartTime']")
	public WebElement runenddate;
	@CacheLookup
	@FindBy(how = How.XPATH, using = "//*[@id='customerPattern']")
	public WebElement customername;
	@CacheLookup
	@FindBy(how = How.XPATH, using = "//*[@id='emailAddress']")
	public WebElement emailaddress;
	@CacheLookup
	@FindBy(how = How.XPATH, using = "//*[@id='Search']")
	public WebElement searchbtn;
	@CacheLookup
	@FindBy(how = How.XPATH, using = "//*[@id='Search']/following-sibling::input[1]")
	public WebElement resetbtn;
	
	@CacheLookup
	@FindBy(how = How.XPATH, using = "//*[@id='reportList']")
	public WebElement tablelist;
}

