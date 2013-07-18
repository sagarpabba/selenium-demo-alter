package com.hp.utility;

import java.util.Map;

import org.apache.log4j.Logger;
import org.seleniumhq.jetty7.util.log.Log;

import com.hp.dataproviders.ExcelDataProivderLoginSheet;


public class UtilDemo {

	
	private static Logger logger=Logger.getLogger(UtilDemo.class);
	
	public static void main(String[] args) {
		
	//	logger.debug(HostUtil.getHostIP());
		logger.debug(HostUtil.getFQDN());
		
		Map<String,String> mapdata=ExcelDataProivderLoginSheet.getSpecifySheet(HostUtil.getFQDN(),"C:\\Python27\\workspace\\SeleniumTestingDemo\\resources\\TestData.xls");
	    String item=mapdata.get("URL");
		logger.debug(item);
	}
}
