package com.hp.pop;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.hp.utility.SeleniumCore;

public class Launch_Assessment_Page {

	private static Logger logger = Logger
			.getLogger(Launch_Assessment_Page.class);
	private WebDriver driver;

	@FindBy(how = How.XPATH, using = ".//*[@id='analysisRequestTypeCode']/option[5]")
	private WebElement fwsw;
	@FindBy(how = How.XPATH, using = ".//*[@id='analysisRequestTypeCode']/option[6]")
	private WebElement proactivescan;
	@FindBy(how = How.XPATH, using = ".//*[@id='analysisRequestTypeCode']/option[7]")
	private WebElement shc;

	@FindBy(how = How.XPATH, using = ".//*[@id='create']")
	private WebElement createbtn;

	public Launch_Assessment_Page(WebDriver driver) {
		this.driver = driver;
	}

	public void verifyPageElements() {
		boolean isfwsw = SeleniumCore.isDisplayed(fwsw);
		boolean isps = SeleniumCore.isDisplayed(proactivescan);
		boolean isshc = SeleniumCore.isDisplayed(shc);
		boolean iscreatedbtn = SeleniumCore.isDisplayed(createbtn);
		String pagename=this.getClass().getName();
		logger.info("\n***************************************"+pagename+"****************************************************");

		if (isfwsw && isps && isshc && iscreatedbtn) {
			logger.info("Lanuch assessment page .we had found these objects with text:fw&sw "
					+ fwsw.getText()
					+ ",proactivescan "
					+ proactivescan.getText()
					+ ",system health check "
					+ shc.getText());
		} else {
			Assert.fail();
			logger.info("Launch assessment page .the test is failed as we cannot locate the elements in the page ");
		}
	}

	public Select_Customer_Page newFWSW() {
		SeleniumCore.clickElement(fwsw);
		logger.info("Launch Assessment page new a FW&SW assessment .the clicked webelement is:"
				+ SeleniumCore.getElementText(fwsw));
		WebElement quotenode = driver.findElement(By
				.xpath(".//*[@id='PRO_RVIS']/fieldset/blockquote/span"));
		String expectedtext = "This Assessment reviews the products under the Proactive Care contract to verify "
				+ "that they are at the recommended revision levels. Based on this, HP provides recommendations "
				+ "as to applicable software versions, patches, and firmware revisions.";
		String runtext = SeleniumCore.getElementText(quotenode);
		Assert.assertEquals(runtext, expectedtext);
		SeleniumCore.clickElement(createbtn);
		return PageFactory.initElements(driver, Select_Customer_Page.class);
	}

	public Select_Customer_Page newPS() {

		SeleniumCore.clickElement(proactivescan);
		String pctext = SeleniumCore.getElementText(proactivescan);
		logger.info("Launch assessment page ,new a proactive scan Assessment.the clicked webelement is:"
				+ pctext);
		WebElement quotenode = driver.findElement(By
				.xpath(".//*[@id='PRO_SCAN']/fieldset/blockquote/span"));
		String expectedtext = "This Assessment identifies at a high level the security, performance, configuration,"
				+ " and availability problems of Customers environment before they impact Customers business operations.";
		String runtext = SeleniumCore.getElementText(quotenode);
		Assert.assertEquals(runtext, expectedtext);
		SeleniumCore.clickElement(createbtn);
		return PageFactory.initElements(driver, Select_Customer_Page.class);
	}

	public Select_Customer_Page newshc() {

		SeleniumCore.clickElement(shc);
		String shctext = SeleniumCore.getElementText(shc);
		logger.info("Launch assessment page,new a System health check assessment.the clicked webelement is:"
				+ shctext);
		WebElement quotenode = driver.findElement(By
				.xpath(".//*[@id='SHC']/fieldset/blockquote/span"));
		String expectedtext = "This Assessment identifies at a detailed level the security, performance, "
				+ "configuration, and availability problems of Customers environment and assesses the overall "
				+ "health of that environment (PAC/Server advanced collections only).";
		String runtext = SeleniumCore.getElementText(quotenode);
		Assert.assertEquals(runtext, expectedtext);
		SeleniumCore.clickElement(createbtn);
		return PageFactory.initElements(driver, Select_Customer_Page.class);
	}

}
