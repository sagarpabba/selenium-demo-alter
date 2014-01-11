/**
 * Project Name:PAF_HC
 * File Name:ExcelUtils.java
 * Package Name:com.hp.utility
 * Date:Aug 24, 20132:03:52 PM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.hp.utility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

/**
 * ClassName:ExcelUtils 

 * Reason:	 TODO ADD REASON. 
 * Date:     Aug 24, 2013 2:03:52 PM 
 * @author   huchan

 * @since    JDK 1.6
 * @see 	 
 * @version $Revision: 1.0 $
 */
public class ExcelUtils {

	private static Logger logger=Logger.getLogger(ExcelUtils.class);
	private static String excelpath=SeleniumCore.getProjectWorkspace()+"resources"+File.separator+"TestData.xls";
	
	private static String emailsheetname="Email_Settings";
	
	/**
	 * Method getPostiveData.
	 * @param result ITestContext
	 * @param sheetname String
	 * @return Iterator<Object[]>
	 */
	@DataProvider(name = "getPostiveLine")
	public static Iterator<Object[]> getPostiveData(ITestContext result,String sheetname) {

		List<Object[]> rowdata = new ArrayList<Object[]>();
		List<String> header = null;

		logger.debug("Now we find the data driver file path ,the excel path is "+ excelpath);
		
		try {
			Workbook workbook = Workbook.getWorkbook(new File(excelpath));
			Sheet sheet = workbook.getSheet(sheetname);
			int rows = sheet.getRows();
			int columns = sheet.getColumns();
			header = new ArrayList<String>();
			for (int columnindex = 0; columnindex < columns; columnindex++) {
				String headerelement = sheet.getCell(columnindex, 0)
						.getContents().trim();
				header.add(columnindex, headerelement);
			}
			logger.debug("Excel sheet header is :" + header);
			for (int rowindex = 0; rowindex < rows; rowindex++) {
				Map<String, String> rowmap = new HashMap<String, String>();
				// if the content is empty ,skip this row
				String firstcell = sheet.getCell(0, rowindex).getContents()
						.trim().toLowerCase();

				if (firstcell.equals("yes")) {
					for (int columnindex = 0; columnindex < columns; columnindex++) {
						String cellcontent = sheet
								.getCell(columnindex, rowindex).getContents()
								.trim();
						String eachheader = header.get(columnindex);
						// System.out.println("");
						rowmap.put(eachheader, cellcontent);
					}
					logger.debug("current Row data is  :" + rowmap);
					rowdata.add(new Object[] { rowmap });
				}

			}
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// workbook.
		// System.out.println("list is :"+rowdata);
		return rowdata.iterator();
	}

	/**
	 * Method getExcelIteratorData.
	 * @param result ITestContext
	 * @param sheetname String
	 * @return Iterator<Object[]>
	 */
	@DataProvider(name = "getAll")
	public static Iterator<Object[]> getExcelIteratorData(ITestContext result,String sheetname) {

		List<Object[]> rowdata = new ArrayList<Object[]>();
		List<String> header = null;

		logger.debug("Now we find the data driver file path ,the excel path is "+ excelpath);

		try {
			Workbook workbook = Workbook.getWorkbook(new File(excelpath));
			Sheet sheet = workbook.getSheet(sheetname);
			int rows = sheet.getRows();
			int columns = sheet.getColumns();
			header = new ArrayList<String>();
			for (int columnindex = 0; columnindex < columns; columnindex++) {
				String headerelement = sheet.getCell(columnindex, 0)
						.getContents().trim();
				header.add(columnindex, headerelement);
			}
			logger.debug("Current excel header is :" + header);
			for (int rowindex = 1; rowindex < rows; rowindex++) {
				Map<String, String> rowmap = new HashMap<String, String>();
				// if the content is empty ,skip this row
				String firstcell = sheet.getCell(1, rowindex).getContents();
				if (!(firstcell.isEmpty())) {
					for (int columnindex = 0; columnindex < columns; columnindex++) {
						String cellcontent = sheet
								.getCell(columnindex, rowindex).getContents()
								.trim();
						String eachheader = header.get(columnindex);
						// System.out.println("");
						rowmap.put(eachheader, cellcontent);
					}
					logger.debug("current Row data is :" + rowmap);
					rowdata.add(new Object[] { rowmap });
				}

			}
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("list is :"+rowdata);
		return rowdata.iterator();
	}

	/**
	 * Method getSpecifySheet.
	 * @param excelpath String
	 * @param sheetname String
	 * @param casetype String
	 * @return Map<String,String>
	 */
	public static Map<String, String> getSpecifySheet(String excelpath,String sheetname,String casetype) {

		// List<Object[]> rowdata=new ArrayList<Object[]>();
		List<String> header = null;

		Map<String, String> rowmap = new HashMap<String, String>();
		// String excelpath="";
		boolean findrow = false;
		int rownumber = 0;
		try {
			Workbook workbook = Workbook.getWorkbook(new File(excelpath));
			Sheet sheet = workbook.getSheet(sheetname);
			int rows = sheet.getRows();
			int columns = sheet.getColumns();
			header = new ArrayList<String>();
			for (int columnindex = 0; columnindex < columns; columnindex++) {
				String headerelement = sheet.getCell(columnindex, 0)
						.getContents().trim();
				header.add(columnindex, headerelement);
			}
			logger.debug("Current excel header is :" + header);
			for (int rowindex = 1; rowindex < rows; rowindex++) {
				// get the column 1 to get the host name,the column index begin with 0
				String cellcontent = sheet.getCell(0, rowindex).getContents()
						.toLowerCase().trim();
				logger.info("found the first column content in excel is:"+cellcontent);
				// System.out.println("content is :"+cellcontent+",host name is :"+hostname2);
				// System.out.println("compare values:"+(cellcontent==hostname2));
				if (cellcontent.equalsIgnoreCase(casetype)) {
					logger.debug("Found the correct cell data,the case type we found in excel is:"+cellcontent);
					findrow = true;
					rownumber = rowindex;
					break;
				} else {
					findrow = false;
					logger.debug("sorry we had not found this case  in the spreadsheet");
				}

				logger.debug("current Row data is :" + rowmap);
			}
			// put the map value for the host name row
			if (findrow) {
				for (int columnindex = 0; columnindex < columns; columnindex++) {
					String findcontent = sheet.getCell(columnindex, rownumber)
							.getContents().trim();
					String mapheader = header.get(columnindex);
					rowmap.put(mapheader, findcontent);
				}
			}

		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rowmap;
	}
	
	
	
	/**
	 * this function is for get the excel data for email settings
	 * @param result
	
	 * @return Iterator<Object[]>
	 */
	@DataProvider(name = "devEmail")
	public static Iterator<Object[]> getPostiveData(ITestContext result) {

		List<Object[]> rowdata = new ArrayList<Object[]>();
		List<String> header = null;

		logger.debug("Now we find the data driver file path ,the excel path is "
				+ excelpath);
		// logger.debug("current Excel Data path is :"+excelpath);
		try {
			Workbook workbook = Workbook.getWorkbook(new File(excelpath));
			Sheet sheet = workbook.getSheet(emailsheetname);
			int rows = sheet.getRows();
			int columns = sheet.getColumns();
			header = new ArrayList<String>();
			for (int columnindex = 0; columnindex < columns; columnindex++) {
				String headerelement = sheet.getCell(columnindex, 0)
						.getContents().trim();
				header.add(columnindex, headerelement);
			}
			logger.debug("Excel sheet header is :" + header);
			for (int rowindex = 0; rowindex < rows; rowindex++) {
				Map<String, String> rowmap = new HashMap<String, String>();
				// if the content is empty ,skip this row
				String firstcell = sheet.getCell(0, rowindex).getContents()
						.trim().toLowerCase();

				if (firstcell.equals("dev")) {
					for (int columnindex = 0; columnindex < columns; columnindex++) {
						String cellcontent = sheet
								.getCell(columnindex, rowindex).getContents()
								.trim();
						String eachheader = header.get(columnindex);
						// System.out.println("");
						rowmap.put(eachheader, cellcontent);
					}
					logger.debug("current Row data is  :" + rowmap);
					rowdata.add(new Object[] { rowmap });
				}

			}
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// workbook.
		// System.out.println("list is :"+rowdata);
		return rowdata.iterator();
	}
}

