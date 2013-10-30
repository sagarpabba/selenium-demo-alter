/**
 * Project Name:PAF_Testing_Core
 * File Name:SearchRunDetailPage.java
 * Package Name:com.hp.pageobject
 * Date:Oct 27, 201310:47:12 AM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.hp.po;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * ClassName:SearchRunDetailPage 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 TODO ADD REASON. 
 * Date:     Oct 27, 2013 10:47:12 AM 
 * @author   huchan
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class SearchRunDetailPage extends PageObject {

	
	@FindBy(how=How.XPATH,using="//*[@id='content']/div/p") 
	@CacheLookup
	public WebElement headtitle;
	
	@FindBy(how=How.XPATH,using="//*[@id='fackDownloadBtn']")
    @CacheLookup
    public WebElement downloadbtn;
	
	@FindBy(how=How.XPATH,using="//*[@id='backBtn']")
	@CacheLookup
	public WebElement backbtn;
	
	@FindBy(how=How.XPATH,using="//*[@id='cb_reportDownloadTable']")
	@CacheLookup
	public WebElement checkallbtn;
	
	@FindBy(how=How.XPATH,using="//*[@id='reportDownloadTable']")
	@CacheLookup
	public WebElement reporttable;
}

