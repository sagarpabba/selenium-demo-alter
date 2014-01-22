package com.hp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;

import net.sf.json.JSONObject;

import com.hp.utility.SVNKitCore;
import com.hp.utility.TimeUtils;
import com.opensymphony.xwork2.ActionSupport;

public class SCMAction extends ActionSupport{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2062060379614946773L;

	private JSONObject listlogsObj=null;
	
	private String rows;//every page number  from easyui data

	private String page;//current page number from easyui data
    private String data;  //easyui from the front page get the data
    
	// list the action
	public String listSCMLogs(){
		
	    System.out.println("****************************************Source code management*******************************************");
	    System.out.println("Front json page number is :"+page);
	    System.out.println("Front json total number is :"+rows);
//	      
	    long numbers=SVNKitCore.getTotalRevisions();
	   // String parsenumber=""+numbers;
		//current page number 1,2,3,4
		int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //every page total number
        int number = Integer.parseInt((rows == null || rows == "0") ? "80":rows);  
     
        //every page start index  first is 1  second is number +1     
        long start =numbers+number-number*intPage;
        
		
		System.out.println("total revesion are:"+numbers);
		@SuppressWarnings("rawtypes")		
		Iterator logs=SVNKitCore.getSVNLogs(start,numbers-number+1);
		
		Map<String, Object> alllogs=new HashMap<String,Object>();
		List<Map<String, Object>> totalrecord =new ArrayList<Map<String, Object>>();
		
		while(logs.hasNext()){
			
			Map<String, Object> logmap=new HashMap<String, Object>();
			
			 SVNLogEntry logEntry = (SVNLogEntry) logs.next();
			 
			 String changedate=TimeUtils.toString(logEntry.getDate());
			 String revision=""+logEntry.getRevision();
			 String author=logEntry.getAuthor();
			 String comment=logEntry.getMessage();
			 
			 System.out.println("log date:"+changedate);
			 System.out.println("log revision:"+revision);
			 System.out.println("log author:"+author);
			 System.out.println("log comment:"+comment);
			 
			 String changedetail="Changed Paths:";
			 if (logEntry.getChangedPaths().size() > 0) {
				 
				     /*
	                 * keys are changed paths
	                 */
	                @SuppressWarnings("rawtypes")
					Set changedPathsSet = logEntry.getChangedPaths().keySet();

	                for (@SuppressWarnings("rawtypes")
					Iterator changedPaths = changedPathsSet.iterator(); changedPaths
	                        .hasNext();) {
	                	
			                	  /*
			                     * obtains a next SVNLogEntryPath
			                     */
			                    SVNLogEntryPath entryPath = (SVNLogEntryPath) logEntry
	                                                          .getChangedPaths().get(changedPaths.next());
			                    changedetail=changedetail
			                            + entryPath.getType()+" "
			                            + entryPath.getPath()
			                            + ((entryPath.getCopyPath() != null) ? " (from "
			                                    + entryPath.getCopyPath() + " revision "
			                                    + entryPath.getCopyRevision() + ")" : "")+"|";
	                   }
	               
			    }
			  System.out.println("change detail is :\n"+changedetail);
			  
			  logmap.put("change_date", changedate);
			  logmap.put("change_revision", revision);
			  logmap.put("change_author", author);
			  logmap.put("change_comment", comment);
			  logmap.put("change_detail", changedetail);
			
			  totalrecord.add(logmap);
			 
		}
		
		 alllogs.put("success", true);
		 alllogs.put("rows", totalrecord);
		 alllogs.put("total", numbers);
		 System.out.println("json object data is:"+alllogs);
		 listlogsObj=JSONObject.fromObject(alllogs);
		 System.out.println("json parse date to :"+listlogsObj);
		return SUCCESS;
	}
	
	
	/**
	 * @return the listlogsObj
	 */
	public JSONObject getListlogsObj() {
		return listlogsObj;
	}
	/**
	 * @param listlogsObj the listlogsObj to set
	 */
	public void setListlogsObj(JSONObject listlogsObj) {
		this.listlogsObj = listlogsObj;
	}


	/**
	 * @return the rows
	 */
	public String getRows() {
		return rows;
	}


	/**
	 * @param rows the rows to set
	 */
	public void setRows(String rows) {
		this.rows = rows;
	}


	/**
	 * @return the page
	 */
	public String getPage() {
		return page;
	}


	/**
	 * @param page the page to set
	 */
	public void setPage(String page) {
		this.page = page;
	}


	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}


	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

}
