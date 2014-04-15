package com.selenium.action;

import com.selenium.po.GoogleSearchPage;

/**
* @ClassName: GoogleSearchAction
* @Description: TODO
* @author alterhu2020@gmail.com
* @date Apr 9, 2014 5:58:08 PM
* 
*/

public class GoogleSearchAction extends GoogleSearchPage {


	public void typeAndSearchResult(){
	      clearAndTypeString(searchbox, "abcddd");
	      reporterNewStep("type name and passsord alter latest", "testing", "Failed", "failed comments");
	}
}
