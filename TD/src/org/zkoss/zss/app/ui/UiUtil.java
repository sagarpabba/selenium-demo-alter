/* 
	Purpose:
		
	Description:
		
	History:
		2013/7/10, Created by dennis

Copyright (C) 2013 Potix Corporation. All Rights Reserved.

*/
package org.zkoss.zss.app.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zkoss.lang.Library;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.http.DHtmlLayoutServlet;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ext.Selectable;
/**
 * 
 * @author dennis
 *
 */
public class UiUtil {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2532167889713365940L;
	public static HttpServletRequest request=null;
	public static Object getSingleSelection(Selectable selection){
		if(selection!=null && selection.getSelection().size()>0){
			return selection.getSelection().iterator().next();
		}
		return null;
	}
	
	public static void showInfoMessage(String message,long time) {
		Clients.showNotification(message,"info",null,null,2000,true);
	}
	public static void showInfoMessage(String message) {
		showInfoMessage(message,2000);
	}
	
	public static void showWarnMessage(String message,long time) {
		Clients.showNotification(message,"warn",null,null,2000,true);
	}
	public static void showWarnMessage(String message) {
		showWarnMessage(message, 2000);
	}

	public static boolean isRepositoryReadonly(){
	    
	   //   String permission=request.getParameter("id");
	   // System.out.println("get the permission is :"+permission);
	
	   String readonly =Library.getProperty("zssapp.bookrepostory.readonly","falses").toLowerCase();
	   System.out.println("the  read only is :"+readonly);
		
		return "true".equals(readonly);
	}
}
