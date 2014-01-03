package org.zkoss.zss.app.ui.toolbar;

import java.io.File;
import java.io.IOException;

import org.zkoss.zk.ui.WebApps;
import org.zkoss.zss.api.Importer;
import org.zkoss.zss.api.Importers;
import org.zkoss.zss.api.model.Book;
import org.zkoss.zss.api.model.Sheet;
import org.zkoss.zss.ui.UserActionContext;
import org.zkoss.zss.ui.UserActionHandler;

public class NewToolbar implements UserActionHandler {

	@Override
	public boolean isEnabled(Book book, Sheet sheet) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean process(UserActionContext context) {
		// TODO Auto-generated method stub
		 Importer importer = Importers.getImporter();
         
	        try {
	            Book loadedBook = importer.imports(new File(WebApps.getCurrent()
	                            .getRealPath("/WEB-INF/books/blank.xlsx")), "blank.xlsx");
	            context.getSpreadsheet().setBook(loadedBook);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return true;
	}

}
