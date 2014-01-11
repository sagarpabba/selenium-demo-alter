/**
 * Project Name:PAF_Testing_Core
 * File Name:OptionsPage.java
 * Package Name:com.hp.pageobject
 * Date:Oct 27, 201310:45:57 AM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.hp.po;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * ClassName:OptionsPage 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 TODO ADD REASON. 
 * Date:     Oct 27, 2013 10:45:57 AM 
 * @author   huchan

 * @since    JDK 1.6
 * @see 	 
 * @version $Revision: 1.0 $
 */
public class OptionsPage extends PageObject {

	//define some page object elements in this page
	@FindBy(how = How.XPATH, using = "//*[@id='emailRequestor']")
	public WebElement mailname;
	
	@FindBy(how=How.XPATH,using="//*[@id='language']")
	@CacheLookup
	public WebElement languages;

	@FindBy(how = How.XPATH, using = "//*[@id='saveSubscription']")
	public WebElement savesubscription;

	@FindBy(how = How.XPATH, using = "//*[@id='back']")
	public WebElement backbtn;
	@FindBy(how = How.XPATH, using = "//*[@id='next']")
	public WebElement nextbtn;
}

