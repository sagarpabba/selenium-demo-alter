package com.hp.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import com.hp.utility.DBUtils;
import com.opensymphony.xwork2.ActionSupport;

public class DashboardAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3771554125058253869L;
	
	private JSONArray objDashboard=null;
	private String user;
	//database object
	DBUtils db=new DBUtils();
	private Connection con=null;
	private PreparedStatement ps=null;
	private ResultSet rs=null;
	private int passnumber=0;
	private int failednumber=0;
	private int warningnumber=0;

	
	private String sql="select * from build_result br where br.BUILD_ID "
			+ "in (select t.build_id from (select * from build_summary bs ORDER BY bs.START_TIME desc limit 1) as t);";

	//private String creattime="";
	
	
	public String PieChart(){
		
		Map<String, Object> passdata=new HashMap<String,Object>();
		Map<String, Object> faileddata=new HashMap<String,Object>();
		Map<String, Object> warningdata=new HashMap<String,Object>();
		List<Map<String, Object>> activitylist =new ArrayList<Map<String, Object>>();
		System.out.println("login user  is:"+user);
		
		try{
				con=db.getConnection();
				ps=db.getPreparedStatement(con, sql);
			//	ps.setString(1, user);
				rs=ps.executeQuery();
			
				while(rs.next()){
					 // String creattime=null;
					  String status=rs.getString("STEP_STATUS");
					  if(rs.getString("CREATE_TIME")==null){
						 String  creattime="";
					  }else{
						  String creattime=rs.getString("CREATE_TIME");
					  }
					  //String creattime=rs.getString("CREATE_TIME");
					  if(status.trim().equalsIgnoreCase("Passed")){
						  passnumber=passnumber+1;
					  }else if(status.trim().equalsIgnoreCase("Failed")){
						  failednumber=failednumber+1;
						  
					  }else if (status.trim().equalsIgnoreCase("Warning")){
						  warningnumber=warningnumber+1;
					  }
					  
				}
			
		}catch (SQLException e) {
			
		}finally{
			db.closeConnction(con);
			db.closePreparedStatement(ps);
			db.closeResultSet(rs);
		}
		
		
		passdata.put("label", "Passed Cases");
		passdata.put("data", passnumber);
		passdata.put("color", "#00FF00");
		
		faileddata.put("label", "Failed Cases");
		faileddata.put("data", failednumber);
		faileddata.put("color", "#FF0000");
		
		warningdata.put("label", "Warning Cases");
		warningdata.put("data", warningnumber);
		warningdata.put("color", "#FFFF00");
		
		activitylist.add(passdata);
		activitylist.add(faileddata);
		activitylist.add(warningdata);
		objDashboard=JSONArray.fromObject(activitylist);
		return SUCCESS;
	}

    public String barChart(){
    //	List<Integer> activitylist =new ArrayList<Integer>();
    	
    	return SUCCESS;
    }

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}


	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}



	/**
	 * @return the objDashboard
	 */
	public JSONArray getObjDashboard() {
		return objDashboard;
	}



	/**
	 * @param objDashboard the objDashboard to set
	 */
	public void setObjDashboard(JSONArray objDashboard) {
		this.objDashboard = objDashboard;
	}
	
	

}
