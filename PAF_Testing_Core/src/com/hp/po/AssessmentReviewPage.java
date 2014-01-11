/**
 * Project Name:PAF_Testing_Core
 * File Name:AssessmentReviewPage.java
 * Package Name:com.hp.pageobject
 * Date:Oct 27, 201310:46:17 AM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.hp.po;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * ClassName:AssessmentReviewPage 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 TODO ADD REASON. 
 * Date:     Oct 27, 2013 10:46:17 AM 
 * @author   huchan

 * @since    JDK 1.6
 * @see 	 
 * @version $Revision: 1.0 $
 */
public class AssessmentReviewPage extends PageObject {

	//define some page elements in this page
	@FindBy(how = How.XPATH, using = "//*[@id='divREVIEW_REQUEST']/h2")
	public WebElement header;
}

