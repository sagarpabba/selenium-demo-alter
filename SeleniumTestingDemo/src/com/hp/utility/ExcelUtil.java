package com.hp.utility;

import java.io.File;
import java.util.Iterator;

import org.apache.log4j.Logger;

//import com.google.common.collect.Table.Cell;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ExcelUtil implements Iterator<Object[]> {
	private static final Logger log=Logger.getLogger(ExcelUtil.class);
	private Workbook workbook = null;
	private Sheet sheet = null;
	private Cell cell=null;
	private int rowNum = 0;
	private int curRowNo = 0;
	
	public ExcelUtil(String excelname,String sheetname){
		  try {
		   File directory = new File(".");
		   this.workbook= Workbook.getWorkbook(new File(directory.getCanonicalPath() + "\\resources\\" + 
				   excelname.replaceAll("\\.", "/")+".xls"));
		   this.sheet=workbook.getSheet(sheetname);
		   this.rowNum =sheet.getRows();   		   
		  } catch (Exception e) {
		   log.error("met the exception:"+e.getMessage());
		  } 
	}
	private String[][] getExcelData(String xlPath, String shtName, String tbName) throws Exception{
		String[][] tabArray=null;
		workbook = Workbook.getWorkbook(new File(xlPath));
		sheet = workbook.getSheet(shtName);
		int sRow,sCol, eRow, eCol,ci,cj;
		cell=sheet.findCell(tbName);
		sRow=cell.getRow();
		sCol=cell.getColumn();
		Cell tableEnd= sheet.findCell(tbName, sCol+1,sRow+1, 100, 64000, false);
		eRow=tableEnd.getRow();
		eCol=tableEnd.getColumn();
		log.info("startRow="+sRow+", endRow="+eRow+", " + "startCol="+sCol+", endCol="+eCol);
		tabArray=new String[eRow-sRow-1][eCol-sCol-1];
		ci=0;
		for (int i=sRow+1;i<eRow;i++,ci++)
		{
		    cj=0;
		    for (int j=sCol+1;j<eCol;j++,cj++)
		    {
		         tabArray[ci][cj]=sheet.getCell(j,i).getContents();
	        }
	     }
		return tabArray;
	 }

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		if (this.rowNum==0 || this.curRowNo>=this.rowNum){
			   try {
			    workbook.close();
			   } catch (Exception e) {
			    e.printStackTrace();
			   } 
			   return false;
	      }
		 else
			  {
			   return true;
			  }
	}

	@Override
	public Object[] next() {
		// TODO Auto-generated method stub
		  Cell [] c = sheet.getRow(curRowNo);
		  String [] s  = new String[c.length];
		  for(int i=0;i< c.length;i++)
		   s[i] = c[i].getContents();
		  Object r[]=new Object[1];
		  r[0]=s;
		  this.curRowNo++;
		  return r;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}
}
