package com.selenium.po;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.selenium.utilities.BasePage;

/**
* @ClassName: GoogleSearchPage
* @Description: TODO
* @author alterhu2020@gmail.com
* @date Apr 9, 2014 5:58:03 PM
* 
*/

public class GoogleSearchPage extends BasePage {

	@FindBy(how = How.XPATH, using ="//*[@name=\"q\"]")
	public WebElement searchbox;
}
