/**
 * Project Name:PAF_Testing_Core
 * File Name:HomePage.java
 * Package Name:com.hp.pageobject
 * Date:Oct 27, 201310:38:34 AM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.hp.po;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * ClassName:HomePage 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 TODO ADD REASON. 
 * Date:     Oct 27, 2013 10:38:34 AM 
 * @author   huchan

 * @since    JDK 1.6
 * @see 	 
 * @version $Revision: 1.0 $
 */
public class HomePage extends PageObject {

	//define some webelement object in this page
	@FindBy(how = How.XPATH, using = "//*[@id='createRequest']")
	public WebElement newassessmentrequest;
	@FindBy(how = How.XPATH, using = "//*[@id='listRuns']/b")
	public WebElement listrun;
	@FindBy(how = How.XPATH, using = "//*[@id='listSubscriptions']")
	public WebElement listsubscription;
	@FindBy(how = How.XPATH, using = "//*[@id='feedback']")
	public WebElement feekback;

	@FindBy(how = How.XPATH, using = "//*[@id='hpit-topUser']")
	public WebElement loginuser;

	@FindBy(how = How.XPATH, using = "//*[@id='hpit-btmList1'][2]")
	public WebElement buildnumbertag;	
	
	@FindBy(how=How.XPATH,using="//*[@id='content']/div/div")
	public WebElement pafwarningnote;
	
}

