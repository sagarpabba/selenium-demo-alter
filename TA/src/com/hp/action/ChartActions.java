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

import com.hp.utility.DBUtils;
import com.hp.utility.TimeUtils;
import com.opensymphony.xwork2.ActionSupport;

public class ChartActions extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8855486106461620721L;
	
	private JSONObject objExecutionBarChartWeekly=null;
	//database object
	DBUtils db=new DBUtils();
	private Connection con=null;
	private PreparedStatement ps=null;
	private ResultSet rs=null;
	
	private String sql="select * from build_report br where br.CREATE_TIME BETWEEN ? and ? order by br.CREATE_TIME ASC";

// the blow is for execution result chart	
	
	public String barChartWeekly(){
		Map<String,List<List<Object>>> statusmap =new HashMap<String,List<List<Object>>>();
		
		List<List<Object>> passedlist =new ArrayList<List<Object>>();
		List<List<Object>> failedlist =new ArrayList<List<Object>>();
		List<List<Object>> warninglist =new ArrayList<List<Object>>();
		try{
			con=db.getConnection();
			ps=db.getPreparedStatement(con, sql);
			ps.setString(1, TimeUtils.getMondayOfThisWeek());
			ps.setString(2, TimeUtils.getSundayOfThisWeek());
		//	ps.setString(1, user);
			rs=ps.executeQuery();		
			while(rs.next()){
				    List<Object> passedsub=new ArrayList<Object>();
				    List<Object> failedsub =new ArrayList<Object>();
				    List<Object> warningsub =new ArrayList<Object>();
			        int buildid=rs.getInt("BUILD_ID");
			        int passed=rs.getInt("PASSED");
			        int failed=rs.getInt("FAILED");
			        int warning=rs.getInt("WARNING");
			     //   Date runtime=rs.getDate("CREATE_TIME");
			        //passed list
			        passedsub.add(buildid);
			        passedsub.add(passed);
			        //failed list
			        failedsub.add(buildid);
			        failedsub.add(failed);
			        
			        warningsub.add(buildid);
			       // warningsub.add("EXECUTION ID:"+String.valueOf(buildid)+new SimpleDateFormat("yyyy-MM-dd").format(runtime));
			        warningsub.add(warning);
			        
			        passedlist.add(passedsub);
			        failedlist.add(failedsub);
			        warninglist.add(warningsub);
			}
			System.out.println("passed list is:"+passedlist);
			System.out.println("failed list is:"+failedlist);
			System.out.println("warning list is:"+warninglist);
		}catch (SQLException e) {
			
		}finally{
			db.closeConnction(con);
			db.closePreparedStatement(ps);
			db.closeResultSet(rs);
		}
		statusmap.put("passed", passedlist);
		statusmap.put("failed", failedlist);
		statusmap.put("warning", warninglist);
		System.out.println("json map is:"+statusmap);
		objExecutionBarChartWeekly=JSONObject.fromObject(statusmap);
		System.out.println("Json return value is:"+objExecutionBarChartWeekly);
		return SUCCESS;
	}

	
	public String barChartMonthly(){
		
		return SUCCESS;
	}
	
	public String barChartProject(){
		
		return SUCCESS;
	}


	public JSONObject getObjExecutionBarChartWeekly() {
		return objExecutionBarChartWeekly;
	}


	public void setObjExecutionBarChartWeekly(JSONObject objExecutionBarChartWeekly) {
		this.objExecutionBarChartWeekly = objExecutionBarChartWeekly;
	}



	
}
