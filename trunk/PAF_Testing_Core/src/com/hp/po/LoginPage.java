/**
 * Project Name:PAF_Testing_Core
 * File Name:LoginPage.java
 * Package Name:com.hp.pageobject
 * Date:Oct 27, 201310:30:02 AM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.hp.po;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * ClassName:LoginPage 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 TODO ADD REASON. 
 * Date:     Oct 27, 2013 10:30:02 AM 
 * @author   huchan

 * @since    JDK 1.6
 * @see 	 
 * @version $Revision: 1.0 $
 */
public class LoginPage extends PageObject {

	//define some page elements in this page

	@FindBy(how = How.XPATH, using = "//*[@name='USER']")
	public WebElement emailinput;
	@FindBy(how = How.XPATH, using = "//*[@name='PASSWORD']")
	public WebElement password;
	@FindBy(how = How.XPATH, using = "//*[@id='Login']/div[2]/input")
	public WebElement submitbtn;
	@FindAll({ @FindBy(className = "ft-text") })
	public List<WebElement> content;
	
}

