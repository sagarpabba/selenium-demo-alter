package com.hp.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

















import net.sf.json.JSONObject;

import com.hp.model.Builds;
import com.hp.utility.DBUtils;
import com.hp.utility.TimeUtils;
import com.opensymphony.xwork2.ActionSupport;

public class BuildAction extends ActionSupport {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2241993631556593099L;

	/**
	 * 
	 */
	private String rows;//every page number  from easyui data

	private String page;//current page number from easyui data
    private String data;  //easyui from the front page get the data
    private String sort;   //the front page sort field
    private String order;    //desc or asc
	public JSONObject latestBuildObj=null;
	
	
	private JSONObject newBuildObj=null;
	
	private JSONObject deleteBuildObj=null;
	
	private JSONObject searchBuildObj=null;
	private Builds build;
	
	//database object
	private Connection con=null;
    private PreparedStatement ps=null;
		
	private ResultSet rs=null;
	private int totalrows=0;
	
	//jdbc database object
	String sql="select * from BUILD_SUMMARY t where t.BUILD_STATUS!='invalid' order by t.START_TIME desc LIMIT ?,?";
	DBUtils db=new DBUtils();
	//new build execution sql
	String checkidsql="select count(*) as numbers from build_summary bs WHERE bs.BUILD_ID=?";
	String updateidsql="update build_summary bs set bs.BUILD_NAME=?,bs.BUILD_STATUS=?,"
			           + "bs.BUILD_TYPE=?,bs.BUILD_COMMENT=?,bs.START_TIME=?,bs.END_TIME=? where bs.BUILD_ID=?";
	//String newidsql="insert INTO build_summary (BUILD_ID,BUILD_TYPE,BUILD_NAME,BUILD_STATUS,BUILD_COMMENT,START_TIME,END_TIME) "
	//		       + "values(?,?,?,?,?,?,?)";
	String deletesql="delete from build_summary where BUILD_ID=?";
	
	
	public String latestBuild()
	{
		System.out.println("**********build list***************************************************************");
	//	System.out.println("search user name is :"+search.getUser());
		System.out.println("Front json page number is :"+page);
	    System.out.println("Front json total number is :"+rows);
	    System.out.println("sort field is:"+sort);
	    System.out.println("sort is:"+order);
//	      
	    
		//current page number 1,2,3,4
		int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //every page total number
        int number = Integer.parseInt((rows == null || rows == "0") ? "20":rows);  
     
        //every page start index  first is 1  second is number +1     
        int start = (intPage-1)*number;
//        int end=start+number-1;
        
		Map<String, Object> totaldata=new HashMap<String,Object>();
		List<Map<String, Object>> totalrecord =new ArrayList<Map<String, Object>>();
		
		
	   	System.out.print("Page start:"+start);
	   	System.out.println("Page numbers:"+number);
		
		try{
				
		       con=db.getConnection();
		       ps=db.getPreparedStatement(con, sql);
               System.out.println("Get result from :"+start+",select number are :"+number);
		       ps.setInt(1, start);
		       ps.setInt(2, number);
		       rs=ps.executeQuery();
				while(rs.next())
				{
					String starttime,endtime;
				//	int likenumber,dislikenumber;
					Map<String, Object> buildmap=new HashMap<String, Object>();
					buildmap.put("build_id",rs.getInt("BUILD_ID"));
					System.out.println("build id is:"+rs.getInt("build_id"));
					buildmap.put("build_status", rs.getString("BUILD_STATUS"));
					System.out.println("build id is:"+rs.getString("build_status"));
					buildmap.put("build_type",rs.getString("BUILD_TYPE"));
					buildmap.put("build_name", rs.getString("BUILD_NAME"));
					buildmap.put("build_comment", rs.getString("BUILD_COMMENT"));
					if(rs.getString("START_TIME")==null){
						starttime="";
					}else{
						starttime=rs.getString("START_TIME");

					}
					if(rs.getString("END_TIME")==null){
						endtime="";
					}else{
						endtime=rs.getString("END_TIME");
					}
					
				    buildmap.put("build_starttime",starttime);
				    buildmap.put("build_endtime",endtime);
				    System.out.println("build id is:"+rs.getString("end_time"));
				    buildmap.put("likenumber",rs.getInt("LIKE"));
				    System.out.println("like number is :"+rs.getInt("like"));
				    buildmap.put("dislikenumber",rs.getInt("DISLIKE"));
					
				    totalrecord.add(buildmap);
//				    System.out.println("totalrecord:"+totalrecord.toString());
					
				}
				
				totalrows=db.getTotalRows(con, "BUILD_SUMMARY");
				db.closeResultSet(rs);
				db.closePreparedStatement(ps);
				db.closeConnction(con);
				
				totaldata.put("success", true);
					    
			
		}
		catch(Exception e)
		{
			System.out.println("the blow is the error exception:");
			System.out.println(e);
			totaldata.put("errorMsg", e);
			totaldata.put("success",false);
		}
		
		
		totaldata.put("rows",totalrecord);
		totaldata.put("total",totalrows);
		latestBuildObj=JSONObject.fromObject(totaldata);
		
		
		return SUCCESS;
	}
	

	
	//save a new build from the front page data
	public String saveBuild(){
		System.out.println("build id:"+build.getId());
		System.out.println("build name:"+build.getName());
		System.out.println("build type:"+build.getType());
		System.out.println("build status:"+build.getStatus());
		System.out.println("build start time:"+build.getStarttime());
		System.out.println("build end time :"+build.getEndtime());
		System.out.println("build new defect:"+build.getNewdefects());
		System.out.println("build old defect:"+build.getOlddefects());
		System.out.println("build comment:"+build.getComments());
		// insert this record
		Map<String, Object> newmap=new HashMap<String, Object>();
		try {
				int buildgot=0;
				con=db.getConnection();
				ps=db.getPreparedStatement(con, checkidsql);
				ps.setInt(1, build.getId());
				rs=ps.executeQuery();
				while(rs.next()){
					buildgot=rs.getInt("numbers");
				}
				System.out.println("build execution id count is:"+buildgot);
				//get the specified execution id,will update it 
			   if(buildgot>0){
				  System.out.print("this build id is created before ,we just need to update the execution result"); 
				  ps=db.getPreparedStatement(con, updateidsql);
				  ps.setString(1,build.getName() );  //build name
				  ps.setString(2, build.getStatus());
				  ps.setString(3,build.getType() );
				  ps.setString(4,build.getComments() );
				  System.out.println("format time is :"+TimeUtils.formatTime(build.getStarttime()));
				  ps.setString(5,TimeUtils.formatTime(build.getStarttime()) );
				  ps.setString(6,TimeUtils.formatTime(build.getEndtime()) );
				  ps.setInt(7,build.getId());
			      int updateresult=ps.executeUpdate();
			      if(updateresult==1){
			    	  newmap.put("success", true);
			    	  System.out.println("Insert this record into database correctly now ...");
			      }else{
			    	  newmap.put("success",false);
			    	  System.out.println("Failed, cannot insert this record");
			      }
			   }else{
				   newmap.put("success",false);
				   System.out.println("Sorry ,you need to input the existing exeuction id");
			   }
			   
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.closeResultSet(rs);
			db.closePreparedStatement(ps);
			db.closeConnction(con);
		}
		
		newBuildObj=JSONObject.fromObject(newmap);
		System.out.println("get the json object is:"+newBuildObj);
		return SUCCESS;
	}
	
	public String deleteBuild(){
		System.out.println("build id:"+build.getId());
		
		// insert this record
		Map<String, Object> newmap=new HashMap<String, Object>();
		try {
				int buildgot=0;
				con=db.getConnection();
				ps=db.getPreparedStatement(con, deletesql);
				ps.setInt(1, build.getId());
				buildgot=ps.executeUpdate();
			
				System.out.println("build execution id count is:"+buildgot);
				//get the specified execution id,will update it 
			   if(buildgot==1){				 
			    	  newmap.put("success", true);
			    	  System.out.println("Insert this record into database correctly now ...");
			   }else{
			    	  newmap.put("success",false);
			    	  System.out.println("Failed, cannot insert this record");
			      }			   
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.closeResultSet(rs);
			db.closePreparedStatement(ps);
			db.closeConnction(con);
		}
		
		deleteBuildObj=JSONObject.fromObject(newmap);
		System.out.println("get the json object is:"+deleteBuildObj);
		return SUCCESS;
	}
	
	
	public String searchBuild(){
		
		System.out.println("Front json page number is :"+page);
	    System.out.println("Front json total number is :"+rows);
	    System.out.println("sort field is:"+sort);
	    System.out.println("sort is:"+order);
//	      
	    int totalsearchlist=0;
		//current page number 1,2,3,4
		int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //every page total number
        int number = Integer.parseInt((rows == null || rows == "0") ? "20":rows);  
     
        //every page start index  first is 1  second is number +1     
        int start = (intPage-1)*number;
//        int end=start+number-1;
        
		
	   	System.out.print("Page start:"+start);
	   	System.out.println("Page numbers:"+number);
	   	
			System.out.println("start time is:"+build.getStarttime());
			System.out.println("end time is:"+build.getEndtime());
			System.out.println("type is:"+build.getType());
			System.out.println("build id is:"+build.getId());
			
			Map<String, Object> totaldata=new HashMap<String,Object>();
			List<Map<String, Object>> newsearchlist =new ArrayList<Map<String, Object>>();
			
			String searchsql="SELECT * FROM BUILD_SUMMARY t WHERE t.BUILD_STATUS != 'invalid' ";
			if(!build.getStarttime().equals("")){
				searchsql+=" AND date(t.START_TIME) >='"+build.getStarttime()+"'";
			}
			if(!build.getEndtime().equals("")){
				searchsql+=" and date(t.START_TIME) <='"+build.getEndtime()+"'";
			}
			if(!build.getType().equals("")){
				searchsql+=" and t.BUILD_TYPE like '%"+build.getType()+"%'";
			}
			if(build.getId()!=null){
				searchsql+=" and t.BUILD_ID="+build.getId();
			}
			searchsql+=" ORDER BY t.START_TIME DESC LIMIT ?,?";
			System.out.println("search sql is:"+searchsql);
			
			try {
				   con=db.getConnection();
				   ps=db.getPreparedStatement(con, searchsql);
				   ps.setInt(1, start);
			       ps.setInt(2, number);
			       rs=ps.executeQuery();
				   while(rs.next())
					{
					   totalsearchlist=totalsearchlist+1;
						String starttime,endtime;
					//	int likenumber,dislikenumber;
						Map<String, Object> buildmap=new HashMap<String, Object>();
						buildmap.put("build_id",rs.getInt("BUILD_ID"));
						System.out.println("build id is:"+rs.getInt("build_id"));
						buildmap.put("build_status", rs.getString("BUILD_STATUS"));
						System.out.println("build id is:"+rs.getString("build_status"));
						buildmap.put("build_type",rs.getString("BUILD_TYPE"));
						buildmap.put("build_name", rs.getString("BUILD_NAME"));
						buildmap.put("build_comment", rs.getString("BUILD_COMMENT"));
						if(rs.getString("START_TIME")==null){
							starttime="";
						}else{
							starttime=rs.getString("START_TIME");

						}
						if(rs.getString("END_TIME")==null){
							endtime="";
						}else{
							endtime=rs.getString("END_TIME");
						}
						
					    buildmap.put("build_starttime",starttime);
					    buildmap.put("build_endtime",endtime);
					    System.out.println("build id is:"+rs.getString("end_time"));
					    buildmap.put("likenumber",rs.getInt("LIKE"));
					    System.out.println("like number is :"+rs.getInt("like"));
					    buildmap.put("dislikenumber",rs.getInt("DISLIKE"));
						
					    newsearchlist.add(buildmap);
//					    System.out.println("totalrecord:"+totalrecord.toString());
						
					}
					
					//totalrows=db.getTotalRows(con, "BUILD_SUMMARY");
					db.closeResultSet(rs);
					db.closePreparedStatement(ps);
					db.closeConnction(con);
					
					totaldata.put("success", true);
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				db.closeResultSet(rs);
				db.closePreparedStatement(ps);
				db.closeConnction(con);
			}
			
			totaldata.put("rows",newsearchlist);
			totaldata.put("total",totalsearchlist);
			searchBuildObj=JSONObject.fromObject(totaldata);
			return SUCCESS;
	}
    public String getRows() {
		return rows;
	}



	public void setRows(String rows) {
		this.rows = rows;
	}



	public String getPage() {
		return page;
	}



	public void setPage(String page) {
		this.page = page;
	}



	public String getData() {
		return data;
	}



	public void setData(String data) {
		this.data = data;
	}



	public JSONObject getLatestBuildObj() {
		return latestBuildObj;
	}



	public void setLatestBuildObj(JSONObject latestBuildObj) {
		this.latestBuildObj = latestBuildObj;
	}


	/**
	 * @return the newBuildObj
	 */
	public JSONObject getNewBuildObj() {
		return newBuildObj;
	}


	/**
	 * @param newBuildObj the newBuildObj to set
	 */
	public void setNewBuildObj(JSONObject newBuildObj) {
		this.newBuildObj = newBuildObj;
	}



	/**
	 * @return the build
	 */
	public Builds getBuild() {
		return build;
	}



	/**
	 * @param build the build to set
	 */
	public void setBuild(Builds build) {
		this.build = build;
	}



	public JSONObject getDeleteBuildObj() {
		return deleteBuildObj;
	}



	public void setDeleteBuildObj(JSONObject deleteBuildObj) {
		this.deleteBuildObj = deleteBuildObj;
	}



	public String getSort() {
		return sort;
	}



	public void setSort(String sort) {
		this.sort = sort;
	}



	public String getOrder() {
		return order;
	}



	public void setOrder(String order) {
		this.order = order;
	}



	public JSONObject getSearchBuildObj() {
		return searchBuildObj;
	}



	public void setSearchBuildObj(JSONObject searchBuildObj) {
		this.searchBuildObj = searchBuildObj;
	}
   
}
