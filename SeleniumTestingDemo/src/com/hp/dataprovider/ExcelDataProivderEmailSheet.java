package com.hp.dataprovider;

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

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.log4testng.Logger;

public class ExcelDataProivderEmailSheet {

	public static String sheetname = "Email_Settings";
	public static String excelpath = System.getProperty("user.dir")
			+ "\\resources\\TestData.xls";

	private static Logger logger = Logger
			.getLogger(ExcelDataProivderEmailSheet.class);

	// this is for sheet for the login sheet ,this is all the valid test case
	@DataProvider(name = "devEmail")
	public static Iterator<Object[]> getPostiveData(ITestContext result) {

		List<Object[]> rowdata = new ArrayList<Object[]>();
		List<String> header = null;

		logger.debug("Now we find the data driver file path ,the excel path is "
				+ excelpath);
		// logger.debug("current Excel Data path is :"+excelpath);
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

	@DataProvider(name = "proEmail")
	public static Iterator<Object[]> getExcelIteratorData(ITestContext result) {

		List<Object[]> rowdata = new ArrayList<Object[]>();
		List<String> header = null;

		String excelpath = System.getProperty("user.dir")
				+ "\\resources\\TestData.xls";
		logger.debug("Now we find the data driver file path ,the excel path is "
				+ excelpath);

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
				String firstcell = sheet.getCell(0, rowindex).getContents()
						.trim().toLowerCase();
				if (firstcell.equals("pro")) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("list is :"+rowdata);
		return rowdata.iterator();
	}
}
