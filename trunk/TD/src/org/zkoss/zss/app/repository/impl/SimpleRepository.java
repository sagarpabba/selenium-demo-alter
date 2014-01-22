/* 
	Purpose:
		
	Description:
		
	History:
		2013/7/10, Created by dennis

Copyright (C) 2013 Potix Corporation. All Rights Reserved.

*/
package org.zkoss.zss.app.repository.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.utils.SVNKitCore;
import org.zkoss.zk.ui.WebApps;
import org.zkoss.zss.api.Exporters;
import org.zkoss.zss.api.Importers;
import org.zkoss.zss.api.model.Book;
import org.zkoss.zss.app.repository.BookInfo;
import org.zkoss.zss.app.repository.BookRepository;
import org.zkoss.zss.app.ui.UiUtil;
/**
 * 
 * @author dennis
 *
 */
public class SimpleRepository implements BookRepository{
	File root;
	public SimpleRepository(File root){
		this.root = root;
	}
	
	
	@Override
	public synchronized List<BookInfo> list() {
		List<BookInfo> books = new ArrayList<BookInfo>();
		for(File f:root.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				if(file.isFile() && !file.isHidden()){
					String ext = FileUtil.getNameExtension(file.getName()).toLowerCase();
					if("xls".equals(ext) || "xlsx".equals(ext)){
						return true;
					}
				}
				return false;
			}
		})){
			books.add(new SimpleBookInfo(f,f.getName(),new Date(f.lastModified())));
		}
		return books;
	}

	@Override
	public synchronized Book load(BookInfo info) throws IOException {
		Book book = Importers.getImporter().imports(((SimpleBookInfo)info).getFile(), info.getName());
		return book;
	}
   /*
    * (non-Javadoc):Save the current document
    * @see org.zkoss.zss.app.repository.BookRepository#save(org.zkoss.zss.app.repository.BookInfo, org.zkoss.zss.api.model.Book)
    */
	@Override
	public synchronized BookInfo save(BookInfo info, Book book) throws IOException {
		if(UiUtil.isRepositoryReadonly()){
			return null;
		}
		FileOutputStream fos = null;
		try{
			File f = ((SimpleBookInfo)info).getFile();
			//write to temp file first to avoid write error damage original file 
			File temp = File.createTempFile("temp", f.getName());
			fos = new FileOutputStream(temp);
			Exporters.getExporter().export(book, fos);
			//backup the file 20131230 Alter
			String backup_filepath=f.getParent();
			String backup_filename="backup_"+f.getName()+"_"+new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime())+".xls";
			File backupfile=new File(backup_filepath+File.separator+backup_filename);
			fos=new FileOutputStream(backupfile);
			Exporters.getExporter().export(book, fos);	
			//check in the code into SVN
			SVNKitCore.checkinOneFile(f.getAbsolutePath(), "Commit TestData.xls file from the web page");		
			fos.close();
			fos = null;
			
			FileUtil.copy(temp,f);
			temp.delete();
			
		}finally{
			if(fos!=null)
				fos.close();
		}
		return info;
	}
	
	@Override
	public synchronized BookInfo saveAs(String bookname,Book book) throws IOException {
		if(UiUtil.isRepositoryReadonly()){
			return null;
		}
		String name = FileUtil.getName(bookname);
		String ext = "";
		switch(book.getType()){
		case EXCEL_2003:
			ext = ".xls";
			break;
		case EXCEL_2007:
			ext = ".xlsx";
			break;
		default:
			throw new RuntimeException("unknow book type");
		}
		File f = new File(root,name+ext);
		int c = 0;
		if(f.exists()){
			f = new File(root,name+"("+(++c)+")"+ext);
		}
		SimpleBookInfo info = new SimpleBookInfo(f,f.getName(),new Date());
		return save(info,book);
	}


	@Override
	public boolean delete(BookInfo info) throws IOException {
		if(UiUtil.isRepositoryReadonly()){
			return false;
		}
		File f = ((SimpleBookInfo)info).getFile();
		if(!f.exists()){
			return false;
		}
		return f.delete();
	}
}
