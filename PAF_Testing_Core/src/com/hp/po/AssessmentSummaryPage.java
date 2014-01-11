/**
 * Project Name:PAF_Testing_Core
 * File Name:AssessmentSummaryPage.java
 * Package Name:com.hp.pageobject
 * Date:Oct 27, 201310:46:50 AM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.hp.po;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * ClassName:AssessmentSummaryPage 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 TODO ADD REASON. 
 * Date:     Oct 27, 2013 10:46:50 AM 
 * @author   huchan

 * @since    JDK 1.6
 * @see 	 
 * @version $Revision: 1.0 $
 */
public class AssessmentSummaryPage extends PageObject {

	//define some object elements in this page

	@FindBy(how = How.XPATH, using = "//*[@id='main_column']/div[1]/div/div[2]/ul/li[1]")
	public WebElement runid;
	@FindBy(how = How.XPATH, using = "//*[@id='main_column']/div[1]/div/div[2]/ul/li[2]")
	public WebElement requesttype;
	@FindBy(how = How.XPATH, using = "//*[@id='main_column']/div[1]/div/div[2]/ul/li[3]")
	public WebElement customername;
	@FindBy(how = How.XPATH, using = "//*[@id='main_column']/div[1]/div/div[2]/ul/li[4]")
	public WebElement address;
	@FindBy(how = How.XPATH, using = "//*[@id='main_column']/div[1]/div/div[2]/ul/li[5]")
	public WebElement languages;
	@FindBy(how = How.XPATH, using = "//*[@id='main_column']/div[1]/div/div[2]/ul/li[6]")
	public WebElement devicenumbers;

	@FindBy(how = How.XPATH, using = "//*[@id='main_column']/div[2]/div/div")
	public WebElement notes;
}

