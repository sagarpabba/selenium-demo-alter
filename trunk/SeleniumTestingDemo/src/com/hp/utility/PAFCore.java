package com.hp.utility;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PAFCore {

	// the page is loading data
	private static Logger logger = Logger.getLogger(PAFCore.class);

	public static void waitForPageLoad(WebDriver driver)
			throws Exception {
		// *[@id='hpit-busy']/img
		String imagepath = ".//*[@id='hpit-busy']/img";
		WebElement loadimg = driver.findElement(By.xpath(imagepath));
		while (loadimg.isDisplayed()) {
			logger.info("the load image displayed status is:"
					+ loadimg.isDisplayed());
			SeleniumCore.sleepSeconds(4);

		}
	}

}
