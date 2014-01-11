/**
 * Project Name:PAF_Testing_Core
 * File Name:DevicePage.java
 * Package Name:com.hp.pageobject
 * Date:Oct 27, 201310:45:28 AM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.hp.po;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * ClassName:DevicePage 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 TODO ADD REASON. 
 * Date:     Oct 27, 2013 10:45:28 AM 
 * @author   huchan

 * @since    JDK 1.6
 * @see 	 
 * @version $Revision: 1.0 $
 */
public class DevicePage extends PageObject {

	//define some page object elements

	@FindBy(how = How.XPATH, using = "//*[@id='divSELECT_DEVICES']/h2")
	public WebElement devicehead;
	@FindBy(how = How.XPATH, using = "//*[@id='gview_devicesTable']/div[3]")
	public WebElement devicetable;
	
	@FindBy(how=How.XPATH,using="//*[@id='cb_devicesTable']")
	public WebElement checkallbox;
	
	@FindBy(how=How.XPATH,using="//*[@id='devicesTable']/*/tr[2]/td/b")
    public WebElement customerline;

	@FindBy(how = How.XPATH, using = "//*[@id='back']")
	public WebElement backbtn;
	@FindBy(how = How.XPATH, using = "//*[@id='next']")
	public WebElement nextbtn;
}

