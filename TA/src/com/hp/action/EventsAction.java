package com.hp.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import net.sf.json.JSONArray;

import com.hp.utility.DBUtils;
import com.opensymphony.xwork2.ActionSupport;

public class EventsAction extends ActionSupport implements SessionAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6818293370770346467L;
	private JSONArray eventObj=null;
	

	private String eventssql="select * from EVENTS t "
			                  + " where t.user_name=? order by t.event_id asc";
	
    private DBUtils db=new DBUtils();	
    private Connection con=null;
    private PreparedStatement ps=null;
    private ResultSet rs=null;
    
    private String username=null;
	
    private Map<String, Object> session;
    
	
	public String listEvents(){
		
		
		//Map<String, Object> events=new HashMap<String,Object>();
		List<Map<String, Object>> rowrecords =new ArrayList<Map<String, Object>>();
		//System.out.println("event type is:"+eventgroupname);
		try {
			
				con=db.getConnection();
				ps=db.getPreparedStatement(con, eventssql);
				ps.setString(1,(String) session.get("userid"));
				rs=ps.executeQuery();
				while(rs.next()){
					Map<String, Object> event=new HashMap<String, Object>();
					int eventid=rs.getInt("event_id");
					String streventid=String.valueOf(eventid);
					
					String eventtitle=rs.getString("text");
					String  starttime=rs.getString("start_time");
					String endtime=rs.getString("end_time");
					String urgency=rs.getString("urgency");
					String status=rs.getString("status");
					event.put("allDay","");
					event.put("title", eventtitle);
					event.put("id",eventid);
					event.put("start", starttime);
					event.put("end", endtime);
					//event.put("eventColor","#b55793");
					rowrecords.add(event);
				}
					
				
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				db.closeConnction(con);
				db.closePreparedStatement(ps);
				db.closeResultSet(rs);
			}
		
		  //  events.put("rows", rowrecords);		
			eventObj=JSONArray.fromObject(rowrecords);		
			return SUCCESS;
	}


	/**
	 * @return the eventObj
	 */
	public JSONArray getEventObj() {
		return eventObj;
	}


	/**
	 * @param eventObj the eventObj to set
	 */
	public void setEventObj(JSONArray eventObj) {
		this.eventObj = eventObj;
	}


	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}


	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	public Map<String, Object> getSession() {
		return session;
	}


	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.session=session;
	}

	
	

}
