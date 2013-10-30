/**
 * Project Name:PAF_Testing_Core
 * File Name:LaunchAssessmentPage.java
 * Package Name:com.hp.pageobject
 * Date:Oct 27, 201310:41:56 AM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.hp.po;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * ClassName:LaunchAssessmentPage 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 TODO ADD REASON. 
 * Date:     Oct 27, 2013 10:41:56 AM 
 * @author   huchan
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class LaunchAssessmentPage extends PageObject{

	
	@FindBy(how = How.XPATH, using = "//*[@id='analysisRequestTypeCode']")
	public WebElement options;

	@FindBy(how = How.XPATH, using = "//*[@id='create']")
	public WebElement createbtn;
	
}

