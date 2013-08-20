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

public class ExcelDataProivderDeviceSheet {

	public static String sheetname = "Device_Detail";
	public static String excelpath = System.getProperty("user.dir")
			+ "\\resources\\TestData.xls";

	private static Logger logger = Logger
			.getLogger(ExcelDataProivderDeviceSheet.class);

	// this is for sheet for the login sheet ,this is all the valid test case
	@DataProvider(name = "getPostiveLine")
	public static Iterator<Object[]> getPostiveData(ITestContext result) {

		List<Object[]> rowdata = new ArrayList<Object[]>();
		List<String> header = null;

		logger.debug("Now we find the data driver file path ,the excel path is "
				+ excelpath);
		// 'logger.debug("current Excel Data path is :"+excelpath);
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

	@DataProvider(name = "getAll")
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("list is :"+rowdata);
		return rowdata.iterator();
	}

	public static Map<String, String> getSpecifySheet(String excelpath,
			String hostname) {

		// List<Object[]> rowdata=new ArrayList<Object[]>();
		List<String> header = null;

		Map<String, String> rowmap = new HashMap<String, String>();
		;
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
				// get the column 2 to get the host name ,the index begin from 0
				String cellcontent = sheet.getCell(1, rowindex).getContents()
						.toLowerCase().trim();
				String lasthostname = hostname.toLowerCase().trim();
				// System.out.println("content is :"+cellcontent+",host name is :"+hostname2);
				// System.out.println("compare values:"+(cellcontent==hostname2));
				if (cellcontent.equals(lasthostname)) {
					findrow = true;
					rownumber = rowindex;
					break;
				} else {
					findrow = false;
					logger.debug("sorry we had not found this host in the spreadsheet");
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
		} catch (NullPointerException e) {
			logger.error("cannot import the data from the excel ,maybe the sheet name you input is wrong .");
		}

		return rowmap;
	}
}
