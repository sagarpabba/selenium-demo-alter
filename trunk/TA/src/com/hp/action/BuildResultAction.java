package com.hp.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.hp.utility.DBUtils;
import com.hp.utility.TimeUtils;
import com.opensymphony.xwork2.ActionSupport;

public class BuildResultAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8018421644415322020L;
	
	private JSONObject listResultsObj=null;
	
	private String buildid;
	private String starttime;
	private String endtime;
	DBUtils db=new DBUtils();
	private Connection con=null;
	private PreparedStatement ps=null;
	private ResultSet rs=null;
	private PreparedStatement valueps=null;
	private ResultSet valuers=null;
	private String buildstatus=null;
	
	String summarysql="select  t.STEP_STATUS,count(t.STEP_STATUS) from BUILD_RESULT t "
			+ "where t.BUILD_ID=? GROUP BY t.STEP_STATUS";
	String listsql="select t.STEP_ID,t.STEP_NAME,t.STEP_DESC,t.STEP_STATUS,t.STEP_COMMENT,t.CREATE_TIME from"
    		+ " BUILD_RESULT t where t.build_id=? order by t.BUILD_ID ASC";
	String timesql="select t.BUILD_ID,t.START_TIME,t.END_TIME from BUILD_SUMMARY t WHERE t.BUILD_ID=?";
	
	String valuesql="select t.BUILD_ID,t.DATA_NAME,t.DATA_VALUE from BUILD_VALUE t where t.BUILD_ID=?";
			
	
	
	public String listBuildResult() throws ParseException{
		
		   
			Map<String, Object> builddetails=new HashMap<String,Object>();
			List<Map<String, Object>> steprecourd =new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> valuerecourd =new ArrayList<Map<String, Object>>();
			//get the parameter value
			//String buildid=(String) ActionContext.getContext().getParameters().get("buildid");
			//String buildid2=ServletActionContext.getRequest().getParameter("buildid");
			//System.out.println("build id 2:"+buildid2);
			System.out.println("starttime:"+starttime.replace("%20", " "));
			System.out.println("endtime:"+endtime.replace("%20", " "));
			//starttime=starttime.replace("%20", " ");
			//endtime=endtime.replace("%20", " ").trim();
			System.out.println("Build id:"+buildid);
			
			builddetails.put("buildid", buildid);
			System.out.println("action function endtime is:"+endtime);
			String totaltime=null;
			if(endtime.equals("0")){
				totaltime="Running";
			}else{
				totaltime=TimeUtils.howManyMinutes(starttime, endtime);
			}
			
			
			int totalrecords=0;
			int passed=0;
			int failed=0;
			int warning=0;
			//database data		
			
			try {
				con=db.getConnection();
				ps=db.getPreparedStatement(con, listsql);
				ps.setString(1, buildid);
				rs=ps.executeQuery();
		
				
				while(rs.next()){
					Map<String, Object> resultmap=new HashMap<String, Object>();
					
					totalrecords=totalrecords+1;
					int id=rs.getInt("STEP_ID");
					String stepname=rs.getString("STEP_NAME").trim();
					String stepdesc=rs.getString("STEP_DESC").trim();
					String status=rs.getString("STEP_STATUS").trim();
					if(status.equalsIgnoreCase("passed")){
						passed=passed+1;
					}else if(status.equalsIgnoreCase("warning")){
						warning=warning+1;
					}else if(status.equalsIgnoreCase("failed")){
						 failed=failed+1;
					}else{
						System.out.println("we cannot find this status in database data:"+status);
					}
					String comment=rs.getString("STEP_COMMENT").trim();
					String createtime=rs.getString("CREATE_TIME").trim();
					
					
					resultmap.put("stepid", id);
					resultmap.put("stepname", stepname);
					resultmap.put("stempdesc", stepdesc);
					resultmap.put("status", status);
					resultmap.put("comment", comment);
					resultmap.put("createtime", createtime);
					
					steprecourd.add(resultmap);
					
				}
	    
				valueps=db.getPreparedStatement(con, valuesql);
				valueps.setString(1, buildid);
				valuers=valueps.executeQuery();
				
				while(valuers.next()){
					Map<String, Object> valuemap=new HashMap<String, Object>();
									
					valuemap.put("dataname", valuers.getString("DATA_NAME"));
					valuemap.put("datavalue", valuers.getString("DATA_VALUE"));
					valuerecourd.add(valuemap);
					
				}
				
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				db.closePreparedStatement(ps);
				db.closeResultSet(rs);
				db.closeConnction(con);
				
			}
			
			builddetails.put("steps", steprecourd);
			builddetails.put("values", valuerecourd);
			
			builddetails.put("totaltime", totaltime);
			builddetails.put("totalnumbers", totalrecords);
			builddetails.put("passed", passed);
			builddetails.put("failed", failed);
			builddetails.put("warning", warning);
			
			if (passed == totalrecords) {
				buildstatus="Passed";
			} else if (failed > 0) {
				buildstatus="Failed";
			} else if((warning>0)&&(failed==0)){
				buildstatus="Warning";
			}else{
				buildstatus="Not Completed";
			}
			
			builddetails.put("buildstatus", buildstatus);
			
			
			listResultsObj=JSONObject.fromObject(builddetails);
			System.out.println("Json front data is:"+listResultsObj);
			
			return SUCCESS;
	}

	/**
	 * @return the listResultsObj
	 */
	public JSONObject getListResultsObj() {
		return listResultsObj;
	}

	/**
	 * @param listResultsObj the listResultsObj to set
	 */
	public void setListResultsObj(JSONObject listResultsObj) {
		this.listResultsObj = listResultsObj;
	}

	/**
	 * @return the buildid
	 */
	public String getBuildid() {
		return buildid;
	}

	/**
	 * @param buildid the buildid to set
	 */
	public void setBuildid(String buildid) {
		this.buildid = buildid;
	}

	/**
	 * @return the starttime
	 */
	public String getStarttime() {
		return starttime;
	}

	/**
	 * @param starttime the starttime to set
	 */
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	/**
	 * @return the endtime
	 */
	public String getEndtime() {
		return endtime;
	}

	/**
	 * @param endtime the endtime to set
	 */
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	

}
