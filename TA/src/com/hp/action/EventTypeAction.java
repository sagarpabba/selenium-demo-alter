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

import net.sf.json.JSONObject;

import com.hp.utility.DBUtils;
import com.opensymphony.xwork2.ActionSupport;

public class EventTypeAction extends ActionSupport implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -172816777824661636L;
	
	private int eventgroupname;
	private JSONObject eventTypeObj=null;
	
	private String eventtypesql="select * from EVENT_TYPE t "
			                  + "WHERE t.EVENT_GROUP=? order by t.TYPE_ID asc";
	
    private DBUtils db=new DBUtils();	
    private Connection con=null;
    private PreparedStatement ps=null;
    private ResultSet rs=null;
	
    private Map<String, Object> session;
    
	
	
	public String listEvents(){
		
		Map<String, Object> eventtypes=new HashMap<String,Object>();
		List<Map<String, Object>> typerecords =new ArrayList<Map<String, Object>>();
		System.out.println("event type is:"+eventgroupname);
		try {
			
			con=db.getConnection();
			ps=db.getPreparedStatement(con, eventtypesql);
			ps.setInt(1,eventgroupname);
			rs=ps.executeQuery();
			while(rs.next()){
				Map<String, Object> eventtypemap=new HashMap<String, Object>();
				int eventtypeid=rs.getInt("TYPE_ID");
				String eventtypename=rs.getString("EVENT_NAME");
				
				eventtypemap.put("eventypeid", eventtypeid);
				eventtypemap.put("eventtypename", eventtypename);
				typerecords.add(eventtypemap);				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.closeConnction(con);
			db.closePreparedStatement(ps);
			db.closeResultSet(rs);
		}
		System.out.println("session user name is:"+session.get("userid"));
		eventtypes.put("user",session.get("userid"));
		eventtypes.put("eventyperow", typerecords);
		
		setEventTypeObj(JSONObject.fromObject(eventtypes));
		System.out.println("event json object is:"+getEventTypeObj());
		return SUCCESS;
	}


	/**
	 * @return the eventgroupname
	 */
	public int getEventgroupname() {
		return eventgroupname;
	}

	/**
	 * @param eventgroupname the eventgroupname to set
	 */
	public void setEventgroupname(int eventgroupname) {
		this.eventgroupname = eventgroupname;
	}


	/**
	 * @return the eventTypeObj
	 */
	public JSONObject getEventTypeObj() {
		return eventTypeObj;
	}


	/**
	 * @param eventTypeObj the eventTypeObj to set
	 */
	public void setEventTypeObj(JSONObject eventTypeObj) {
		this.eventTypeObj = eventTypeObj;
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
